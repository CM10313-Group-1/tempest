package tempest.GUI;

import tempest.State;

import javax.swing.*;
import java.awt.*;

public class GUIManager extends  JFrame{
    private static JPanel currentPanel;
    private static CardLayout cl;

    private State state;

    private HomePage home = new HomePage();
    private AddModulePage addModule = new AddModulePage();
    private AddSessionPage addSession;

    public GUIManager(State state){
        this.state = state;
        this.run();
    }

    private void run(){
        addSession = new AddSessionPage(state);

        setSize(500,150);
        setTitle("Tempest");

        currentPanel = new JPanel();
        cl = new CardLayout();

        currentPanel.setLayout(cl);

        // Adding panels to cardPanel
        currentPanel.add(home.getPanel(), "1");

        currentPanel.add(addModule.getPanel(), "2");

        currentPanel.add(addSession.getPanel(), "3");

        getContentPane().add(currentPanel);

        // Frame Setting
        setLocationRelativeTo(null); // Centering GUI
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void changeFrame(int newPanel){
        cl.show(currentPanel, Integer.toString(newPanel));
    }
}
