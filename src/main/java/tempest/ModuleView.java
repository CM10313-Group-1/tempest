package tempest;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class ModuleView extends JFrame implements ActionListener {

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

    public ModuleView()
    {
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

        ArrayList<String> modules = new ArrayList<>();
        modules.add("Maths");
        modules.add("PoP");
        modules.add("Sys Arch");

        moduleDropDown = new JComboBox<>(modules.toArray());
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

            if (currentCard == 2) {
                String moduleName = createModuleInput.getText();

                //Call Module()
            }
            else if (currentCard == 3) {
                Object module = moduleDropDown.getSelectedItem();

                String hours = hoursInput.getText();
                String minutes = minutesInput.getText();

                //Call session???
            }

            cl.first(cardPanel); //Changes panel to home panel

            currentCard = 1;

            cancelPanelVisible(currentCard);
        }
    }

    public static void main(String[] args)
    {
        ModuleView demo = new ModuleView();

        demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        demo.setVisible(true);
    }

    //TODO:
    // - Clear JTextFields when their panels are shown
    // - Call module and session
    // - Get list of modules and populate the drop down with these -> from state class
    // - The drop down should be of type module
    // - Should be Module module = moduleDropDown.getSelectedItem();
}
