package tempest.ui.pages.charts;

import javax.swing.*;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;

import org.jfree.data.category.DefaultCategoryDataset;
import tempest.Module;
import tempest.State;
import tempest.StudySession;
import tempest.ui.GUIManager;
import tempest.ui.components.BackButton;
import tempest.ui.pages.PageNames;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class TimeBarChart extends Chart {
    private static final long serialVersionUID = -2288959674462946064L;

    private BackButton backButton;

    private int[] hours;

    public TimeBarChart(State state, GUIManager manager) {
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
     * Creates a ChartPanel containing the Bar Chart
     *
     * @return ChartPanel
     */
    private ChartPanel createBarChart() {
        // Renderer
        BarRenderer renderer = new BarRenderer();
        renderer.setShadowVisible(false);

        renderer.setDefaultToolTipGenerator((categoryDataset, i, i1) -> {
            int hourVal = hours[i1];
            int percent = categoryDataset.getValue(i, i1).intValue();

            return String.format("%d%% or %d hrs %02d mins", percent, hourVal / 60, hourVal % 60);
        });

        // Axis
        NumberAxis time = new NumberAxis("% Time Studied");
        time.setRange(0, 100);

        CategoryAxis hour = new CategoryAxis("Hour of the Day");

        // Plot
        CategoryPlot plot = new CategoryPlot(createDataset(), hour, time, renderer);
        plot.setBackgroundPaint(Color.DARK_GRAY);

        JFreeChart barChart = new JFreeChart("Time Studied Per Hour", plot);

        return new ChartPanel(barChart);
    }

    /**
     * @return A TableXYDataset containing the dates and length of all study sessions
     */
    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Used to get just the hour of a session
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
            dataset.addValue(hours[i]/totalTime * 100, "Hours Studied", Integer.toString(i));
        }

        return dataset;
    }

    @Override
    public String getName() {
        return PageNames.TIME_BAR;
    }

    public BackButton getBackButton() {
        return backButton;
    }
}
