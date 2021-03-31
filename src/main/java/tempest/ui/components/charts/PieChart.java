package tempest.ui.components.charts;

import javax.swing.JLabel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;

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

  private ChartPanel createChart(){
    PieDataset<String> dataset = generateDataset(state);
    return new ChartPanel(ChartFactory.createPieChart("Pie Chart", dataset, true, true, false));
  }

  @Override
  public String getName() {
    return ChartTypes.PIE;
  }

  @Override
  public PieDataset<String> generateDataset(State state) {
    DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
    for(Module module: state.getModules()){
      int totalStudyTime = 0;
      for(StudySession studySession: module.getStudySessions()){
        totalStudyTime += studySession.duration.toMinutes();
      }
      dataset.setValue(module.getName(), totalStudyTime);
    }
    return dataset;
  }

}
