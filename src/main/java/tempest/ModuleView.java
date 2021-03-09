package tempest;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ModuleView extends JFrame{

    //could create the 'home' menu as soon as the class constructor is called

    public static void main(String[] args){
        ArrayList<String> modules = new ArrayList<>();
        modules.add("Maths");
        modules.add("PoP");
        modules.add("Sys Arch");
        createInputWindow(1, modules);
    }

    //0 would be to create a new module and 1 would be to add to an existing module
    public static void createInputWindow(int indicator, ArrayList<String> modules){
        JFrame window = new JFrame();

        if(indicator == 0){
            JPanel createModulePanel = new JPanel();

            JLabel createModuleLabel = new JLabel("Enter module name:");
            JTextField createModuleInput = new JTextField(20);

            createModulePanel.add(createModuleLabel);
            createModulePanel.add(createModuleInput);
            createModulePanel.setLayout(new FlowLayout());

            window.add(createModulePanel);
        }

        if(indicator == 1){
            JComboBox<Object> moduleDropDown = new JComboBox<>(modules.toArray());
            window.add(moduleDropDown);

            JTextField hoursInput = new JTextField(2);
            JLabel hoursInputLabel = new JLabel("Hours");

            JTextField minutesInput = new JTextField(2);
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

            window.add(timeInputPanel);
        }

        JPanel buttonPanel = new JPanel();

        JButton enterButton = new JButton("Enter");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(enterButton);
        buttonPanel.add(cancelButton);
        buttonPanel.setLayout(new FlowLayout());
        window.add(buttonPanel);

        window.setLayout(new BoxLayout(window.getContentPane(), BoxLayout.Y_AXIS));
        window.pack();
        window.setVisible(true);
    }
}
