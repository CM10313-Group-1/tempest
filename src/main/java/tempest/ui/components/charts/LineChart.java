package tempest.ui.components.charts;

import javax.swing.JLabel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
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
    this.add(createChart());
  }

  /**
   * Generates the chart as a panel that can be added to the UI.
   * 
   * @return {@link ChartPanel} of the chart.
   */
  private ChartPanel createChart() {
    CategoryDataset dataset = generateDataset(state);
    return new ChartPanel(ChartFactory.createLineChart("Line Chart", "Date", "Time / mins", dataset,
        PlotOrientation.VERTICAL, true, false, false));
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
