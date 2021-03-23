package tempest.GUI.components;

import tempest.GUI.GUIManager;
import tempest.GUI.Page;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionButtonPanel implements ActionListener {

    private JButton cancelButton;

    private GUIManager manager;

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
        this.manager = manager;

        JPanel buttonPanel = new JPanel();

        JButton enterButton = new JButton("Enter");
        enterButton.setFocusable(false);
        enterButton.addActionListener(page);

        buttonPanel.add(getCancelButton(manager));
        buttonPanel.add(enterButton);

        return buttonPanel;
    }

    public JButton getCancelButton(GUIManager manager) {
        this.manager = manager;

        cancelButton = new JButton("Cancel");
        cancelButton.setFocusable(false);
        cancelButton.addActionListener(this);

        return cancelButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelButton) {
            manager.swapToPrevCard();
        }
    }

    /**
     * Returns a cancel button
     *
     * Used by the test classes to get a pages cancel button
     *
     * @return JButton
     */
    public JButton getCancelButtonInstance() {
        return cancelButton;
    }


}
