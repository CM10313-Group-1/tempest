package tempest.ui.components;

import javax.swing.JButton;
import tempest.ui.pages.InputPage;

public class EnterButton extends JButton {
    private static final long serialVersionUID = 9196271090552224373L;

    /**
     * The created instance of ActionButtonPanel is a JPanel So just add it to the
     * page's panel
     *
     * The panel contains an enter button and a back button
     *
     * The enter button needs to be retrieved and handled in the class which calls
     * this method
     *
     * The back button is handled already
     *
     * @param page    this (instance of class calling getButtonPanel). The class calling
     *                needs to implement the inputPage interface
     */
    public EnterButton(InputPage page) {
        super("Enter");
        this.setFocusable(false);
        this.addActionListener(page);
    }
}
