package tempest.GUI;
import tempest.GUI.components.BackButton;
import tempest.GUI.components.LinkButton;
import tempest.Module;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageSessionsPage extends Page implements ActionListener {
    private final GUIManager manager;

    private JButton delSessionsButton;
    private JButton addSessionsButton;

    private BackButton backButton;

    public ManageSessionsPage(GUIManager guiManager){
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

        delSessionsButton = new LinkButton("View and delete study sessions", manager.getPageName(DeleteSessionPage.class), this);
        addSessionsButton = new LinkButton("Add study sessions", manager.getPageName(AddSessionPage.class), this);

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

    // Disable delete button if no sessions
    public void toggleDeleteButton(Module[] modules) {
        for (Module m : modules) {
            if (m.getStudySessions().length > 0) {
                delSessionsButton.setEnabled(true);
                return;
            }
        }

        delSessionsButton.setEnabled(false);
    }

    public JButton getAddSessionsButton() {
        return addSessionsButton;
    }

    public JButton getDelSessionsButton() {
        return delSessionsButton;
    }

    /**
     * Used by tests to get this pages back button
     */
    public BackButton getBackButton() {
        return backButton;
    }
}
