package tempest.GUI;

import tempest.GUI.components.ModuleDropDown;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ManageModulesPage extends Page{
    private GUIManager manager;
    private ModuleDropDown moduleDropDown = new ModuleDropDown();

    private JPanel manageModulesPanel;
    private JPanel pageSwapPanel;
    private JPanel cancelPanel;

    private JButton addModuleButton;
    private JButton deleteModuleButton;
    private JButton cancelButton;
    private JComboBox<Object> dropDown;

    public ManageModulesPage(GUIManager guiManager){this.manager = guiManager;}

    public JPanel getPanel(){
        manageModulesPanel = new JPanel();
        pageSwapPanel = new JPanel();
        cancelPanel = new JPanel();

        dropDown = moduleDropDown.getModuleDropDown();

        addModuleButton = new JButton("Add a module");
        deleteModuleButton = new JButton("Delete a module");
        cancelButton = new JButton("Cancel");

        addModuleButton.addActionListener(this);
        deleteModuleButton.addActionListener(this);
        cancelButton.addActionListener(this);

        pageSwapPanel.add(addModuleButton);
        pageSwapPanel.add(deleteModuleButton);
        cancelPanel.add(cancelButton);

        manageModulesPanel.add(pageSwapPanel);
        manageModulesPanel.add(cancelPanel);

        manageModulesPanel.setLayout(new BoxLayout(manageModulesPanel, BoxLayout.Y_AXIS));

        return manageModulesPanel;
    }

    /**
     * Updates the delete module button to be disabled if there are no
     * modules to delete.
     */
    public void update(){
        if(dropDown.getItemCount() == 0){
            deleteModuleButton.setEnabled(false);
        }
        // If a module/modules have been created then the button is active again
        else{
            deleteModuleButton.setEnabled(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();

        if(source == addModuleButton){
            manager.swapCard("addModule");
        }
        else if(source == deleteModuleButton){
            manager.swapCard("deleteModule");
        }
        else if(source == cancelButton){
            manager.swapToPrevCard();
        }
    }

    public JButton getAddModuleButton(){
        return addModuleButton;
    }

    public JButton getDeleteModuleButton(){
        return deleteModuleButton;
    }

    public JButton getCancelButton(){
        return cancelButton;
    }
}
