package tempest.GUI;
import tempest.GUI.components.ActionButtonPanel;
import tempest.GUI.components.BackButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SessionsPage extends Page implements ActionListener {
    private final GUIManager manager;

    private JButton deleteSessions;
    private JButton addSessions;

    private BackButton backButton;

    public SessionsPage(GUIManager guiManager){
        this.manager = guiManager;
    }

    @Override
    public String getName() {
        return null;
    }

    public JPanel getPanel(){
        JPanel backPanel = new JPanel();

        backButton = new BackButton(manager);
        backPanel.add(backButton);

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
        sessionsPanel.add(backPanel);

        return sessionsPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();

        if(source == addSessions){
            manager.swapCard("addSessionPage");
        } else if (source == deleteSessions) {
            manager.swapCard("deleteSessionPage");
        }
    }

    /**
     * Used by tests to get this pages back button
     */
    public BackButton getBackButton() {
        return backButton;
    }
}
