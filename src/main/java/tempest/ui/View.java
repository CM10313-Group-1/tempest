package tempest.ui;

import javax.swing.JPanel;

public abstract class View extends JPanel {
    private static final long serialVersionUID = -917650974601474363L;

    /**
     * Ensures all views have a getName() method
     *
     * The name of the view is used to identify the view in the {@link ViewManager}
     *
     * @return String - Name of the view
     */
    public abstract String getName();
}
