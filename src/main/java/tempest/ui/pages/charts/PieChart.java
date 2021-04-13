package tempest.ui.pages.charts;

import javax.swing.JLabel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;

import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import tempest.State;
import tempest.StudySession;
import tempest.ui.GUIManager;
import tempest.Module;
import tempest.ui.components.BackButton;
import tempest.ui.pages.PageNames;

import java.awt.*;

public class PieChart extends Chart {
    private static final long serialVersionUID = 7074811797165362922L;

    private BackButton backButton;

    public PieChart(State state, GUIManager manager) {
        super(state, manager);
        this.add(new JLabel(getName()));
        setupUI();
    }

    private ChartPanel createChart() {
        // TODO: Could get the label to show hours and minutes.

        PieDataset<String> dataset = generateDataset(state);
        ChartPanel pieChart = new ChartPanel(ChartFactory.createPieChart("Pie Chart", dataset, true, true, false));

        PiePlot plot = (PiePlot) pieChart.getChart().getPlot();
        plot.setBackgroundPaint(Color.DARK_GRAY);

        // Changes the label formatting to allow minutes to be shown
        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator("{0} = {1} mins");
        plot.setLabelGenerator(labelGenerator);

        // When hovering over sections hours are displayed
        plot.setToolTipGenerator((pieDataset, comparable) -> {
            int sectionMins = pieDataset.getValue(comparable).intValue();

            return String.format("%s: %d hrs %02d mins", comparable.toString(), sectionMins / 60, sectionMins % 60);
        });

        return pieChart;
    }

    private void setupUI() {
        this.removeAll();
        this.add(createChart());

        backButton = new BackButton(manager);
        this.add(backButton);
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

        for (Module module : state.getModules()) {
            int totalStudyTime = 0;

            for (StudySession studySession : module.getStudySessions()) {
                totalStudyTime += studySession.duration.toMinutes();
            }

            dataset.setValue(module.getName(), totalStudyTime);
        }
        return dataset;
    }

    @Override
    public String getName() {
        return PageNames.PIE;
    }

    public BackButton getBackButton() {
        return backButton;
    }
}
