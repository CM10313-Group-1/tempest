package tempest.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends Page implements ActionListener{
    private final GUIManager manager;

    private JButton addModuleButton;
    private JButton addSessionButton;

    public HomePage(GUIManager guiManager){
        this.manager = guiManager;
    }

    public JPanel getPanel(){
        JPanel homePage = new JPanel();

        JPanel buttonPanel = new JPanel();

        addModuleButton = new JButton("Add a new module");
        addSessionButton = new JButton("Add a new session");

        addModuleButton.setFocusable(false);
        addSessionButton.setFocusable(false);

        addModuleButton.addActionListener(this);
        addSessionButton.addActionListener(this);

        buttonPanel.add(addModuleButton);
        buttonPanel.add(addSessionButton);

        homePage.add(buttonPanel);

        return homePage;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();

        if(source == addModuleButton){
            manager.swapCard("addModule");
        }
        else if(source == addSessionButton){
            manager.swapCard("addSession");
        }
    }
}
