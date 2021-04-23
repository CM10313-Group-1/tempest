package tempest.ui.pages;

import tempest.ui.GUIManager;
import tempest.ui.PageManager;

import javax.swing.*;

/**
 * All page classes should extend this class
 */
public abstract class Page extends JPanel {
    private static final long serialVersionUID = -7384127634444815527L;

    public GUIManager manager;

    public Page(GUIManager manager){
        this.manager = manager;
    }

    /**
     * Ensures all pages have a getName() method
     *
     * The name of the page is used to identify the page in the {@link PageManager}
     *
     * @return String - Name of the page
     */
    public abstract String getName();
}
