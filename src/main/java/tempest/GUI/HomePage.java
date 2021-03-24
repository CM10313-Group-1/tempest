package tempest.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends Page implements ActionListener{
    private final GUIManager manager;

    private JButton manageModulesButton;
    private JButton addSessionButton;

    public HomePage(GUIManager guiManager){
        this.manager = guiManager;
    }

    public JPanel getPanel(){
        JPanel homePage = new JPanel();

        JPanel buttonPanel = new JPanel();

        manageModulesButton = new JButton("Manage modules");
        addSessionButton = new JButton("Add a new session");

        manageModulesButton.setFocusable(false);
        addSessionButton.setFocusable(false);

        manageModulesButton.addActionListener(this);
        addSessionButton.addActionListener(this);

        buttonPanel.add(manageModulesButton);
        buttonPanel.add(addSessionButton);

        homePage.add(buttonPanel);

        return homePage;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();

        if(source == manageModulesButton){
            manager.swapCard("manageModules");
        }
        else if(source == addSessionButton){
            manager.swapCard("addSession");
        }
    }

    public JButton getManageModulesButton() {
        return manageModulesButton;
    }

    public JButton getAddSessionButton() {
        return addSessionButton;
    }
}
