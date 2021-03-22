package tempest.GUI;
import tempest.GUI.components.ActionButtonPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class sessionsPage extends Page implements ActionListener {
    private final GUIManager manager;
    private final ActionButtonPanel actionButtonPanel = new ActionButtonPanel();

    private JButton deleteSessions;
    private JButton addSessions;

    public sessionsPage(GUIManager guiManager){
        this.manager = guiManager;
    }

    public JPanel getPanel(){
        JPanel cancelPanel = new JPanel();

        JButton cancelButton = actionButtonPanel.getCancelButton(manager);
        cancelPanel.add(cancelButton);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new FlowLayout());

        deleteSessions = new JButton("Delete study sessions");
        addSessions = new JButton("Add study sessions");

        deleteSessions.addActionListener(this);
        addSessions.addActionListener(this);

        deleteSessions.setFocusable(false);
        addSessions.setFocusable(false);

        optionsPanel.add(deleteSessions);
        optionsPanel.add(addSessions);

        JPanel sessionsPanel = new JPanel();
        sessionsPanel.setLayout(new BoxLayout(sessionsPanel, BoxLayout.Y_AXIS));

        sessionsPanel.add(optionsPanel);
        sessionsPanel.add(cancelPanel);

        return sessionsPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();

        if(source == addSessions){
            manager.swapCard("addSession");
        } else if (source == deleteSessions) {
            manager.swapCard("deleteSession");
        }
    }

    /**
     * Used by tests to get this pages cancel button
     */
    public ActionButtonPanel getComponents() {
        return actionButtonPanel;
    }
}
