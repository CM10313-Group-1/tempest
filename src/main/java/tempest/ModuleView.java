package tempest;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import javax.swing.*;

public class ModuleView extends JFrame implements ActionListener {

    private State state = new State();

    private int currentCard = 1;

    private JPanel cardPanel;
    private JPanel actionButtonPanel = new JPanel();

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
        getModules();
    }

    private void getModules() {
        modules = new ArrayList<>(Arrays.asList(state.getModules()));
    }

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

        //buttonPanel
        buttonPanel(actionButtonPanel);


        getContentPane().add(cardPanel, BorderLayout.NORTH);

        getContentPane().add(actionButtonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        pack();
    }

    private void homePanel(JPanel home) {
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

            buttonPanelVisible(currentCard);
        });

        addSessionButton.addActionListener(arg0 -> {
            currentCard = 3;

            cl.show(cardPanel, Integer.toString(currentCard));

            buttonPanelVisible(currentCard);
        });
    }

    private void modulePanel(JPanel addModule) {
        JPanel createModulePanel = new JPanel();

        JLabel createModuleLabel = new JLabel("Enter module name:");
        createModuleInput = new JTextField(20);

        createModulePanel.add(createModuleLabel);
        createModulePanel.add(createModuleInput);
        createModulePanel.setLayout(new FlowLayout());

        addModule.add(createModulePanel);
    }

    private void sessionPanel(JPanel addSession) {
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
    }

    private void buttonPanel(JPanel actionButtonPanel) {
        actionButtonPanel.setVisible(currentCard != 1);

        cancelButton = new JButton("Cancel");
        enterButton  = new JButton("Enter");

        actionButtonPanel.add(cancelButton);
        actionButtonPanel.add(enterButton);

        cancelButton.addActionListener(this);
        enterButton.addActionListener(this);
    }

    //Hiding the cancel button when on the home page
    private void buttonPanelVisible(int card) {
        actionButtonPanel.setVisible(card != 1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelButton) {
            cl.first(cardPanel); //Changes panel to home panel

            currentCard = 1;

            buttonPanelVisible(currentCard);
        } else if (e.getSource() == enterButton) {

            // Creating a new module
            if (currentCard == 2) {
                String moduleName = createModuleInput.getText();

                state.createModule(moduleName);

                dispose();
                run();
            }
            // Adding a new session
            else if (currentCard == 3) {
                Object moduleName = moduleDropDown.getSelectedItem();
                String moduleNameString = moduleName.toString();

                Date date = new Date();

                String hours = hoursInput.getText();
                String minutes = minutesInput.getText();

                int hoursInt = Integer.parseInt(hours);
                int minutesInt = Integer.parseInt(minutes);

                Duration time = Duration.ofMinutes(hoursInt * 60L + minutesInt);

                for(Module module: modules){
                    if(moduleNameString.equals(module.name)){
                        module.addSession(date, time);
                        break;
                    }
                }
            }

            cl.first(cardPanel); //Changes panel to home panel

            currentCard = 1;

            buttonPanelVisible(currentCard);
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
    // - Update list of modules when a new module created -> change local list OR call state.getModules() again
    // - Update drop down box when new modules created
    // - Clear JTextFields when their panels are shown
}
