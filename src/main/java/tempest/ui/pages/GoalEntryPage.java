package tempest.ui.pages;

import tempest.Module;
import tempest.State;
import tempest.ui.ErrorMessage;
import tempest.ui.GUIManager;
import tempest.ui.components.ActionButtonPanel;
import tempest.ui.components.ClearButton;
import tempest.ui.components.ModuleDropDown;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.Duration;
import java.util.Objects;

public class GoalEntryPage extends Page implements InputPage {

    private final State state;
    private final ModuleDropDown moduleDropDown = new ModuleDropDown();
    private final ActionButtonPanel actionButtonPanel;
    private final ErrorMessage errorMessage = new ErrorMessage();

    private JComboBox<Object> dropDown;
    private JTextField hoursInput;
    private JTextField minutesInput;
    private JButton enterButton;

    public GoalEntryPage(State state, GUIManager guiManager) {
        super(guiManager);
        this.state = state;

        this.actionButtonPanel = new ActionButtonPanel(guiManager, this);
        setupUI();
    }

    @Override
    public String getName() {
        return PageNames.GOAL_ENTRY;
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
    public void clearInput() {
        hoursInput.setText(""); // Clearing hours textbox
        minutesInput.setText(""); // Clearing minutes textbox
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source == enterButton) {
            handleSettingGoal();
        }
    }

    private void handleSettingGoal() {
        if(dropDown.getItemCount() < 1) { // No modules created, so can't add a goal
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
            if(hours.equals("")) {
                hours = Integer.toString(0);

            }
            else if(minutes.equals("")) {
                minutes = Integer.toString(0);
            }

            hoursInt = Integer.parseInt(hours);
            minutesInt = Integer.parseInt(minutes);

            if(hoursInt < 0 || minutesInt < 0) {
                throw new Exception("Input must be greater than 0");

            } else if(hoursInt > 0 && minutesInt > 59) {
                throw new Exception("If an hour is entered minutes should be < 60");
            }

            Duration time = Duration.ofMinutes(hoursInt * 60L + minutesInt);

            // Checks the goal entered is under 1 week
            if(time.toMinutes() > 7 * 24 * 60) {
                throw new Exception("A goal can't be longer than a week");
            }

            Module module = null;

            for(Module m : state.getModules()) {
                if(moduleName.equals(m.getName())) {
                    module = m;
                    break;
                }
            }

            if(module == null) {
                throw new Exception("Unable to find this module");
            }

            module.setGoal((int) time.toMinutes());

            HomePage home = (HomePage) manager.getPage(HomePage.class);
            home.createNewBar(module);

            System.out.println("Goal successfully added");

            clearInput();

        }
        catch(NumberFormatException e) {
            errorMessage(new NumberFormatException("Please enter positive integers"));

        }
        catch(Exception e) {
            errorMessage(e);
        }
    }

    private void errorMessage(Exception message) {
        errorMessage.showMessage(this, message);
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
