package tempest.ui.components;

import javax.swing.JButton;
import javax.swing.JPanel;

import tempest.ui.GUIManager;
import tempest.ui.pages.Page;

public class ActionButtonPanel extends JPanel {
    private static final long serialVersionUID = 9196271090552224373L;
    private final BackButton backButton;

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
     * @param manager Manager instance
     * @param page    this (instance of class calling getButtonPanel)
     */
    public ActionButtonPanel(GUIManager manager, Page page) {
        super();
        this.backButton = new BackButton(manager);
        JButton enterButton = new JButton("Enter");
        enterButton.setFocusable(false);
        enterButton.addActionListener(page);

        this.add(backButton);
        this.add(enterButton);

    }

    public JButton getBackButtonInstance() {
        return backButton;
    }
}
