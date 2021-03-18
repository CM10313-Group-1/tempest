package tempest.GUI;

import tempest.State;
import tempest.Supervisor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUIManager extends  JFrame{
    private static JPanel currentPanel;
    private static CardLayout cl;

    private State state;
    private Supervisor supervisor;

    private HomePage home;
    private AddModulePage addModule;
    private AddSessionPage addSession;

    public GUIManager(State state, Supervisor supervisor){
        this.state = state;
        this.supervisor = supervisor;
        this.run();
    }

    /**
     * Sets up the GUI interface
     */
    private void run(){
        home = new HomePage(this);
        addSession = new AddSessionPage(state, this);
        addModule = new AddModulePage(state, this);

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

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                supervisor.onClose();
            }
        });

        // Frame Setting
        setLocationRelativeTo(null); // Centering GUI
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Switches between pages (panels)
     *
     * @param newPanel the number of the panel to switch to
     */
    public void changeFrame(int newPanel){
        //just kills it to refresh the combobox
        if(newPanel == 1){
            dispose();
            new GUIManager(state, supervisor);
        }
        else{
            cl.show(currentPanel, Integer.toString(newPanel));
        }
    }
}
