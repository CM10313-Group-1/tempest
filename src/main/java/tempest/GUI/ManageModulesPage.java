package tempest.GUI;

import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import tempest.GUI.components.BackButton;
import tempest.GUI.components.ModuleDropDown;

public class ManageModulesPage extends Page {
    private final GUIManager manager;
    private final ModuleDropDown moduleDropDown = new ModuleDropDown();

    private JPanel manageModulesPanel;
    private JPanel pageSwapPanel;
    private JPanel backPanel;

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
        manageModulesPanel = new JPanel();
        pageSwapPanel = new JPanel();
        backPanel = new JPanel();

        dropDown = moduleDropDown.getModuleDropDown();

        addModuleButton = new JButton("Add a module");
        deleteModuleButton = new JButton("Delete a module");
        backButton = new BackButton(manager);

        addModuleButton.addActionListener(this);
        deleteModuleButton.addActionListener(this);
        backButton.addActionListener(this);

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
        Object source = e.getSource();

        if (source == addModuleButton) {
            manager.swapCard("addModulePage");
        } else if (source == deleteModuleButton) {
            manager.swapCard("deleteModulePage");
        }
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
