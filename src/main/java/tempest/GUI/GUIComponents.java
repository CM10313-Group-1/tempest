package tempest.GUI;

import tempest.Module;
import tempest.State;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//TODO:
// - Current page - 1 in actionPerformed() -> Use a currentCard variable in GUIManger w/ getter? or just a separate specific previousPanel() method?
// - Separate drop down and button panel into their own classes???
// - As JComboBox is static might have same problem as cancel button???

public class GUIComponents implements ActionListener {

    private static JComboBox<Object> moduleDropDown;

    private JButton cancelButton;

    private GUIManager manager;

    public JPanel getButtonPanel(GUIManager manager, Page page) {
        this.manager = manager;

        JPanel buttonPanel = new JPanel();

        cancelButton = new JButton("Cancel");
        JButton enterButton = new JButton("Enter");

        cancelButton.setFocusable(false);
        enterButton.setFocusable(false);

        cancelButton.addActionListener(this);
        enterButton.addActionListener(page);

        buttonPanel.add(cancelButton);
        buttonPanel.add(enterButton);

        return buttonPanel;
    }

    public void createModuleDropDown(State state) {
        moduleDropDown = new JComboBox<>();

        // Populating drop down with the names of all current modules
        for (Module m : state.getModules()) {
            moduleDropDown.addItem(m.getName());
        }
    }

    public JComboBox<Object> getModuleDropDown() {
        return moduleDropDown;
    }

    public void addModule(String name) {
        moduleDropDown.addItem(name);
    }

    public void removeModule(String name) {
        moduleDropDown.removeItem(name);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelButton) {
            manager.changePanel(1); // Current page - 1
        }
    }
}
