package tempest.GUI.components;

import tempest.GUI.GUIManager;
import tempest.GUI.Page;

import javax.swing.*;

public class ActionButtonPanel {

    private CancelButton cancelButton;

    /**
     * Returns a button panel containing an enter button and a cancel button
     *
     * The enter button needs to be retrieved and handled in the class which calls
     * this method
     *
     * The cancel button is handled already
     *
     * @param manager Manager instance
     * @param page this (instance of class calling getButtonPanel)
     * @return JPanel
     */
    public JPanel getButtonPanel(GUIManager manager, Page page) {
        cancelButton = new CancelButton();

        JPanel buttonPanel = new JPanel();

        JButton enterButton = new JButton("Enter");
        enterButton.setFocusable(false);
        enterButton.addActionListener(page);

        buttonPanel.add(cancelButton.getCancelButton(manager));
        buttonPanel.add(enterButton);

        return buttonPanel;
    }

    public JButton getCancelButtonInstance() {
        return cancelButton.getCancelButtonInstance();
    }
}
