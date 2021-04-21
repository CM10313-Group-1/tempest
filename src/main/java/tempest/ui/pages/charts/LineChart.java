package tempest.ui.pages.charts;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.data.time.TimePeriod;
import org.jfree.data.time.TimePeriodValues;
import org.jfree.data.time.TimePeriodValuesCollection;
import tempest.Module;
import tempest.State;
import tempest.StudySession;
import tempest.Supervisor;
import tempest.ui.GUIManager;
import tempest.ui.components.BackButton;
import tempest.ui.pages.PageNames;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LineChart extends Chart {
    private static final long serialVersionUID = -1275171253819439097L;
    private String specifiedModule = null;

    private BackButton backButton;
    private XYPlot plot;
    private TimePeriodValuesCollection dataset;

    private final JComboBox<String> lineComboBox;

    public LineChart(State state, GUIManager manager) {
        super(state, manager);

        lineComboBox = new JComboBox<>();
        lineComboBox.addItem("All");

        for (Module m : state.getModules()) {
            lineComboBox.addItem(m.getName());
        }

        lineComboBox.addActionListener(e -> {
            setModuleFilter((String) Objects.requireNonNull(lineComboBox.getSelectedItem()));
            updateChart(Supervisor.state);
            manager.resizeGUI();
        });

        setupUI();
    }

    private void setupUI() {
        this.removeAll();
        this.add(createChart());

        backButton = new BackButton(manager);
        this.add(backButton);
        this.add(lineComboBox);
    }

    @Override
    public void updateChart(State state) {
        this.state = state;
        setupUI();
    }

    /**
     * Generates the chart as a panel that can be added to the UI.
     *
     * @return {@link ChartPanel} of the chart.
     */
    private ChartPanel createChart() {
        plot = generatePlot(state);
        JFreeChart chart = new JFreeChart(plot);
        setModuleColors(state.getModules());
        return new ChartPanel(chart);
    }

    /**
     * Generates the plot to be displayed for the chart.
     *
     * @param state The current state of recorded data.
     * @return A plot showing all data as a line chart.
     */
    private XYPlot generatePlot(State state) {
        DateAxis domainAxis = new DateAxis("Date");
        dataset = generateDataset(state);
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setDrawSeriesLineAsPath(true);
        XYPlot plot = new XYPlot(dataset, domainAxis, new NumberAxis("Minutes Studied"), renderer);
        plot.setBackgroundPaint(Color.DARK_GRAY);
        return plot;
    }

    /**
     * Generates the dataset to be used for displaying the state information.
     *
     * @param state The current state of recorded data.
     * @return A dataset recording the number of minutes studied, per day, per
     * module.
     */
    private TimePeriodValuesCollection generateDataset(State state) {
        TimePeriodValuesCollection dataset = new TimePeriodValuesCollection();

        if (specifiedModule == null) {
            for (Module m : state.getModules()) {
                TimePeriodValues moduleSeries = new TimePeriodValues(m.getName());

                for (StudySession s : m.getStudySessions()) {
                    TimePeriod day = new SimpleTimePeriod(s.date, s.date);
                    moduleSeries.add(day, s.duration.toMinutes());
                }

                dataset.addSeries(moduleSeries);
            }

        } else {
            for (Module m : state.getModules()) {
                if (m.getName().equals(specifiedModule)) {
                    TimePeriodValues moduleSeries = new TimePeriodValues(m.getName());

                    for (StudySession s : m.getStudySessions()) {
                        TimePeriod day = new SimpleTimePeriod(s.date, s.date);
                        moduleSeries.add(day, s.duration.toMinutes());
                    }
                    dataset.addSeries(moduleSeries);
                }
            }

        }

        return dataset;
    }

    private void setModuleColors(Module[] modules) {
        for (Module module : modules) {
            try {
                if (module.getStudySessions().length > 0) {
                    plot.getRenderer().setSeriesPaint(dataset.indexOf(module.getName()), module.getColor());
                }
            } catch (IllegalArgumentException ignored) {
            }
        }

    }

    @Override
    public String getName() {
        return PageNames.LINE;
    }

    public BackButton getBackButton() {
        return backButton;
    }

    public void setModuleFilter(String selectedItem) {
        if (selectedItem.equals("All")) {
            specifiedModule = null;
        } else {
            specifiedModule = selectedItem;
        }
    }
}
