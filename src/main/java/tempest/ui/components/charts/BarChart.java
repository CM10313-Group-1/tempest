package tempest.ui.components.charts;

import javax.swing.JLabel;

import org.jfree.data.general.Dataset;

import tempest.State;
import tempest.ui.ViewManager;

public class BarChart extends Chart {
  private static final long serialVersionUID = -2288959674462946064L;

  public BarChart(State state, ViewManager<Chart> manager) {
    super(state, manager);
    this.add(new JLabel(getName()));
  }

  @Override
  public String getName() {
    return ChartTypes.BAR;
  }

  @Override
  public Dataset generateDataset(State state) {
    // TODO Auto-generated method stub
    return null;
  }

}
