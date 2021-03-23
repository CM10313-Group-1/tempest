package tempest.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends Page implements ActionListener{
    private final GUIManager manager;

    private JButton addModuleButton;
    private JButton sessionsButton;

    public HomePage(GUIManager guiManager){
        this.manager = guiManager;
    }

    public JPanel getPanel(){
        JPanel homePage = new JPanel();

        JPanel buttonPanel = new JPanel();

        addModuleButton = new JButton("Add a new module");
        sessionsButton = new JButton("Study Sessions");

        addModuleButton.setFocusable(false);
        sessionsButton.setFocusable(false);

        addModuleButton.addActionListener(this);
        sessionsButton.addActionListener(this);

        buttonPanel.add(addModuleButton);
        buttonPanel.add(sessionsButton);

        homePage.add(buttonPanel);

        return homePage;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();

        if(source == addModuleButton){
            manager.swapCard("addModulePage");
        }
        else if(source == sessionsButton){
            manager.swapCard("sessionsPage");
        }
    }

    public JButton getAddModuleButton() {
        return addModuleButton;
    }

    public JButton getSessionsButton() {
        return sessionsButton;
    }
}
