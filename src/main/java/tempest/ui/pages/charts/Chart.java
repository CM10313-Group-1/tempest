package tempest.ui.pages.charts;

import tempest.Module;
import tempest.State;
import tempest.ui.GUIManager;
import tempest.ui.pages.Page;

/**
 * Generic chart class.
 */
public abstract class Chart extends Page {
    private static final long serialVersionUID = -2648755806373669402L;

    public State state;

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

    /**
     * Updates the colors used to display each module's data on the chart.
     * 
     * @param modules The {@link Module} modules to update.
     */
    public abstract void setModuleColors(Module[] modules);
}
