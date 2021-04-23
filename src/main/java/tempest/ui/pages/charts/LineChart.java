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
import tempest.ui.GUIManager;
import tempest.ui.pages.PageNames;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LineChart extends Chart {
    private static final long serialVersionUID = -1275171253819439097L;
    private String specifiedModule = null;

    private XYPlot plot;
    private TimePeriodValuesCollection dataset;

    private JComboBox<String> lineComboBox;
    private String selectedItem = "";

    //TODO: Only populate with modules with sessions

    public LineChart(State state, GUIManager manager) {
        super(state, manager);
        setupUI();
    }

    private void setupUI() {
        this.removeAll();

        this.add(createChart());

        JPanel dropDownPanel = new JPanel();
        createDropDown();
        dropDownPanel.add(lineComboBox);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backPanel);
        buttonPanel.add(dropDownPanel);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        this.add(buttonPanel);
    }

    private void createDropDown() {
        lineComboBox = new JComboBox<>();
        populateDropDown();

        lineComboBox.addActionListener(e -> {
            selectedItem = (String) lineComboBox.getSelectedItem();
            setModuleFilter(Objects.requireNonNull(selectedItem));
            updateChart(state);
            manager.resizeGUI();
        });
    }

    private void populateDropDown() {
        lineComboBox.addItem("All");

        for (Module m : state.getModules()) {
            if (m.getStudySessions().length > 0) {
                lineComboBox.addItem(m.getName());
            }
        }

        lineComboBox.setSelectedItem(selectedItem);

    }

    private void setModuleFilter(String selectedItem) {
        if (selectedItem.equals("All")) {
            specifiedModule = null;
        } else {
            specifiedModule = selectedItem;
        }
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
        JFreeChart chart = new JFreeChart("Study Sessions Across Time", plot);
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
        dataset = generateDataset(state);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setDrawSeriesLineAsPath(true);

        XYPlot plot = new XYPlot(dataset, new DateAxis("Date"), new NumberAxis("Minutes Studied"), renderer);
        plot.setBackgroundPaint(Color.DARK_GRAY);

        Font bold = new Font("Dialog", Font.BOLD, 15);
        plot.getDomainAxis().setLabelFont(bold);
        plot.getRangeAxis().setLabelFont(bold);

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
                if (m.getStudySessions().length <= 0) {
                    continue;
                }

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
                    break;
                }
            }
        }

        return dataset;
    }

    private void setModuleColors(Module[] modules) {
        for (Module m : modules) {
            if (m.getStudySessions().length > 0) {
                plot.getRenderer().setSeriesPaint(dataset.indexOf(m.getName()), m.getColor());
            }
        }

    }

    @Override
    public String getName() {
        return PageNames.LINE;
    }
}
