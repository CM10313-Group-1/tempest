package tempest.ui.pages;

import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import tempest.Module;
import tempest.ui.GUIManager;
import tempest.ui.components.BackButton;
import tempest.ui.components.LinkButton;

public class ManageModulesPage extends Page {
    private static final long serialVersionUID = -3556076472382354523L;

    private final LinkButton addModuleButton = new LinkButton("Add a module", PageNames.ADD_MODULE, this);
    private final LinkButton deleteModuleButton = new LinkButton("Delete a module", PageNames.DELETE_MODULE, this);

    private BackButton backButton;

    public ManageModulesPage(GUIManager guiManager) {
        super(guiManager);

        setupUI();
    }

    @Override
    public String getName() {
        return PageNames.MANAGE_MODULES;
    }

    private void setupUI() {
        JPanel pageSwapPanel = new JPanel();
        JPanel backPanel = new JPanel();

        backButton = new BackButton(manager);

        pageSwapPanel.add(deleteModuleButton);
        pageSwapPanel.add(addModuleButton);
        backPanel.add(backButton);

        this.add(pageSwapPanel);
        this.add(backPanel);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    /**
     * Disables the delete button if there are no modules, otherwise the
     * button is enabled
     *
     * @param modules Array containing all the modules
     */
    public void setButtonActivity(Module[] modules) {
        // If a module/modules have been created then the button is active again
        deleteModuleButton.setEnabled(modules.length != 0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LinkButton source = (LinkButton) e.getSource();
        manager.swapCard(source.getDestination());
    }

    public LinkButton getAddModuleButton() {
        return addModuleButton;
    }

    public LinkButton getDeleteModuleButton() {
        return deleteModuleButton;
    }

    public JButton getBackButton() {
        return backButton;
    }
}
