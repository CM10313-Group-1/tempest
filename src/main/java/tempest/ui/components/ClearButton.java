package tempest.ui.components;

import javax.swing.JButton;

import tempest.ui.pages.Page;

public class ClearButton extends JButton {
    private static final long serialVersionUID = -857346759811441764L;

    /**
     * The created instance of ClearButton is a clear button so just add it to the
     * page's panel
     *
     * The page calling this needs a clearInput() method
     *
     * @param page this (instance of class calling getButtonPanel)
     */
    public ClearButton(Page page) {
        super("Clear");
        this.setFocusable(false);
        this.addActionListener(e -> page.clearInput());
    }
}
