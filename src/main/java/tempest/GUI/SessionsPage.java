package tempest.GUI;
import tempest.GUI.components.ActionButtonPanel;
import tempest.GUI.components.BackButton;
import tempest.GUI.components.LinkButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SessionsPage extends Page implements ActionListener {
    private final GUIManager manager;

    private BackButton backButton;

    public SessionsPage(GUIManager guiManager){
        this.manager = guiManager;
    }

    @Override
    public String getName() {
        return "sessionsPage";
    }

    public JPanel getPanel(){
        JPanel backPanel = new JPanel();

        backButton = new BackButton(manager);
        backPanel.add(backButton);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new FlowLayout()); // TODO: Layout needed???

        JButton delSessionsButton = new LinkButton("View and delete study sessions", manager.getPageName(AddSessionPage.class), this);
        JButton addSessionsButton = new LinkButton("Add study sessions", manager.getPageName(DeleteSessionsPage.class), this);

        optionsPanel.add(delSessionsButton);
        optionsPanel.add(addSessionsButton);

        JPanel sessionsPanel = new JPanel();
        sessionsPanel.setLayout(new BoxLayout(sessionsPanel, BoxLayout.Y_AXIS));

        sessionsPanel.add(optionsPanel);
        sessionsPanel.add(backPanel);

        return sessionsPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        LinkButton source = (LinkButton) e.getSource();
        manager.swapCard(source.getDestination());
    }

    /**
     * Used by tests to get this pages back button
     */
    public BackButton getBackButton() {
        return backButton;
    }
}
