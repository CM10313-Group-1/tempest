package tempest.GUI;

import tempest.State;
import tempest.Supervisor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//TODO:
// - Change AddModule and AddSession pages to be Modules and Session pages with options to delete, view & add
// - Don't need to extend JFrame - perhaps just confusing

public class GUIManager extends JFrame {
    private static JPanel cardPanel;
    private static CardLayout cl;

    private final State state;
    private final Supervisor supervisor;

    private GUIComponents components;

    public GUIManager(State state, Supervisor supervisor){
        this.state = state;
        this.supervisor = supervisor;
        run();
    }

    /**
     * Sets up and runs the GUI
     */
    private void run(){
        components = new GUIComponents();
        components.createModuleDropDown(state);

        HomePage home = new HomePage(this);
        AddModulePage addModule = new AddModulePage(state, this);
        AddSessionPage addSession = new AddSessionPage(state, this);

        setSize(500,150);
        setTitle("Tempest");

        cardPanel = new JPanel();
        cl = new CardLayout();

        cardPanel.setLayout(cl);

        // Adding panels to cardPanel
        cardPanel.add(home.getPanel(), "1");

        cardPanel.add(addModule.getPanel(), "2");

        cardPanel.add(addSession.getPanel(), "3");

        getContentPane().add(cardPanel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                supervisor.onClose();
            }
        });

        // Frame Settings
        setLocationRelativeTo(null); // Centering GUI
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Switches between pages (panels)
     *
     * @param index the index of the panel to switch to
     */
    public void changePanel(int index){
        cl.show(cardPanel, Integer.toString(index));
    }

    /**
     * Creates a new module using state and updates the module drop down
     * in GUIComponents
     *
     * @param moduleName Name of module to be created
     */
    public void addModule(String moduleName) {
        state.createModule(moduleName);
        components.addModule(moduleName);
    }

    /**
     * Removes the module using state and updates the module drop down
     * in GUIComponents
     *
     * @param moduleName Name of module to be removed
     */
    public void removeModule(String moduleName) {
        state.deleteModule(moduleName);
        components.removeModule(moduleName);
    }
}
