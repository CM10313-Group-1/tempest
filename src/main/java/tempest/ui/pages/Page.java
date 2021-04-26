package tempest.ui.pages;

import tempest.ui.GUIManager;
import tempest.ui.PageManager;
import tempest.ui.components.BackButton;

import javax.swing.JPanel;

/**
 * All page classes should extend this class
 */
public abstract class Page extends JPanel {
    private static final long serialVersionUID = -7384127634444815527L;

    protected GUIManager manager;
    protected JPanel backPanel = new JPanel();
    private final BackButton backButton;

    public Page(GUIManager manager){
        this.manager = manager;
        backPanel.add(backButton = new BackButton(manager));
    }

    /**
     * Ensures all pages have a getName() method
     *
     * The name of the page is used to identify the page in the {@link PageManager}
     *
     * @return String - Name of the page
     */
    public abstract String getName();

    public BackButton getBackButton() {
        return backButton;
    }
}
