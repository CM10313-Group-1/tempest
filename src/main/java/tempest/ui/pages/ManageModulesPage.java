package tempest.ui.pages;

import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import tempest.ui.components.BackButton;
import tempest.ui.GUIManager;
import tempest.ui.components.LinkButton;
import tempest.Module;

public class ManageModulesPage extends Page {
    private final GUIManager manager;

    private JButton addModuleButton;
    private JButton deleteModuleButton;
    private BackButton backButton;

    public ManageModulesPage(GUIManager guiManager) {
        this.manager = guiManager;
    }

    @Override
    public String getName() {
        return "manageModulesPage";
    }

    public JPanel getPanel() {
        JPanel manageModulesPanel = new JPanel();
        JPanel pageSwapPanel = new JPanel();
        JPanel backPanel = new JPanel();

        addModuleButton = new LinkButton("Add a module", manager.getPageName(AddModulePage.class), this);
        deleteModuleButton = new LinkButton("Delete a module", manager.getPageName(DeleteModulePage.class), this);
        backButton = new BackButton(manager);

        pageSwapPanel.add(deleteModuleButton);
        pageSwapPanel.add(addModuleButton);
        backPanel.add(backButton);

        manageModulesPanel.add(pageSwapPanel);
        manageModulesPanel.add(backPanel);

        manageModulesPanel.setLayout(new BoxLayout(manageModulesPanel, BoxLayout.Y_AXIS));

        return manageModulesPanel;
    }

    /**
     * Disables the delete button if there are no modules, otherwise the
     * button is enabled
     *
     * @param modules Array containing all the modules
     */
    public void toggleDeleteButton(Module[] modules) {
        // If a module/modules have been created then the button is active again
        deleteModuleButton.setEnabled(modules.length != 0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LinkButton source = (LinkButton) e.getSource();
        manager.swapCard(source.getDestination());
    }

    public JButton getAddModuleButton() {
        return addModuleButton;
    }

    public JButton getDeleteModuleButton() {
        return deleteModuleButton;
    }

    public JButton getBackButton() {
        return backButton;
    }
}
