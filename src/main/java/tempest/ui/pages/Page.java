package tempest.ui.pages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import tempest.ui.GUIManager;
import tempest.ui.ViewManager;

import javax.swing.*;

/**
 * All page classes should extend this class
 */
public abstract class Page extends JPanel implements ActionListener {
    private static final long serialVersionUID = -7384127634444815527L;

    public GUIManager manager;

    public Page(GUIManager manager){
        this.manager = manager;
    }

    /**
     * Ensures all pages have a getName() method
     *
     * The name of the page is used to identify the page in the {@link ViewManager}
     *
     * @return String - Name of the page
     */
    public abstract String getName();

    public void actionPerformed(ActionEvent e) {
        System.err.println("One of the page classes your using needs an actionPerformed() method");
    }
}
