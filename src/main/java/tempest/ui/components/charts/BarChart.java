package tempest.ui.components.charts;

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
import tempest.ui.ViewManager;

import java.awt.*;
import java.util.*;

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

        Font bold = new Font("Dialog", Font.BOLD, 15);
        plot.getDomainAxis().setLabelFont(bold);
        plot.getRangeAxis().setLabelFont(bold);

        //Dataset
        plot.setDataset(createDataset());

        //Renderer
        StackedXYBarRenderer renderer = new StackedXYBarRenderer();

        renderer.setBarPainter(new StandardXYBarPainter());

        renderer.setShadowVisible(false);
        //renderer.setMargin(0.2);
        //renderer.setBarAlignmentFactor(0.5); //-> Doesn't work

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

        // Sorts the study sessions so the biggest sessions are added first

//        //TODO: Only shows smallest time if a module has > 1 study session in a day
//
//        ArrayList<StudySession> allSessions = new ArrayList<>();
//
//        Map<StudySession, String> dict = new HashMap<>();
//
//        for (Module m : state.getModules()) {
//
//            StudySession[] sessions = m.getStudySessions();
//
//            for (StudySession session : sessions) {
//                dict.put(session, m.getName());
//            }
//
//            allSessions.addAll(Arrays.asList(sessions));
//        }
//
//        allSessions.sort(Collections.reverseOrder(Comparator.comparing(o -> o.duration)));
//
//        for (StudySession allSession : allSessions) {
//            System.out.println(allSession.duration.toMinutes());
//        }
//
//        for (StudySession s : allSessions) {
//            dataset.add(new Day(s.date), s.duration.toMinutes(), dict.get(s));
//        }


        // Adds sessions in the order they are stored - means the colours are the same as line & pie charts

        //TODO: Only shows largest time if a module has > 1 study session in a day

        for (Module m : state.getModules()) {
            for (StudySession s : m.getStudySessions()) {
                dataset.add(new Day(s.date), s.duration.toMinutes(), m.getName());
            }
        }

        System.out.println(dataset.getItemCount(3));

        return dataset;
    }

    @Override
    public String getName() {
        return ChartTypes.BAR;
    }
}

//https://www.tutorialspoint.com/javafx/stacked_bar_chart.htm -> JavaFX

//https://stackoverflow.com/questions/40105094/jfreechart-horizontal-stacked-bar-chart-with-date-axis -> Horizontal - StackedBarRenderer - JFreeChart