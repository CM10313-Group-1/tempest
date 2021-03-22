package tempest.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * All page classes should extend this class
 */
public abstract class Page implements ActionListener {

    /**
     * Ensures all pages have a getPanel() method
     *
     * May also be useful if need to call page.getPanel()
     *
     * @return A JPanel holding all the necessary components for that specific page
     */
    public abstract JPanel getPanel();


    // Can't be abstract because there might be a page class that doesn't implement ActionListener
    public void actionPerformed(ActionEvent e) {
        System.err.println("One of the page classes your using needs an actionPerformed() method");
    }

    public void clearInput() {
        System.err.println("One of the page classes your using needs an clearInput() method");
    }
}
