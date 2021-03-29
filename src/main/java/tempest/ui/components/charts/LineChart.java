package tempest.ui.components.charts;

import javax.swing.JLabel;

import org.jfree.chart.JFreeChart;

import tempest.State;
import tempest.ui.ViewManager;

public class LineChart extends Chart {
  private static final long serialVersionUID = -1275171253819439097L;

  public LineChart(State state, ViewManager<Chart> manager) {
    super(state, manager);
    this.add(new JLabel(getName()));
  }

  @Override
  public JFreeChart getChart() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getName() {
    return ChartTypes.LINE;
  }

}
