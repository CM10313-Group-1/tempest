package tempest.ui.pages.charts;

import javax.swing.JLabel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StackedXYBarRenderer;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeTableXYDataset;
import org.jfree.data.xy.TableXYDataset;

import tempest.Module;
import tempest.State;
import tempest.StudySession;
import tempest.ui.GUIManager;
import tempest.ui.components.BackButton;
import tempest.ui.pages.PageNames;

import java.awt.*;

public class StackedBarChart extends Chart {
    private static final long serialVersionUID = -2288959674462946064L;

    private BackButton backButton;

    public StackedBarChart(State state, GUIManager manager) {
        super(state, manager);
        this.add(new JLabel(getName()));

        setupUI();
    }

    private void setupUI() {
        this.removeAll();
        this.add(createBarChart());

        backButton = new BackButton(manager);
        this.add(backButton);
    }

    @Override
    public void updateChart(State state) {
        this.state = state;
        setupUI();
    }

    /**
     * Creates a ChartPanel containing the Stacked Bar Chart
     *
     * @return ChartPanel
     */
    private ChartPanel createBarChart() {
        XYPlot plot = new XYPlot();

        //Axis
        plot.setDomainAxis(new DateAxis("Date"));
        plot.setRangeAxis(new NumberAxis("Time"));

        Font bold = new Font("Dialog", Font.BOLD, 15);
        plot.getDomainAxis().setLabelFont(bold);
        plot.getRangeAxis().setLabelFont(bold);

        //Dataset
        plot.setDataset(createDataset());

        //Background colour
        plot.setBackgroundPaint(Color.DARK_GRAY);

        //Renderer
        StackedXYBarRenderer renderer = new StackedXYBarRenderer();

        renderer.setShadowVisible(false);
        renderer.setBarPainter(new StandardXYBarPainter());

        // Hours and mins when hovering over bars
        renderer.setDefaultToolTipGenerator((xyDataset, i, i1) -> {
            int value = (int) xyDataset.getYValue(i, i1);

            return String.format("%d hrs %02d mins", value / 60, value % 60);
        });

        plot.setRenderer(renderer);

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
            for (StudySession s : m.getSessionsPerDay()) {
                dataset.add(new Day(s.date), s.duration.toMinutes(), m.getName());
            }
        }

        return dataset;
    }

    @Override
    public String getName() {
        return PageNames.STACKED_BAR;
    }

    public BackButton getBackButton() {
        return backButton;
    }
}
