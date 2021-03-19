package tempest.GUI;

import tempest.Module;
import tempest.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

//TODO:
// - Add a check so the user can't enter a session > 24hrs
// - Add a check so the user can't enter study sessions in a day which add up to be > 24hrs

public class AddSessionPage extends JFrame implements ActionListener{
    private GUIManager guiManager;

    private ArrayList<Module> modules;
    private State state;
    private JComboBox<Object> moduleDropDown;
    private JTextField hoursInput;
    private JTextField minutesInput;
    private JButton enterButton;
    private JButton cancelButton;
    private JPanel buttonPanel;

    public AddSessionPage(State state, GUIManager guiManager){
        this.state = state;
        this.guiManager = guiManager;
    }

    /**
     * @return returns the container with all of the components for the page
     */
    public Container getPanel(){
        JPanel sessionPanel = new JPanel();
        JPanel inputPanel = new JPanel();

        modules = new ArrayList<>(Arrays.asList(state.getModules()));

        moduleDropDown = new JComboBox<>();

        // Populating drop down with the names of all current modules
        for (Module m : modules) {
            moduleDropDown.addItem(m.getName());
        }

        inputPanel.add(moduleDropDown);

        buttonPanel = new JPanel();

        cancelButton = new JButton("Cancel");
        enterButton = new JButton("Enter");

        cancelButton.setFocusable(false);
        enterButton.setFocusable(false);

        cancelButton.addActionListener(this);
        enterButton.addActionListener(this);

        buttonPanel.add(enterButton);
        buttonPanel.add(cancelButton);

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

        inputPanel.add(timeInputPanel);

        sessionPanel.add(inputPanel);
        sessionPanel.add(buttonPanel);

        sessionPanel.setLayout(new BoxLayout(sessionPanel, BoxLayout.Y_AXIS));

        return sessionPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();

        if(source == enterButton){
            handleAddingSession();
        }
        else if(source == cancelButton){
            guiManager.changeFrame(1);
        }
    }

    /**
     * Handles the user pressing enter on the add session page
     */
    private void handleAddingSession() {
        if (modules.size() < 1) { // No modules created, so can't add a session
            return;
        }

        String moduleName = Objects.requireNonNull(moduleDropDown.getSelectedItem()).toString();

        Date date = new Date();

        String hours = hoursInput.getText();
        String minutes = minutesInput.getText();

        int hoursInt = 0;
        int minutesInt = 0;

        if (hours.strip().equals("") && minutes.strip().equals("")) { // Empty hours and minutes
            System.out.println("A session needs to be longer >= 1 minute");
            return;
        }
        else if (hours.strip().equals("")) { // Only minutes have been entered
            try {
                minutesInt = Integer.parseInt(minutes);

                if (minutesInt <= 0) {
                    throw new NumberFormatException(); // Negative minutes
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid minutes entered");
                return;
            }
        }
        else if (minutes.strip().equals("")) { // Only hours have been entered
            try {
                hoursInt = Integer.parseInt(hours);

                if (hoursInt <= 0) {
                    throw new NumberFormatException(); // Negative hours
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid hours entered");
                return;
            }
        }
        else { // Hours and minutes have been entered
            try {
                minutesInt = Integer.parseInt(minutes);
                hoursInt = Integer.parseInt(hours);

                if (hoursInt <= 0 || minutesInt <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid time entered");
                return;
            }
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

        hoursInput.setText(""); // Clearing inputted hours
        minutesInput.setText(""); // Clearing inputted mins
    }
}
