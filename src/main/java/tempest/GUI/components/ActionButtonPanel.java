package tempest.GUI.components;

import javax.swing.JButton;
import javax.swing.JPanel;

import tempest.GUI.GUIManager;
import tempest.GUI.Page;

public class ActionButtonPanel extends JPanel {
    private static final long serialVersionUID = 9196271090552224373L;
    private BackButton backButton;

    /**
     * Returns a button panel containing an enter button and a cancel button
     *
     * The enter button needs to be retrieved and handled in the class which calls
     * this method
     *
     * The cancel button is handled already
     *
     * @param manager Manager instance
     * @param page    this (instance of class calling getButtonPanel)
     * @return JPanel
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

    public JButton getCancelButtonInstance() {
        return backButton;
    }
}
