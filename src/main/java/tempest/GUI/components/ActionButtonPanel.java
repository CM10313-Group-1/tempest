package tempest.GUI.components;

import tempest.GUI.GUIManager;
import tempest.GUI.Page;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//TODO:
// - Rename getCancelButton()
// - Have a cancelButton() method/class to be called to get just a cancel button?


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

        cancelButton = new JButton("Cancel");
        JButton enterButton = new JButton("Enter");

        cancelButton.setFocusable(false);
        enterButton.setFocusable(false);

        cancelButton.addActionListener(this);
        enterButton.addActionListener(page);

        buttonPanel.add(cancelButton);
        buttonPanel.add(enterButton);

        return buttonPanel;
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
    public JButton getCancelButton() {
        return cancelButton;
    }


}
