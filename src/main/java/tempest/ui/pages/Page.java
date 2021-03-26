package tempest.ui.pages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

/**
 * All page classes should extend this class
 */
public abstract class Page implements ActionListener {

    /**
     * Ensures all pages have a getName() method
     *
     * Used when adding all pages to the cardLayout in the manager
     *
     * The name of the page is used to identify the page in the cardLayout
     *
     * @return String - Name of the page
     */
    public abstract String getName();

    /**
     * Ensures all pages have a getPanel() method
     *
     * May also be useful if need to call page.getPanel()
     *
     * @return A JPanel holding all the necessary components for that specific page
     */
    public abstract JPanel getPanel();

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
