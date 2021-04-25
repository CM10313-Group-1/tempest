package tempest.ui.pages;

import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import tempest.Module;
import tempest.State;
import tempest.ui.ErrorMessage;
import tempest.ui.GUIManager;
import tempest.ui.components.EnterButton;
import tempest.ui.components.ClearButton;
import tempest.ui.components.ModuleDropDown;

public class AddModulePage extends Page implements InputPage {
    private static final long serialVersionUID = -6175924935345590918L;

    private final State state;
    private final ModuleDropDown moduleDropDown = new ModuleDropDown();
    private final EnterButton enterButton;
    private final ErrorMessage errorMessage = new ErrorMessage();

    private JTextField moduleNameInput;

    public AddModulePage(State state, GUIManager guiManager) {
        super(guiManager);

        this.state = state;
        this.enterButton = new EnterButton(this);
        setupUI();
    }

    @Override
    public String getName() {
        return PageNames.ADD_MODULE;
    }

    private void setupUI() {
        moduleNameInput = new JTextField(30);
        moduleNameInput.setDocument(new TextInputLimit(80));

        JLabel moduleInputLabel = new JLabel("Enter module name:");
        ClearButton clearButton = new ClearButton(this);

        JPanel inputPanel = new JPanel();
        inputPanel.add(moduleInputLabel);
        inputPanel.add(moduleNameInput);
        inputPanel.add(clearButton);

        backPanel.add(enterButton);

        this.add(inputPanel);
        this.add(backPanel);

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

        // Checking if module name is unique
        for (Module m : state.getModules()) {
            if (moduleName.equals(m.getName())) {
                errorMessage(new Exception("Another module already has this name"));
                return;
            }
        }

        addModule(moduleName);
        clearInput();

        System.out.println("Module successfully created");
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
     * GUIComponents`
     *
     * @param moduleName Name of module to be created
     */
    private void addModule(String moduleName) {
        state.createModule(moduleName);
        moduleDropDown.addModule(moduleName);
    }

    public EnterButton getEnterButton() {
        return enterButton;
    }

    public void setModuleName(String name) {
        moduleNameInput.setText(name);
    }
}

//https://www.tutorialspoint.com/how-can-we-limit-the-number-of-characters-inside-a-jtextfield-in-java
class TextInputLimit extends PlainDocument {
    private final int limit;

    TextInputLimit (int limit) {
        super();
        this.limit = limit;
    }

    public void insertString (int offset, String  str, AttributeSet attr) throws BadLocationException {
        if (str == null) return;

        if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str, attr);
        }
    }
}
