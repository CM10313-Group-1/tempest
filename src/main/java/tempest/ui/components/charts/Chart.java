package tempest.ui.components.charts;

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

  /**
   * Update the internal state of the chart with a new state.
   * 
   * @param state The current state of recorded data.
   */
  public abstract void updateChart(State state);
}
