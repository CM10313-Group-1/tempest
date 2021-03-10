package tempest;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;

public class ModuleView extends JFrame implements ActionListener {

    private State currentModules = new State();

    private int currentCard = 1;

    private JPanel cardPanel;
    private JPanel buttonPanel = new JPanel();

    private JTextField createModuleInput;

    private JTextField hoursInput;
    private JTextField minutesInput;
    private JComboBox<Object> moduleDropDown;

    private JButton cancelButton;
    private JButton enterButton;

    private CardLayout cl;

    private ArrayList<Module> modules;

    public ModuleView()
    {
        State state = new State();
        modules = new ArrayList<>(Arrays.asList(state.getModules()));
    }

    private void run() {
        setTitle("Tempest");

        setSize(500, 150);

        cardPanel = new JPanel();

        cl = new CardLayout();

        // set the layout
        cardPanel.setLayout(cl);

        // **** Home Panel ****
        JPanel home = new JPanel();

        home.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton addModuleButton  = new JButton("Add a new module");
        JButton addSessionButton = new JButton("Add a new session");

        addModuleButton.addActionListener(this);
        addSessionButton.addActionListener(this);

        home.add(addModuleButton);
        home.add(addSessionButton);

        addModuleButton.addActionListener(arg0 -> {
            currentCard = 2;

            cl.show(cardPanel, Integer.toString(currentCard));

            cancelPanelVisible(currentCard);
        });

        addSessionButton.addActionListener(arg0 -> {
            currentCard = 3;

            cl.show(cardPanel, Integer.toString(currentCard));

            cancelPanelVisible(currentCard);
        });


        // **** Add Module Panel ****
        JPanel addModule = new JPanel();

        JPanel createModulePanel = new JPanel();

        JLabel createModuleLabel = new JLabel("Enter module name:");
        createModuleInput = new JTextField(20);

        createModulePanel.add(createModuleLabel);
        createModulePanel.add(createModuleInput);
        createModulePanel.setLayout(new FlowLayout());

        addModule.add(createModulePanel);

        // **** Add Session Panel
        JPanel addSession = new JPanel();

        moduleDropDown = new JComboBox<>();

        //Populating drop down with the names of all current modules
        for (Module m : modules) {
            moduleDropDown.addItem(m.name);
        }

        addSession.add(moduleDropDown);

        hoursInput = new JTextField(2);
        JLabel hoursInputLabel = new JLabel("Hours");

        minutesInput = new JTextField(2);
        JLabel minutesInputLabel = new JLabel("Minutes");

        JPanel hoursPanel = new JPanel();
        hoursPanel.add(hoursInputLabel);
        hoursPanel.add(hoursInput);

        JPanel minutesPanel = new JPanel();
        minutesPanel.add(minutesInputLabel);
        minutesPanel.add(minutesInput);

        JPanel timeInputPanel = new JPanel();
        timeInputPanel.add(hoursPanel);
        timeInputPanel.add(minutesPanel);
        timeInputPanel.setLayout(new FlowLayout());

        addSession.add(timeInputPanel);


        // Adding the cardPanel on "home"
        cardPanel.add(home, "1");

        // Adding the cardPanel on "addModule"
        cardPanel.add(addModule, "2");

        // Adding the cardPanel on "cardPanel"
        cardPanel.add(addSession, "3");


        buttonPanel.setVisible(currentCard != 1);

        cancelButton = new JButton("Cancel");
        enterButton = new JButton("Enter");

        buttonPanel.add(cancelButton);
        buttonPanel.add(enterButton);

        cancelButton.addActionListener(this);
        enterButton.addActionListener(this);


        // used to get content pane
        getContentPane().add(cardPanel, BorderLayout.NORTH);

        // used to get content pane
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private void cancelPanelVisible(int card) {
        buttonPanel.setVisible(card != 1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelButton) {
            cl.first(cardPanel); //Changes panel to home panel

            currentCard = 1;

            cancelPanelVisible(currentCard);
        } else if (e.getSource() == enterButton) {

            // Creating a new module
            if (currentCard == 2) {
                String moduleName = createModuleInput.getText();

                currentModules.createModule(moduleName);

                // Update module list???
            }
            // Adding a new session
            else if (currentCard == 3) {
                Object moduleName = moduleDropDown.getSelectedItem();

                String hours = hoursInput.getText();
                String minutes = minutesInput.getText();
            }

            cl.first(cardPanel); //Changes panel to home panel

            currentCard = 1;

            cancelPanelVisible(currentCard);
        }
    }

    public static void main(String[] args)
    {
        ModuleView demo = new ModuleView();
        demo.run();

        demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        demo.setVisible(true);
    }

    //TODO:
    // - Clear JTextFields when their panels are shown
    // - Call session
    // - Update list of modules when a new module created -> change local list OR call state.getModules() again
    // - Update drop down box when new modules created
}
