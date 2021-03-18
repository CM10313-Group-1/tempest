package tempest.GUI;

import tempest.Module;
import tempest.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class AddModulePage extends JFrame implements ActionListener{
    private State state;
    private GUIManager guiManager;

    private ArrayList<Module> modules;
    private JTextField moduleNameInput;
    private JLabel moduleInputLabel;
    private JPanel buttonPanel;
    private JButton enterButton;
    private JButton cancelButton;

    public AddModulePage(State state, GUIManager guiManager){
        this.state = state;
        this.guiManager = guiManager;
    }

    public Container getPanel(){
        modules = new ArrayList<>(Arrays.asList(state.getModules()));

        buttonPanel = new JPanel();

        moduleNameInput = new JTextField(20);
        moduleInputLabel = new JLabel("Enter module name:");

        cancelButton = new JButton("Cancel");
        enterButton = new JButton("Enter");

        cancelButton.setFocusable(false);
        enterButton.setFocusable(false);

        cancelButton.addActionListener(this);
        enterButton.addActionListener(this);

        buttonPanel.add(enterButton);
        buttonPanel.add(cancelButton);

        JPanel inputPanel = new JPanel();
        inputPanel.add(moduleInputLabel);
        inputPanel.add(moduleNameInput);

        getContentPane().add(inputPanel, BorderLayout.NORTH);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        return getContentPane();
    }

    private void handleCreatingModule() {
        String moduleName = moduleNameInput.getText();

        if (moduleName.equals("")) {
            System.out.println("Invalid module name");
            return;
        }

        boolean uniqueName = true;

        // Checking if module name is unique
        for (Module m : modules) {
            if (moduleName.equals(m.getName())) {
                System.out.println("Another module already has this name");
                uniqueName = false;
                break;
            }
        }

        if (uniqueName) {
            state.createModule(moduleName);

            guiManager.changeFrame(2);

            System.out.println("Module successfully created");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();

        if(source == enterButton){
            handleCreatingModule();
        }
        else if(source == cancelButton){
            guiManager.changeFrame(1);
        }
    }
}
