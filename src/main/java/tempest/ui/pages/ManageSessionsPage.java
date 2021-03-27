package tempest.ui.pages;
import tempest.ui.components.BackButton;
import tempest.ui.GUIManager;
import tempest.ui.components.LinkButton;
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

        setupUI();
    }

    @Override
    public String getName() {
        return "manageSessionsPage";
    }

    public void setupUI(){
        JPanel backPanel = new JPanel();

        backButton = new BackButton(manager);
        backPanel.add(backButton);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new FlowLayout()); // TODO: Layout needed???

        delSessionsButton = new LinkButton("View and delete study sessions", "deleteSessionPage", this);
        addSessionsButton = new LinkButton("Add study sessions", "addSessionPage", this);

        optionsPanel.add(delSessionsButton);
        optionsPanel.add(addSessionsButton);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(optionsPanel);
        this.add(backPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        LinkButton source = (LinkButton) e.getSource();
        manager.swapCard(source.getDestination());
    }

    // Disable delete button if no sessions
    //TODO: Use
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
