package tempest.GUI;

import tempest.Module;
import tempest.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//TODO:
// - Check if entered module name already exists using state equals()
// - Is the current method of getting the enterButton good???
// - Better if pressing enter sends user back a screen???

public class AddModulePage extends Page implements ActionListener{
    private final State state;
    private final GUIManager manager;

    private JTextField moduleNameInput;
    private JButton enterButton;

    public AddModulePage(State state, GUIManager guiManager){
        this.state = state;
        this.manager = guiManager;
    }

    public JPanel getPanel(){
        JPanel modulePage = new JPanel();

        JPanel buttonPanel = new GUIComponents().getButtonPanel(manager, this);
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
            manager.addModule(moduleName);
            moduleNameInput.setText(""); // Clearing inputted module name

            System.out.println("Module successfully created");
        }
    }
}
