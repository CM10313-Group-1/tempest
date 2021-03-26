package tempest.ui.pages;

import java.awt.event.ActionEvent;
import java.util.Objects;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import tempest.State;
import tempest.ui.GUIManager;
import tempest.ui.components.BackButton;
import tempest.ui.components.ModuleDropDown;

public class DeleteModulePage extends Page {
    private static final long serialVersionUID = 2589222088607882971L;

    private final State state;
    private final GUIManager manager;
    private final ModuleDropDown moduleDropDown = new ModuleDropDown();

    private BackButton backButton;
    private JButton deleteButton;
    private JComboBox<Object> dropDown;

    public DeleteModulePage(State state, GUIManager guiManager) {
        super();
        this.state = state;
        this.manager = guiManager;
        setupUI();
    }

    @Override
    public String getName() {
        return "deleteModulePage";
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
        // Swaps to prev card if no modules to delete
        if (dropDown.getItemCount() == 0) {
            System.err.println("Attempting to delete modules when there are none");
            return;
        }

        String moduleName = Objects.requireNonNull(dropDown.getSelectedItem()).toString();

        state.deleteModule(moduleName);
        moduleDropDown.removeModule(moduleName);

        System.out.println(moduleName + " successfully deleted.");
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }
}
