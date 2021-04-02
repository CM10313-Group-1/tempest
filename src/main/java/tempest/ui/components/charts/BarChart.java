package tempest.ui.components.charts;

import javax.swing.JLabel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeTableXYDataset;
import org.jfree.data.xy.TableXYDataset;

import tempest.Module;
import tempest.State;
import tempest.StudySession;
import tempest.ui.ViewManager;

public class BarChart extends Chart {
    private static final long serialVersionUID = -2288959674462946064L;

    public BarChart(State state, ViewManager<Chart> manager) {
        super(state, manager);
        this.add(new JLabel(getName()));

        setupUI();
    }

    private void setupUI() {
        this.removeAll();
        this.add(createBarChart());
    }

    @Override
    public void updateChart(State state) {
        this.state = state;
        setupUI();
    }

    /**
     * Creates a ChartPanel containing the Bar Chart
     *
     * @return ChartPanel
     */
    private ChartPanel createBarChart() {
        XYPlot plot = new XYPlot();

        //Axis
        plot.setDomainAxis(new DateAxis("Date"));
        plot.setRangeAxis(new NumberAxis("Time"));

        //Dataset
        plot.setDataset(createDataset());

        //Renderer
        plot.setRenderer(new XYBarRenderer());

        // Creating the bar chart
        JFreeChart chart = new JFreeChart(plot);
        return new ChartPanel(chart);
    }

    /**
     * @return A TableXYDataset containing the dates and length of all study sessions
     */
    private TableXYDataset createDataset() {
        TimeTableXYDataset dataset = new TimeTableXYDataset();

        for (Module m : state.getModules()) {
            for (StudySession s : m.getStudySessions()) {
                dataset.add(new Day(s.date), s.duration.toMinutes(), m.getName());
            }
        }

        return dataset;
    }

    @Override
    public String getName() {
        return ChartTypes.BAR;
    }
}
