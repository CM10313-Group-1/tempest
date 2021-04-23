package tempest.ui.components;

import javax.swing.JButton;
import tempest.ui.pages.InputPage;

public class EnterButton extends JButton {
    private static final long serialVersionUID = 9196271090552224373L;

    /**
     * The created instance of EnterButton is a JButton so just add it to one of the
     * page's panel
     *
     * The enter button will call the calling pages actionPerformed method when clicked
     *
     * @param page    this (instance of class calling getButtonPanel). The class calling
     *                needs to implement the InputPage interface
     */
    public EnterButton(InputPage page) {
        super("Enter");
        this.setFocusable(false);
        this.addActionListener(page);
    }
}
