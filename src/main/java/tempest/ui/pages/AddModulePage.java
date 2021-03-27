package tempest.ui.pages;

import java.awt.event.ActionEvent;

import javax.swing.*;

import tempest.Module;
import tempest.State;
import tempest.ui.ErrorMessage;
import tempest.ui.GUIManager;
import tempest.ui.components.ActionButtonPanel;
import tempest.ui.components.ClearButton;
import tempest.ui.components.ModuleDropDown;

public class AddModulePage extends Page {
    private static final long serialVersionUID = -6175924935345590918L;

    private final State state;
    private final ModuleDropDown moduleDropDown = new ModuleDropDown();
    private final ActionButtonPanel actionButtonPanel;
    private final ErrorMessage errorMessage = new ErrorMessage();

    private JTextField moduleNameInput;
    private JButton enterButton;

    public AddModulePage(State state, GUIManager guiManager) {
        super();

        this.state = state;
        this.actionButtonPanel = new ActionButtonPanel(guiManager, this);
        setupUI();
    }

    @Override
    public String getName() {
        return "addModulePage";
    }

    private void setupUI() {
        enterButton = (JButton) actionButtonPanel.getComponent(1);

        moduleNameInput = new JTextField(20);
        JLabel moduleInputLabel = new JLabel("Enter module name:");
        ClearButton clearButton = new ClearButton(this);

        JPanel inputPanel = new JPanel();
        inputPanel.add(moduleInputLabel);
        inputPanel.add(moduleNameInput);
        inputPanel.add(clearButton);

        this.add(inputPanel);
        this.add(actionButtonPanel);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
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

        moduleName = moduleName.strip();

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
        errorMessage.showMessage(this, message);
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

    public ActionButtonPanel getActionButtons() {
        return actionButtonPanel;
    }

    public JButton getEnterButton() {
        return enterButton;
    }

    public void setModuleNameInput(String name) {
        moduleNameInput.setText(name);
    }
}
