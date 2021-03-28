package tempest.ui.components.charts;

import javax.swing.JLabel;

import org.jfree.chart.JFreeChart;

import tempest.State;
import tempest.ui.ViewManager;

public class PieChart extends Chart {
  private static final long serialVersionUID = 7074811797165362922L;

  public PieChart(State state, ViewManager<Chart> manager) {
    super(state, manager);
    this.add(new JLabel(getName()));
  }

  @Override
  public JFreeChart getChart() {
    return null;
  }

  @Override
  public String getName() {
    return ChartTypes.PIE;
  }

}
