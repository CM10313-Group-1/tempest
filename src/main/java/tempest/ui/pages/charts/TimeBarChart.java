package tempest.ui.pages.charts;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import javax.swing.JLabel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import tempest.Module;
import tempest.State;
import tempest.StudySession;
import tempest.ui.GUIManager;
import tempest.ui.pages.PageNames;

public class TimeBarChart extends Chart {
    private static final long serialVersionUID = -2288959674462946064L;

    private int[] hours;

    public TimeBarChart(State state, GUIManager manager) {
        super(state, manager);
        this.add(new JLabel(getName()));

        setupUI();
    }

    private void setupUI() {
        this.removeAll();
        this.add(createBarChart());

        this.add(backPanel);
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
        BarRenderer renderer = new BarRenderer();
        renderer.setShadowVisible(false);
        renderer.setDefaultToolTipGenerator((categoryDataset, i, i1) -> {
            int hourVal = hours[i1];

            DecimalFormat df = new DecimalFormat("#.##");
            double percent = categoryDataset.getValue(i, i1).doubleValue();

            return String.format("%s%% or %d hrs %02d mins", df.format(percent), hourVal / 60, hourVal % 60);
        });

        NumberAxis time = new NumberAxis("% Time Studied");
        time.setRange(0, 100);
        CategoryAxis hour = new CategoryAxis("Hour of the Day");

        CategoryPlot plot = new CategoryPlot(createDataset(), hour, time, renderer);
        plot.setBackgroundPaint(Color.DARK_GRAY);

        Font bold = new Font("Dialog", Font.BOLD, 15);
        plot.getDomainAxis().setLabelFont(bold);
        plot.getRangeAxis().setLabelFont(bold);

        JFreeChart barChart = new JFreeChart("% Time Studied Per Hour", plot);

        return new ChartPanel(barChart);
    }

    /**
     * @return A TableXYDataset containing the dates and length of all study
     *         sessions
     */
    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH");

        hours = new int[24];

        // Adding together the durations of each session to a "24 hour" array
        for (Module m : state.getModules()) {
            for (StudySession s : m.getStudySessions()) {
                int sessionHour = Integer.parseInt(hourFormat.format(s.date));
                hours[sessionHour] += s.duration.toMinutes();
            }
        }

        double totalTime = Arrays.stream(hours).sum(); // Used to calculate percentage

        // Populating dataset with percentages
        for (int i = 0; i < hours.length; i++) {
            dataset.addValue(hours[i] / totalTime * 100, "Hours Studied", Integer.toString(i));
        }

        return dataset;
    }

    @Override
    public String getName() {
        return PageNames.TIME_BAR;
    }
}
