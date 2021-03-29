package tempest.ui.components.charts;

import javax.swing.JLabel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import tempest.Module;
import tempest.State;
import tempest.StudySession;
import tempest.ui.ViewManager;

public class LineChart extends Chart {
  private static final long serialVersionUID = -1275171253819439097L;

  public LineChart(State state, ViewManager<Chart> manager) {
    super(state, manager);
    this.add(new JLabel(getName()));
    this.add(new ChartPanel(getChart()));
  }

  @Override
  public JFreeChart getChart() {
    JFreeChart chart = ChartFactory.createLineChart("Line Chart", "Date", "Time / mins", generateDataset(this.state),
        PlotOrientation.VERTICAL, true, false, false);
    return chart;
  }

  @Override
  public CategoryDataset generateDataset(State state) {
    final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    for (Module m : state.getModules()) {
      for (StudySession s : m.getStudySessions()) {
        dataset.addValue(s.duration.toMinutes(), m.getName(), StudySession.STORED_DATE_FORMAT.format(s.date));
      }
    }
    return dataset;
  }

  @Override
  public String getName() {
    return ChartTypes.LINE;
  }

}
