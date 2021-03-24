package tempest.GUI.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import tempest.GUI.GUIManager;

public class BackButton extends JButton {
    private static final long serialVersionUID = -349591108764334577L;

    /**
     * Navigation button that sends the user back to the previous page.
     * 
     * @param manager
     */
    public BackButton(GUIManager manager) {
        super("Back");
        this.setFocusable(false);
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.swapToPrevCard();
            }
        });
    }
}
