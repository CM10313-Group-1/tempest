package tempest.GUI.components;

import javax.swing.JButton;

import tempest.GUI.Page;

public class ClearButton extends JButton {
    private static final long serialVersionUID = -857346759811441764L;

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
    public ClearButton(Page page) {
        super("Clear");
        this.setFocusable(false);
        this.addActionListener(e -> page.clearInput());
    }
}
