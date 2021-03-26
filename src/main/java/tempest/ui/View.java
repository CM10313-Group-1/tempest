package tempest.ui;

import javax.swing.JPanel;

public abstract class View {
    /**
     * Ensures all views have a getName() method
     *
     * The name of the view is used to identify the view in the {@link ViewManager}
     *
     * @return String - Name of the view
     */
    public abstract String getName();

    /**
     * Ensures all views have a getPanel() method
     *
     * May also be useful if need to call view.getPanel()
     *
     * @return A JPanel holding all the necessary components for that specific view
     */
    public abstract JPanel getPanel();
}
