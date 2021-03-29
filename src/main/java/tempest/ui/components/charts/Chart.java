package tempest.ui.components.charts;

import org.jfree.chart.JFreeChart;
import org.jfree.data.general.Dataset;

import tempest.State;
import tempest.ui.View;
import tempest.ui.ViewManager;

/**
 * Generic chart class.
 */
public abstract class Chart extends View {
  private static final long serialVersionUID = -2648755806373669402L;
  public State state;
  public ViewManager<Chart> vm;

  public Chart(State state, ViewManager<Chart> manager) {
    this.state = state;
    this.vm = manager;
  }

  public abstract JFreeChart getChart();

  public abstract Dataset generateDataset(State state);
}
