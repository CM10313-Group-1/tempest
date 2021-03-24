package tempest.GUI;

import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import tempest.GUI.components.BackButton;
import tempest.GUI.components.LinkButton;
import tempest.GUI.components.ModuleDropDown;

public class ManageModulesPage extends Page {
    private final GUIManager manager;
    private final ModuleDropDown moduleDropDown = new ModuleDropDown();

    private JButton addModuleButton;
    private JButton deleteModuleButton;
    private BackButton backButton;
    private JComboBox<Object> dropDown;

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

        dropDown = moduleDropDown.getModuleDropDown();

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
     * Updates the delete module button to be disabled if there are no modules to
     * delete.
     */
    public void update() {
        // If a module/modules have been created then the button is active again
        deleteModuleButton.setEnabled(dropDown.getItemCount() != 0);
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
