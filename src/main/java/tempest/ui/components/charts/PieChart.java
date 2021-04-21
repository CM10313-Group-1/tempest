package tempest.ui.components.charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import tempest.Module;
import tempest.State;
import tempest.StudySession;
import tempest.ui.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.LinkedList;

public class PieChart extends Chart {
    private static final long serialVersionUID = 7074811797165362922L;
    public Date specifiedDate = null;

    public PieChart(State state, ViewManager<Chart> manager) {
        super(state, manager);
        this.add(new JLabel(getName()));
        setupUI();
    }

    private ChartPanel createChart() {
        // TODO: Could get the label to show hours and minutes.

        PieDataset<String> dataset = generateDataset(state);
        ChartPanel pieChart = new ChartPanel(ChartFactory.createPieChart("Pie Chart", dataset, true, true, false));

        // Changes the label formatting to allow minutes to be shown.
        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator("{0} = {1} mins");
        PiePlot plot = (PiePlot) pieChart.getChart().getPlot();
        plot.setLabelGenerator(labelGenerator);
        plot.setBackgroundPaint(Color.DARK_GRAY);
        return pieChart;
    }

    @Override
    public String getName() {
        return ChartTypes.PIE;
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
     * Creates the dataset used by the pie chart.
     *
     * @param state Instance of state
     * @return The dataset consisting of module names and total study time in minutes
     */
    public PieDataset<String> generateDataset(State state) {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        LinkedList<StudySession> studySessions;
        int totalStudyTime;

        for (Module module : state.getModules()) {
            totalStudyTime = 0;
            // filters the list and picks sessions for the valid time period
            studySessions = filterList(module.getStudySessionsList());

            for (StudySession studySession : studySessions) {
                totalStudyTime += studySession.duration.toMinutes();
            }

            dataset.setValue(module.getName(), totalStudyTime);
            //System.out.println("for "  + module.getName() + " : " + totalStudyTime);
        }

        return dataset;
    }

    public LinkedList<StudySession> filterList(LinkedList<StudySession> studySessions) {
        // if the date is null, return the list. No filtering required
        if(specifiedDate == null){
            return studySessions;
        }

        LinkedList<StudySession> filteredSessions = new LinkedList<>();

        for (StudySession session : studySessions) {
            if ((session.date).after(specifiedDate)) {
                filteredSessions.add(session);
            }
        }

        return filteredSessions;
    }

}
