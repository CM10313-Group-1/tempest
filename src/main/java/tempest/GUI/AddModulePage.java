package tempest.GUI;

import java.awt.event.ActionEvent;

import javax.swing.*;

import tempest.Module;
import tempest.State;
import tempest.GUI.components.ActionButtonPanel;
import tempest.GUI.components.ClearButton;
import tempest.GUI.components.ModuleDropDown;

public class AddModulePage extends Page {
    private final State state;
    private final ModuleDropDown moduleDropDown;
    private final ActionButtonPanel actionButtonPanel;
    private final ErrorMessage errorMessage = new ErrorMessage();

    private JPanel modulePanel;
    private JTextField moduleNameInput;
    private JButton enterButton;

    public AddModulePage(State state, GUIManager guiManager) {
        this.state = state;
        this.actionButtonPanel = new ActionButtonPanel(guiManager, this);

        moduleDropDown = new ModuleDropDown(state);
    }

    @Override
    public String getName() {
        return "addModulePage";
    }

    public JPanel getPanel() {
        modulePanel = new JPanel();

        enterButton = (JButton) actionButtonPanel.getComponent(1);

        moduleNameInput = new JTextField(20);
        JLabel moduleInputLabel = new JLabel("Enter module name:");
        ClearButton clearButton = new ClearButton(this);

        JPanel inputPanel = new JPanel();
        inputPanel.add(moduleInputLabel);
        inputPanel.add(moduleNameInput);
        inputPanel.add(clearButton);

        modulePanel.add(inputPanel);
        modulePanel.add(actionButtonPanel);

        modulePanel.setLayout(new BoxLayout(modulePanel, BoxLayout.Y_AXIS));

        return modulePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == enterButton) {
            handleCreatingModule();
        }
    }

    /**
     * Handles the user trying to create a module
     */
    private void handleCreatingModule() {
        String moduleName = moduleNameInput.getText();

        if (moduleName.equals("")) {
            errorMessage(new Exception("Invalid module name"));
            return;
        }

        boolean uniqueName = true;

        // Checking if module name is unique
        for (Module m : state.getModules()) {
            if (moduleName.equals(m.getName())) {
                errorMessage(new Exception("Another module already has this name"));
                uniqueName = false;
                break;
            }
        }

        if (uniqueName) {
            addModule(moduleName);
            clearInput();

            System.out.println("Module successfully created");
        }
    }

    /**
     * Clears the module name JTextField input
     */
    @Override
    public void clearInput() {
        moduleNameInput.setText(""); // Clearing inputted module name
    }

    /**
     * Creates a pop up notifying the user of an error
     *
     * @param message The error message to be printed in the pop up
     */
    private void errorMessage(Exception message) {
        errorMessage.showMessage(modulePanel, message);
    }

    /**
     * Creates a new module using state and updates the module drop down in
     * GUIComponents
     *
     * @param moduleName Name of module to be created
     */
    private void addModule(String moduleName) {
        state.createModule(moduleName);
        moduleDropDown.addModule(moduleName);
    }

    public ActionButtonPanel getComponents() {
        return actionButtonPanel;
    }

    public JButton getEnterButton() {
        return enterButton;
    }

    public void setModuleNameInput(String name) {
        moduleNameInput.setText(name);
    }
}
