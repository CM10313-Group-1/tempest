package tempest.GUI;

import tempest.Module;
import tempest.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class AddSessionPage extends JFrame implements ActionListener{
    private ArrayList<Module> modules;
    private State state;
    private JComboBox<Object> moduleDropDown;
    private JTextField hoursInput;
    private JTextField minutesInput;
    private JButton enterButton;
    private JButton cancelButton;
    private JPanel buttonPanel;

    public AddSessionPage(State state){this.state = state;}

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

    }
}
