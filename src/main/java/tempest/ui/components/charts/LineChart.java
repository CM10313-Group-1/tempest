package tempest.ui.components.charts;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimePeriod;
import org.jfree.data.time.TimeTableXYDataset;

import tempest.Module;
import tempest.State;
import tempest.StudySession;
import tempest.ui.ViewManager;

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
        TimeTableXYDataset dataset = generateDataset(state);
        return new XYPlot(dataset, domainAxis, new NumberAxis("Minutes Studied"), new XYSplineRenderer());
    }

    /**
     * Generates the dataset to be used for displaying the state information.
     *
     * @param state The current state of recorded data.
     * @return A dataset recording the number of minutes studied, per day, per
     * module.
     */
    private TimeTableXYDataset generateDataset(State state) {
        TimeTableXYDataset dataset = new TimeTableXYDataset();
        for (Module m : state.getModules()) {
            for (StudySession s : m.getStudySessions()) {
                TimePeriod day = new Day(s.date);
                dataset.add(day, s.duration.toMinutes(), m.getName());
            }
        }

        return dataset;
    }

    @Override
    public String getName() {
        return ChartTypes.LINE;
    }

}
