package tempest.ui.pages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import tempest.ui.GUIManager;
import tempest.ui.View;

/**
 * All page classes should extend this class
 */
public abstract class Page extends View implements ActionListener {
    private static final long serialVersionUID = -7384127634444815527L;
    public GUIManager manager;

    public Page(GUIManager manager){
        this.manager = manager;
    }

    // Can't be abstract because there might be a page class that doesn't implement
    // ActionListener
    public void actionPerformed(ActionEvent e) {
        System.err.println("One of the page classes your using needs an actionPerformed() method");
    }

    /**
     * Used by a clear button to clear input fields in a page
     */
    public void clearInput() {
        System.err.println("One of the page classes your using needs an clearInput() method");
    }

}
