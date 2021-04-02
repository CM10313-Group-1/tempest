package tempest.ui.components.charts;

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
import tempest.ui.ViewManager;
import tempest.Module;

public class PieChart extends Chart {
    private static final long serialVersionUID = 7074811797165362922L;

    public PieChart(State state, ViewManager<Chart> manager) {
        super(state, manager);
        this.add(new JLabel(getName()));
        this.add(createChart());
    }

    private ChartPanel createChart() {
        // TODO: Could get the label to show hours and minutes.

        PieDataset<String> dataset = generateDataset(state);
        ChartPanel pieChart = new ChartPanel(ChartFactory.createPieChart("Pie Chart", dataset, true, true, false));

        // Changes the label formatting to allow minutes to be shown.
        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator("{0} = {1} mins");
        PiePlot plot = (PiePlot) pieChart.getChart().getPlot();
        plot.setLabelGenerator(labelGenerator);
        return pieChart;
    }

    @Override
    public String getName() {
        return ChartTypes.PIE;
    }

    @Override
    public void updateChart(State state) {
        // TODO Auto-generated method stub

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

}
