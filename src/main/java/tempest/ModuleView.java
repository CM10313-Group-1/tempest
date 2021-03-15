package tempest;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * ModuleView is responsible for creating the GUIs for the home page, adding a
 * module page and adding studying sessions page.
 *
 */
public class ModuleView extends JFrame implements ActionListener {
    private static final long serialVersionUID = 9089L;

    private final State state;

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
    public ModuleView(State state)
    {
        this.state = state;
        getModules();
        run();
    }

    /**
     * Used to get all the created modules and stores the result in the modules
     * ArrayList
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

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new Supervisor().onClose();
            }
        });

        // Frame Setting
        setLocationRelativeTo(null); // Centering GUI
        // pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ModuleView(new State());
    }

    /**
     * Creates the home page
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

        addModuleButton.addActionListener(this);

        addSessionButton.addActionListener(this);
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

        // Populating drop down with the names of all current modules
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
        Object obj = e.getSource();

        if (obj == addModuleButton) {
            currentCard = 2;
        }
        else if (obj == addSessionButton) {
            currentCard = 3;
        }
        else if (obj == enterButton) {

            if (currentCard == 2) {
                handleCreatingModule();
            }
            else if (currentCard == 3) {
                handleAddingSession();
            }
        }
        else if (obj == cancelButton) {
            currentCard = 1;
        }

        cl.show(cardPanel, Integer.toString(currentCard));

        buttonPanelVisible(currentCard);
    }

    /**
     * Handles the user pressing enter on the add module page
     */
    private void handleCreatingModule() {
        String moduleName = moduleNameInput.getText();

        if (moduleName.equals("")) {
            System.out.println("Invalid module name");
            return;
        }

        boolean uniqueName = true;

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

            dispose();                   // Kills current GUI
            new ModuleView(this.state);  // Opens a new GUI with updated drop down

            System.out.println("Module successfully created");

            currentCard = 1;

            // If want GUI to show same screen then:
            // cl.show(cardPanel, currentCard) -> instead of code at bottom of this else if;
        }
    }

    /**
     * Handles the user pressing enter on the add session page
     */
    private void handleAddingSession() {
        String moduleName = String.valueOf(moduleDropDown.getSelectedItem()); //Handles null values in the drop down

        Date date = new Date();

        String hours = hoursInput.getText();
        String minutes = minutesInput.getText();

        int hoursInt;
        int minutesInt;

        try {
            hoursInt   = Integer.parseInt(hours);
            minutesInt = Integer.parseInt(minutes);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid hours/minutes entered");
            return;
        }

        Duration time = Duration.ofMinutes(hoursInt * 60L + minutesInt);

        boolean foundName = false;

        for (Module m : modules) {
            if (moduleName.equals(m.getName())) {
                m.addSession(date, time);
                foundName = true;
                break;
            }
        }

        if (!foundName) {
            System.out.println("Unable to find this module");
            return;
        }

        System.out.println("Study session successfully added");

        hoursInput.setText("");   // Clearing inputted hours
        minutesInput.setText(""); // Clearing inputted mins

        currentCard = 1;

        // In future sprints will need to call dispose() and new ModuleView() to be able
        // to show the updated sessions unless the frame showing sessions is its own class
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
}
