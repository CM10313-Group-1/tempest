package tempest.ui.components.charts;

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
import tempest.ui.ViewManager;

import java.awt.Color;

public class LineChart extends Chart {
    private static final long serialVersionUID = -1275171253819439097L;

    public LineChart(State state, ViewManager<Chart> manager) {
        super(state, manager);
        setupUI();
    }

    private void setupUI() {
        this.removeAll();
        this.add(createChart());
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
        XYPlot plot = generatePlot(state);
        JFreeChart chart = new JFreeChart(plot);
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
        TimePeriodValuesCollection dataset = generateDataset(state);
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
        for (Module m : state.getModules()) {
            TimePeriodValues moduleSeries = new TimePeriodValues(m.getName());
            for (StudySession s : m.getStudySessions()) {
                TimePeriod day = new SimpleTimePeriod(s.date, s.date);
                moduleSeries.add(day, s.duration.toMinutes());
            }
            dataset.addSeries(moduleSeries);
        }

        return dataset;
    }

    @Override
    public String getName() {
        return ChartTypes.LINE;
    }

}
