package tempest.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame implements ActionListener{
    private JPanel buttonPanel;
    private JButton addModuleButton;
    private JButton addSessionButton;

    public HomePage(){}

    public Container getPanel(){
        buttonPanel = new JPanel();

        addModuleButton = new JButton("Add a new module");
        addSessionButton = new JButton("Add a new session");

        addModuleButton.setFocusable(false);
        addSessionButton.setFocusable(false);

        addModuleButton.addActionListener(this);
        addSessionButton.addActionListener(this);

        buttonPanel.add(addModuleButton);
        buttonPanel.add(addSessionButton);

        getContentPane().add(buttonPanel);

        return getContentPane();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
        if(source == addModuleButton){
            GUIManager.changeFrame(2);
        }
        else if(source == addSessionButton){
            GUIManager.changeFrame(3);
        }
    }
}
