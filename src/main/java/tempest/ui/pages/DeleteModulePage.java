package tempest.ui.pages;

import java.awt.event.ActionEvent;
import java.util.Objects;

import javax.swing.*;

import tempest.State;
import tempest.ui.ErrorMessage;
import tempest.ui.GUIManager;
import tempest.ui.components.BackButton;
import tempest.ui.components.ModuleDropDown;

public class DeleteModulePage extends Page {
    private static final long serialVersionUID = 2589222088607882971L;

    private final State state;
    private final ModuleDropDown moduleDropDown = new ModuleDropDown();
    private final ErrorMessage errorMessage = new ErrorMessage();

    private BackButton backButton;
    private JButton deleteButton;
    private JComboBox<Object> dropDown;

    public DeleteModulePage(State state, GUIManager guiManager) {
        super(guiManager);
        this.state = state;
        setupUI();
    }

    @Override
    public String getName() {
        return PageNames.DELETE_MODULE;
    }

    private void setupUI() {
        JPanel buttonPanel = new JPanel();
        JPanel dropDownPanel = new JPanel();

        backButton = new BackButton(manager);
        deleteButton = new JButton("Delete module");

        deleteButton.addActionListener(this);

        buttonPanel.add(backButton);
        buttonPanel.add(deleteButton);

        dropDown = moduleDropDown.getModuleDropDown();

        dropDownPanel.add(dropDown);

        this.add(dropDownPanel);
        this.add(buttonPanel);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
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
        String moduleName = Objects.requireNonNull(dropDown.getSelectedItem()).toString();
        int response = errorMessage.showWarningMessage(this, "If you delete \"" + moduleName + "\" all of its study sessions will also be deleted.\nAre you sure you want to continue?");

        if (response == JOptionPane.NO_OPTION || response == JOptionPane.CLOSED_OPTION) {
            return;
        }

        state.deleteModule(moduleName);
        moduleDropDown.removeModule(moduleName);

        // Swaps to prev card if no modules to delete
        if (dropDown.getItemCount() == 0) {
            manager.swapToPrevCard();
            return;
        }

        System.out.println(moduleName + " successfully deleted.");
    }

    public BackButton getBackButton() {
        return backButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }
}
