package tempest.GUI;

import java.awt.event.ActionEvent;
import java.util.Objects;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import tempest.State;
import tempest.GUI.components.BackButton;
import tempest.GUI.components.ModuleDropDown;

public class DeleteModulePage extends Page {
    private final State state;
    private final GUIManager manager;
    private final ModuleDropDown moduleDropDown;

    private BackButton backButton;
    private JButton deleteButton;
    private JComboBox<Object> dropDown;

    public DeleteModulePage(State state, GUIManager guiManager) {
        this.state = state;
        this.manager = guiManager;

        moduleDropDown = new ModuleDropDown(state);
    }

    @Override
    public String getName() {
        return "deleteModulePage";
    }

    public JPanel getPanel() {
        JPanel deleteModulePanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel dropDownPanel = new JPanel();

        backButton = new BackButton(manager);
        deleteButton = new JButton("Delete module");

        deleteButton.addActionListener(this);

        buttonPanel.add(backButton);
        buttonPanel.add(deleteButton);

        dropDown = moduleDropDown.getModuleDropDown();

        dropDownPanel.add(dropDown);

        deleteModulePanel.add(dropDownPanel);
        deleteModulePanel.add(buttonPanel);

        deleteModulePanel.setLayout(new BoxLayout(deleteModulePanel, BoxLayout.Y_AXIS));

        return deleteModulePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == deleteButton) {
            handleDeletingModule();
        }
    }

    /**
     * Handles deleting the module and updating the module drop down
     */
    private void handleDeletingModule() {
        // Checks if there are no modules to delete
        if (dropDown.getItemCount() == 0) {
            System.err.println("Attempting to delete modules when there are none");
            return;
        }

        String moduleName = Objects.requireNonNull(dropDown.getSelectedItem()).toString();

        state.deleteModule(moduleName);
        moduleDropDown.removeModule(moduleName);

        // Swaps to the previous card if there are no more modules left
        if (dropDown.getItemCount() == 0) {
            manager.swapToPrevCard();
        }

        System.out.println(moduleName + " successfully deleted.");
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }
}
