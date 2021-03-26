package tempest.ui.components;

import javax.swing.JButton;

import tempest.ui.GUIManager;

public class BackButton extends JButton {
    private static final long serialVersionUID = -349591108764334577L;

    /**
     * Navigation button that sends the user back to the previous page.
     * 
     * @param manager The instance of manager passed into the page class
     */
    public BackButton(GUIManager manager) {
        super("Back");
        this.setFocusable(false);
        this.addActionListener(e -> manager.swapToPrevCard());
    }
}
