package tempest.GUI;

import tempest.Module;
import tempest.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.util.Date;
import java.util.Objects;

//TODO:
// - Is the current method of getting the enterButton good???
// - Add a check so the user can't enter a session > 24hrs
// - Add a check so the user can't enter study sessions in a day which add up to be > 24hrs
// - Better if pressing enter sends user back a screen???

public class AddSessionPage extends Page implements ActionListener{
    private final GUIManager manager;
    private final State state;
    private final GUIComponents component = new GUIComponents();

    private JComboBox<Object> moduleDropDown;
    private JTextField hoursInput;
    private JTextField minutesInput;
    private JButton enterButton;

    public AddSessionPage(State state, GUIManager guiManager){
        this.state = state;
        this.manager = guiManager;
    }

    public JPanel getPanel(){
        JPanel sessionPanel = new JPanel();

        JPanel buttonPanel = component.getButtonPanel(manager, this);
        enterButton = (JButton) buttonPanel.getComponent(1);

        JPanel inputPanel = new JPanel();

        moduleDropDown = component.getModuleDropDown();
        inputPanel.add(moduleDropDown);

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
    }

    /**
     * Handles the user trying to enter a session
     */
    private void handleAddingSession() {
        if (moduleDropDown.getItemCount() < 1) { // No modules created, so can't add a session
            return;
        }

        String moduleName = Objects.requireNonNull(moduleDropDown.getSelectedItem()).toString();

        Date date = new Date();

        String hours = hoursInput.getText();
        String minutes = minutesInput.getText();

        int hoursInt = 0;
        int minutesInt = 0;

        hours = hours.strip();
        minutes = minutes.strip();

        if (hours.equals("") && minutes.equals("")) { // Empty hours and minutes
            System.out.println("A session needs to be longer >= 1 minute");
            return;
        }
        else if (hours.equals("")) { // Only minutes have been entered
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
        else if (minutes.equals("")) { // Only hours have been entered
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

        for (Module m : state.getModules()) {
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
