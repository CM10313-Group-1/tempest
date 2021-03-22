package tempest.GUI;

import tempest.GUI.components.ActionButtonPanel;
import tempest.GUI.components.ModuleDropDown;
import tempest.Module;
import tempest.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddModulePage extends Page implements ActionListener{
    private final State state;
    private final GUIManager manager;
    private final GUIComponents components = new GUIComponents();
    private final ModuleDropDown dropDown = new ModuleDropDown();
    private final ActionButtonPanel actionButtonPanel = new ActionButtonPanel();
    private final ErrorMessage errorMessage = new ErrorMessage();

    private JPanel modulePanel;
    private JTextField moduleNameInput;
    private JButton enterButton;

    public AddModulePage(State state, GUIManager guiManager){
        this.state = state;
        this.manager = guiManager;
    }

    public JPanel getPanel(){
        modulePanel = new JPanel();

        JPanel buttonPanel = actionButtonPanel.getButtonPanel(manager, this);
        enterButton = (JButton) buttonPanel.getComponent(1);

        moduleNameInput = new JTextField(20);
        JLabel moduleInputLabel = new JLabel("Enter module name:");
        JButton clearButton = components.getClearButton(this);

        JPanel inputPanel = new JPanel();
        inputPanel.add(moduleInputLabel);
        inputPanel.add(moduleNameInput);
        inputPanel.add(clearButton);

        modulePanel.add(inputPanel, BorderLayout.NORTH);
        modulePanel.add(buttonPanel, BorderLayout.SOUTH);

        return modulePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();

        if(source == enterButton){
            handleCreatingModule();
        }
    }

    /**
     * Handles the user trying to create a module
     */
    private void handleCreatingModule() {
        String moduleName = moduleNameInput.getText();

        if (moduleName.equals("")) {
            errorMessage("Invalid module name");
            return;
        }

        boolean uniqueName = true;

        // Checking if module name is unique
        for (Module m : state.getModules()) {
            if (moduleName.equals(m.getName())) {
                errorMessage("Another module already has this name");
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
    public void clearInput() {
        moduleNameInput.setText(""); // Clearing inputted module name
    }

    /**
     * Creates a pop up notifying the user of an error
     *
     * @param message The error message to be printed in the pop up
     */
    public void errorMessage(String message) {
        errorMessage.showMessage(modulePanel, message);
    }

    /**
     * Creates a new module using state and updates the module drop down
     * in GUIComponents
     *
     * @param moduleName Name of module to be created
     */
    public void addModule(String moduleName) {
        state.createModule(moduleName);
        dropDown.addModule(moduleName);
    }

    /**
     * Removes the module using state and updates the module drop down
     * in GUIComponents
     *
     * @param moduleName Name of module to be removed
     */
    public void removeModule(String moduleName) {
        state.deleteModule(moduleName);
        dropDown.removeModule(moduleName);
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
