package tempest.ui.components;

import javax.swing.JButton;

import tempest.ui.pages.InputPage;

public class ClearButton extends JButton {
    private static final long serialVersionUID = -857346759811441764L;

    /**
     * The created instance of ClearButton is a clear button so just add it to the
     * page's panel
     *
     * The page calling this needs to implement inputPage
     *
     * @param inputPage this (instance of class calling getButtonPanel)
     */
    public ClearButton(InputPage inputPage) {
        super("Clear");
        this.setFocusable(false);
        this.addActionListener(e -> inputPage.clearInput());
    }
}
