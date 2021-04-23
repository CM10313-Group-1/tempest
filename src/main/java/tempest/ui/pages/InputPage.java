package tempest.ui.pages;

import java.awt.event.ActionListener;

/**
 * Pages should implement this interface if they want to use a
 * clear button and have an enter button
 */
public interface InputPage extends ActionListener {
    /**
     * Used to clear input fields when the clear button is pressed
     */
    void clearInput();
}
