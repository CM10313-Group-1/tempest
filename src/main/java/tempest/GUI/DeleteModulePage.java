package tempest.GUI;

import tempest.GUI.components.ModuleDropDown;
import tempest.State;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class DeleteModulePage extends Page{
    private final State state;
    private final GUIManager manager;
    private final ModuleDropDown moduleDropDown = new ModuleDropDown();

    private JPanel deleteModulePanel;
    private JPanel buttonPanel;
    private JPanel dropDownPanel;

    private JButton cancelButton;
    private JButton deleteButton;
    private JComboBox<Object> dropDown;

    public DeleteModulePage(State state, GUIManager guiManager){
        this.state = state;
        this.manager = guiManager;
    }

    public JPanel getPanel(){
        deleteModulePanel = new JPanel();
        buttonPanel = new JPanel();
        dropDownPanel = new JPanel();

        cancelButton = new JButton("Cancel");
        deleteButton = new JButton("Delete module");

        deleteButton.addActionListener(this);
        cancelButton.addActionListener(this);

        buttonPanel.add(cancelButton);
        buttonPanel.add(deleteButton);

        dropDown = moduleDropDown.getModuleDropDown();

        dropDownPanel.add(dropDown);

        deleteModulePanel.add(dropDownPanel);
        deleteModulePanel.add(buttonPanel);

        deleteModulePanel.setLayout(new BoxLayout(deleteModulePanel, BoxLayout.Y_AXIS));

        return deleteModulePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();

        if(source == deleteButton){
            handleDeletingSession();
        }

        else if(source == cancelButton){
            manager.swapToPrevCard();
        }
    }

    /**
     * Handles deleting the session and updating the module drop down
     */
    private void handleDeletingSession(){
        // Checks if there are no modules to delete
        if(dropDown.getItemCount() == 0){
            return;
        }

        String moduleName = Objects.requireNonNull(dropDown.getSelectedItem()).toString();

        state.deleteModule(moduleName);
        moduleDropDown.removeModule(moduleName);

        // Swaps to the previous card if there are no more modules left
        if(dropDown.getItemCount() == 0){
            manager.swapToPrevCard();
        }

        System.out.println(moduleName + " successfully deleted.");
    }

    public JButton getCancelButton(){
        return cancelButton;
    }

    public JButton getDeleteButton(){
        return deleteButton;
    }
}
