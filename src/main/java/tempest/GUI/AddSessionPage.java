package tempest.GUI;

import tempest.GUI.components.ActionButtonPanel;
import tempest.GUI.components.ClearButton;
import tempest.GUI.components.ModuleDropDown;
import tempest.Module;
import tempest.State;
import tempest.StudySession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

public class AddSessionPage extends Page implements ActionListener{
    private final GUIManager manager;
    private final State state;
    private final ClearButton components = new ClearButton();
    private final ModuleDropDown dropDown = new ModuleDropDown();
    private final ActionButtonPanel actionButtonPanel = new ActionButtonPanel();
    private final ErrorMessage errorMessage = new ErrorMessage();

    private JPanel sessionPanel;
    private JComboBox<Object> moduleDropDown;
    private JTextField hoursInput;
    private JTextField minutesInput;
    private JButton enterButton;

    public AddSessionPage(State state, GUIManager guiManager){
        this.state = state;
        this.manager = guiManager;
    }

    @Override
    public String getName() {
        return "addSessionPage";
    }

    public JPanel getPanel(){
        sessionPanel = new JPanel();

        JPanel buttonPanel = actionButtonPanel.getButtonPanel(manager, this);
        enterButton = (JButton) buttonPanel.getComponent(1);

        JPanel inputPanel = new JPanel();

        moduleDropDown = dropDown.getModuleDropDown();
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

        JButton clearButton = components.getClearButton(this);
        inputPanel.add(clearButton);

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

        String hours = hoursInput.getText();
        String minutes = minutesInput.getText();

        int hoursInt = 0;
        int minutesInt = 0;

        hours = hours.strip();
        minutes = minutes.strip();

        if (hours.equals("") && minutes.equals("")) { // Empty hours and minutes
            errorMessage("A session needs to be >= 1 minute");
            return;
        }
        else if (hours.equals("")) { // Only minutes have been entered
            try {
                minutesInt = Integer.parseInt(minutes);

                if (minutesInt <= 0) {
                    throw new NumberFormatException(); // Negative minutes
                }
            } catch (NumberFormatException e) {
                errorMessage("Invalid minutes entered");
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
                errorMessage("Invalid hours entered");
                return;
            }
        }
        else { // Hours and minutes have been entered
            try {
                minutesInt = Integer.parseInt(minutes);
                hoursInt = Integer.parseInt(hours);

                if (hoursInt <= 0 || minutesInt <= 0) {
                    throw new NumberFormatException(); // At least one is negative
                }

                if (minutesInt > 59) {
                    errorMessage("If an hour is entered minutes should be < 60"); // Minutes exceeds 59 with an hour inputted
                    return;
                }
            } catch (NumberFormatException e) {
                errorMessage("Invalid time entered");
                return;
            }
        }

        Duration time = Duration.ofMinutes(hoursInt * 60L + minutesInt);

        // Checks the session entered is under 24 hours
        if (time.toMinutes() > 24 * 60){
            errorMessage("A study session can't be longer than 24 hours");
            return;
        }

        Module module = null;
        boolean foundName = false;

        for (Module m : state.getModules()) {
            if (moduleName.equals(m.getName())) {
                module = m;
                foundName = true;
                break;
            }
        }

        if (!foundName) {
            errorMessage("Unable to find this module");
            return;
        }

        // Checking if study session in one day add up to be > 24hrs

        int duration = 0;

        for (StudySession s : module.getStudySessions()) {
            if (s.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().equals(LocalDate.now()))
                duration += s.duration.toMinutes();
        }

        if (duration + time.toMinutes() > 1440) {
            errorMessage("Unable to enter a session that will bring total sessions today over 24 hours");
            return;
        }

        module.addSession(new Date(), time);

        System.out.println("Study session successfully added");

        clearInput();
    }

    /**
     * Creates a pop up notifying the user of an error
     *
     * @param message The error message to be printed in the pop up
     */
    private void errorMessage(String message) {
        errorMessage.showMessage(sessionPanel, message);
    }

    /**
     * Clears the hours and minutes JTextField inputs
     */
    public void clearInput() {
        hoursInput.setText(""); // Clearing inputted hours
        minutesInput.setText(""); // Clearing inputted mins
    }

    public JButton getEnterButton() {
        return enterButton;
    }

    public ActionButtonPanel getComponents() {
        return actionButtonPanel;
    }

    public void setHours(String hours) {
        hoursInput.setText(hours);
    }

    public void setMins(String mins) {
        minutesInput.setText(mins);
    }
}
