package tempest.ui.pages.charts;

import tempest.State;
import tempest.ui.GUIManager;
import tempest.ui.pages.Page;

/**
 * Generic chart class.
 */
public abstract class Chart extends Page {
    private static final long serialVersionUID = -2648755806373669402L;

    protected State state;

    public Chart(State state, GUIManager manager) {
        super(manager);
        this.state = state;
    }

    /**
     * Update the internal state of the chart with a new state.
     *
     * @param state The current state of recorded data.
     */
    public abstract void updateChart(State state);
}
