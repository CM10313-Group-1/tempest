package tempest.GUI;

import javax.swing.*;

public class GUIComponents {
    /**
     * Returns a clear button
     *
     * The page calling this needs a clearInput() method
     *
     * Just add this button to the page's panel
     *
     * @param page this (instance of class calling getButtonPanel)
     * @return JButton - A 'Clear' button
     */
    public JButton getClearButton(Page page) {
        JButton clearButton = new JButton("Clear");
        clearButton.setFocusable(false);

        clearButton.addActionListener(e -> page.clearInput());

        return clearButton;
    }
}
