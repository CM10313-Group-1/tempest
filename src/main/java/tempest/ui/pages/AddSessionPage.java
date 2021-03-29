package tempest.ui.pages;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import tempest.Module;
import tempest.State;
import tempest.StudySession;
import tempest.ui.ErrorMessage;
import tempest.ui.GUIManager;
import tempest.ui.components.ActionButtonPanel;
import tempest.ui.components.ClearButton;
import tempest.ui.components.ModuleDropDown;

public class AddSessionPage extends Page {
    private static final long serialVersionUID = 6738660438220363619L;

    private final State state;
    private final ModuleDropDown moduleDropDown = new ModuleDropDown();
    private final ActionButtonPanel actionButtonPanel;
    private final ErrorMessage errorMessage = new ErrorMessage();

    private JComboBox<Object> dropDown;
    private JTextField hoursInput;
    private JTextField minutesInput;
    private JButton enterButton;

    public AddSessionPage(State state, GUIManager guiManager) {
        super();

        this.state = state;
        this.actionButtonPanel = new ActionButtonPanel(guiManager, this);
        setupUI();
    }

    @Override
    public String getName() {
        return PageNames.ADD_SESSION;
    }

    private void setupUI() {
        enterButton = (JButton) actionButtonPanel.getComponent(1);

        JPanel inputPanel = new JPanel();

        dropDown = moduleDropDown.getModuleDropDown();
        inputPanel.add(dropDown);

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

        ClearButton clearButton = new ClearButton(this);
        inputPanel.add(clearButton);

        this.add(inputPanel);
        this.add(actionButtonPanel);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == enterButton) {
            handleAddingSession();
        }
    }

    /**
     * Handles the user trying to enter a session
     */
    private void handleAddingSession() {
        if (dropDown.getItemCount() < 1) { // No modules created, so can't add a session
            return;
        }

        String moduleName = Objects.requireNonNull(dropDown.getSelectedItem()).toString();

        String hours = hoursInput.getText();
        String minutes = minutesInput.getText();

        int hoursInt;
        int minutesInt;

        hours = hours.strip();
        minutes = minutes.strip();

        try {
            if (hours.equals("")) {
                hours = Integer.toString(0);

            } else if (minutes.equals("")) {
                minutes = Integer.toString(0);
            }

            hoursInt = Integer.parseInt(hours);
            minutesInt = Integer.parseInt(minutes);

            if (hoursInt < 0 || minutesInt < 0) {
                throw new Exception("Input must be greater than 0");

            } else if (hoursInt == 0 && minutesInt == 0) {
                throw new Exception("A study session must be >= 1 minutes");

            } else if (hoursInt > 0 && minutesInt > 59) {
                throw new Exception("If an hour is entered minutes should be < 60");
            }

            Duration time = Duration.ofMinutes(hoursInt * 60L + minutesInt);

            // Checks the session entered is under 24 hours
            if (time.toMinutes() > 24 * 60) {
                throw new Exception("A study session can't be longer than 24 hours");
            }

            Module module = null;

            for (Module m : state.getModules()) {
                if (moduleName.equals(m.getName())) {
                    module = m;
                    break;
                }
            }

            if (module == null) {
                throw new Exception("Unable to find this module");
            }

            // Checking if study session in one day add up to be > 24hrs

            int duration = 0;

            for (StudySession s : module.getStudySessions()) {
                if (s.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().equals(LocalDate.now()))
                    duration += s.duration.toMinutes();
            }

            if (duration + time.toMinutes() > 1440) {
                throw new Exception("Unable to enter a session that will bring total sessions today over 24 hours");
            }

            module.addSession(new Date(), time);

            System.out.println("Study session successfully added");

            clearInput();

        } catch (NumberFormatException e) {
            errorMessage(new NumberFormatException("Please enter positive integers"));

        } catch (Exception e) {
            errorMessage(e);
        }
    }

    /**
     * Creates a pop up notifying the user of an error
     *
     * @param message The error message to be printed in the pop up
     */
    private void errorMessage(Exception message) {
        errorMessage.showMessage(this, message);
    }

    /**
     * Clears the hours and minutes JTextField inputs
     */
    @Override
    public void clearInput() {
        hoursInput.setText(""); // Clearing inputted hours
        minutesInput.setText(""); // Clearing inputted mins
    }

    public JButton getEnterButton() {
        return enterButton;
    }

    public ActionButtonPanel getActionButtons() {
        return actionButtonPanel;
    }

    public void setHours(String hours) {
        hoursInput.setText(hours);
    }

    public void setMins(String mins) {
        minutesInput.setText(mins);
    }

    public void setDropDown(String name) {
        dropDown.setSelectedItem(name);
    }
}
