package tempest.GUI;

import tempest.Module;
import tempest.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//TODO:
// - Better if pressing enter sends user back a screen? - Feels more like you've entered something

public class AddModulePage extends Page implements ActionListener{
    private final State state;
    private final GUIManager manager;
    private final GUIComponents components = new GUIComponents();

    private JTextField moduleNameInput;
    private JButton enterButton;

    public AddModulePage(State state, GUIManager guiManager){
        this.state = state;
        this.manager = guiManager;
    }

    public JPanel getPanel(){
        JPanel modulePage = new JPanel();

        JPanel buttonPanel = components.getButtonPanel(manager, this);
        enterButton = (JButton) buttonPanel.getComponent(1);

        moduleNameInput = new JTextField(20);
        JLabel moduleInputLabel = new JLabel("Enter module name:");

        JPanel inputPanel = new JPanel();
        inputPanel.add(moduleInputLabel);
        inputPanel.add(moduleNameInput);

        modulePage.add(inputPanel, BorderLayout.NORTH);
        modulePage.add(buttonPanel, BorderLayout.SOUTH);

        return modulePage;
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
            System.out.println("Invalid module name");
            return;
        }

        boolean uniqueName = true;

        // Checking if module name is unique
        for (Module m : state.getModules()) {
            if (moduleName.equals(m.getName())) {
                System.out.println("Another module already has this name");
                uniqueName = false;
                break;
            }
        }

        if (uniqueName) {
            addModule(moduleName);
            moduleNameInput.setText(""); // Clearing inputted module name

            System.out.println("Module successfully created");
        }
    }

    /**
     * Creates a new module using state and updates the module drop down
     * in GUIComponents
     *
     * @param moduleName Name of module to be created
     */
    public void addModule(String moduleName) {
        state.createModule(moduleName);
        components.addModule(moduleName);
    }

    /**
     * Removes the module using state and updates the module drop down
     * in GUIComponents
     *
     * @param moduleName Name of module to be removed
     */
    public void removeModule(String moduleName) {
        state.deleteModule(moduleName);
        components.removeModule(moduleName);
    }

    public GUIComponents getComponents() {
        return components;
    }

    public void setModuleNameInput(String name) {
        moduleNameInput.setText(name);
    }

    public JButton getEnterButton() {
        return enterButton;
    }
}
