package tempest.ui.pages;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import tempest.Module;
import tempest.ui.GUIManager;
import tempest.ui.components.BackButton;
import tempest.ui.components.LinkButton;

public class ManageSessionsPage extends Page {
private static final long serialVersionUID = -4290018724813073172L;
    private final LinkButton delSessionsButton = new LinkButton("View and delete study sessions", PageNames.DELETE_SESSION, this);
    private final LinkButton addSessionsButton = new LinkButton("Add study sessions", PageNames.ADD_SESSION, this);

    private BackButton backButton;

    public ManageSessionsPage(GUIManager guiManager){
        super(guiManager);

        setupUI();
    }

    @Override
    public String getName() {
        return PageNames.MANAGE_SESSIONS;
    }

    private void setupUI(){
        JPanel backPanel = new JPanel();

        backButton = new BackButton(manager);
        backPanel.add(backButton);

        JPanel optionsPanel = new JPanel();

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

    /**
     * Disables the add sessions button if there are no modules
     *
     * @param modules Modules array
     */
    public void setAddButtonActivity(Module[] modules) {
        addSessionsButton.setEnabled(modules.length != 0);
    }

    /**
     * Disables the delete sessions button if there are no sessions
     *
     * @param modules Modules array
     */
    public void setDeleteButtonActivity(Module[] modules) {
        for (Module m : modules) {
            if (m.getStudySessions().length > 0) {
                delSessionsButton.setEnabled(true);
                return;
            }
        }

        delSessionsButton.setEnabled(false);
    }

    public LinkButton getAddSessionsButton() {
        return addSessionsButton;
    }

    public LinkButton getDelSessionsButton() {
        return delSessionsButton;
    }

    /**
     * Used by tests to get this pages back button
     */
    public BackButton getBackButton() {
        return backButton;
    }


}
