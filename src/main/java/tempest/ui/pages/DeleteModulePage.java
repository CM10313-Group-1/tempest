package tempest.ui.pages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.*;

import tempest.Module;
import tempest.State;
import tempest.ui.ErrorMessage;
import tempest.ui.GUIManager;
import tempest.ui.components.ModuleDropDown;

public class DeleteModulePage extends Page implements ActionListener {
    private static final long serialVersionUID = 2589222088607882971L;

    private final State state;
    private final ModuleDropDown moduleDropDown = new ModuleDropDown();
    private final ErrorMessage errorMessage = new ErrorMessage();

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
        JPanel dropDownPanel = new JPanel();

        deleteButton = new JButton("Delete module");
        deleteButton.addActionListener(this);
        backPanel.add(deleteButton);

        dropDown = moduleDropDown.getModuleDropDown();
        dropDownPanel.add(dropDown);

        this.add(dropDownPanel);
        this.add(backPanel);
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
        boolean sessions = false;

        for (Module m : state.getModules()) {
            if (m.getName().equals(moduleName)) {
                if (m.getStudySessions().length > 0) {
                    sessions = true;
                }
                break;
            }
        }

        // Only showing the warning if the module has sessions
        if (sessions) {
            int response = errorMessage.showWarningMessage(this, "If you delete \"" + moduleName + "\" all of its study sessions will also be deleted.\nAre you sure you want to continue?");

            if (response == JOptionPane.NO_OPTION || response == JOptionPane.CLOSED_OPTION) {
                return;
            }
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

    public JButton getDeleteButton() {
        return deleteButton;
    }
}
