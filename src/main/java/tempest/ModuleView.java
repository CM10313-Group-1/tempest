package tempest;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import javax.swing.*;

/**
 * ModuleView is responsible for creating the GUIs for the home
 * page, adding a module page and adding studying sessions page.
 *
 */
public class ModuleView extends JFrame implements ActionListener {

    private static final State state = new State(); // Only static if the ArrayList isn't static

    private int currentCard = 1;

    private JPanel cardPanel;
    private JPanel actionButtonPanel;

    private JTextField moduleNameInput;

    private JTextField hoursInput;
    private JTextField minutesInput;
    private JComboBox<Object> moduleDropDown;

    private JButton addModuleButton;
    private JButton addSessionButton;

    private JButton cancelButton;
    private JButton enterButton;

    private CardLayout cl;

    private ArrayList<Module> modules;

    /**
     * Calling this constructor will execute the GUI code
     */
    public ModuleView()
    {
        getModules();
        run();
    }

    /**
     * Used to get all the created modules and stores the result
     * in the modules ArrayList
     */
    private void getModules() {
        modules = new ArrayList<>(Arrays.asList(state.getModules()));
    }

    /**
     * Responsible for creating the GUI
     */
    private void run() {
        setTitle("Tempest");
        setSize(500, 150);

        cl = new CardLayout();

        cardPanel = new JPanel();

        cardPanel.setLayout(cl);

        // Home Panel
        JPanel home = new JPanel();
        homePanel(home);

        // Module Panel
        JPanel addModule = new JPanel();
        modulePanel(addModule);

        // Session Panel
        JPanel addSession = new JPanel();
        sessionPanel(addSession);

        // Adding panels to cardPanel
        cardPanel.add(home, "1");

        cardPanel.add(addModule, "2");

        cardPanel.add(addSession, "3");

        // Button Panel
        actionButtonPanel = new JPanel();
        buttonPanel(actionButtonPanel);

        getContentPane().add(cardPanel, BorderLayout.NORTH);
        getContentPane().add(actionButtonPanel, BorderLayout.SOUTH);

        // Frame Setting
        setLocationRelativeTo(null); //Centering GUI
        //pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Creates the home page and provides the code for handling
     * a user pressing the home page buttons
     *
     * @param home : A JPanel that is later added to cardPanel
     */
    private void homePanel(JPanel home) {
        home.setAlignmentX(Component.CENTER_ALIGNMENT);

        addModuleButton  = new JButton("Add a new module");
        addSessionButton = new JButton("Add a new session");

        addModuleButton.setFocusable(false);
        addSessionButton.setFocusable(false);

        addModuleButton.addActionListener(this);
        addSessionButton.addActionListener(this);

        home.add(addModuleButton);
        home.add(addSessionButton);

        addModuleButton.addActionListener(arg0 -> {
            currentCard = 2;

            cl.show(cardPanel, Integer.toString(currentCard));

            buttonPanelVisible(currentCard);
        });

        addSessionButton.addActionListener(arg0 -> {
            currentCard = 3;

            cl.show(cardPanel, Integer.toString(currentCard));

            buttonPanelVisible(currentCard);
        });
    }

    /**
     * Creates the add module page
     *
     * @param addModule : A JPanel that is later added to cardPanel
     */
    private void modulePanel(JPanel addModule) {
        JPanel addModulePanel = new JPanel();

        JLabel addModuleLabel = new JLabel("Enter module name:");
        moduleNameInput = new JTextField(20);

        addModulePanel.add(addModuleLabel);
        addModulePanel.add(moduleNameInput);
        addModulePanel.setLayout(new FlowLayout());

        addModule.add(addModulePanel);
    }

    /**
     * Creates the add session page
     *
     * @param addSession : A JPanel that is later added to cardPanel
     */
    private void sessionPanel(JPanel addSession) {
        moduleDropDown = new JComboBox<>();

        //Populating drop down with the names of all current modules
        for (Module m : modules) {
            moduleDropDown.addItem(m.getName());
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
    }

    /**
     * Creates the button panel at the bottom of all GUI pages
     *
     * @param actionButtonPanel : A JPanel that is later added to cardPanel
     */
    private void buttonPanel(JPanel actionButtonPanel) {
        actionButtonPanel.setVisible(currentCard != 1);

        cancelButton = new JButton("Cancel");
        enterButton  = new JButton("Enter");

        cancelButton.setFocusable(false);
        enterButton.setFocusable(false);

        actionButtonPanel.add(cancelButton);
        actionButtonPanel.add(enterButton);

        cancelButton.addActionListener(this);
        enterButton.addActionListener(this);
    }

    /**
     * Hides the action button panel when on the home page
     *
     * @param card : An integer storing the current page of the GUI
     */
    private void buttonPanelVisible(int card) {
        actionButtonPanel.setVisible(card != 1);
    }

    /**
     * Handles button presses made by the user
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelButton) {
            cl.first(cardPanel); // Changes panel to home panel

            currentCard = 1;

            buttonPanelVisible(currentCard);
        }
        else if (e.getSource() == enterButton) {
            boolean uniqueName = true;

            // Creating a new module
            if (currentCard == 2) {
                String moduleName = moduleNameInput.getText();

                // Checking if module name is unique
                for (Module m : modules) {
                    if (moduleName.equals(m.getName())) {
                        System.out.println("Another module already has this name");
                        uniqueName = false;
                        break;
                    }
                }

                if (uniqueName) {
                    state.createModule(moduleName);

                    dispose();         // Kills current GUI
                    new ModuleView();  // Opens a new GUI with updated drop down

                    System.out.println("Module successfully created");

                    // If want GUI to show same screen then:
                    // cl.show(cardPanel, currentCard) -> instead of code at bottom of this else if;
                }
            }
            // Adding a new session
            else if (currentCard == 3) {
                String moduleName = String.valueOf(moduleDropDown.getSelectedItem()); //Handles null values in the drop down

                Date date = new Date();

                String hours = hoursInput.getText();
                String minutes = minutesInput.getText();

                int hoursInt = Integer.parseInt(hours);
                int minutesInt = Integer.parseInt(minutes);

                Duration time = Duration.ofMinutes(hoursInt * 60L + minutesInt);

                for(Module m: modules){
                    if(moduleName.equals(m.getName())){
                        m.addSession(date, time);
                        break;
                    }
                }

                moduleNameInput.setText(""); // Clearing inputted name

                System.out.println("Study session successfully added");

                // In future sprints will need to call dispose() and new ModuleView() to be able to show the updated sessions
                // Unless the frame showing sessions is its own class
            }

            cl.first(cardPanel); // Changes panel to home panel

            currentCard = 1;

            buttonPanelVisible(currentCard);
        }
    }


    public JButton getAddModuleButton() {
        return addModuleButton;
    }

    public JButton getAddSessionButton() {
        return addSessionButton;
    }

    public JButton getEnterButton() {
        return enterButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    /**
     * @return The current card (page) of the GUI
     */
    public int getCard() {
        return currentCard;
    }

    public void setModuleNameInput(String name) {
        moduleNameInput.setText(name);
    }

    public void setHours(String hours) {
        hoursInput.setText(hours);
    }

    public void setMins(String mins) {
        minutesInput.setText(mins);
    }

    //TODO:
    // - Handle user pressing enter if inputted values are empty
}
