package tempest.GUI.components;

import tempest.GUI.GUIManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CancelButton implements ActionListener {

    private GUIManager manager;

    private JButton cancelButton;

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
