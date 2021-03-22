package tempest.GUI;

import tempest.Module;
import tempest.State;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//TODO:
// - In future might need a separate getCancelButton()? - if pages don't need an enter button
// - Separate drop down and button panel into their own classes?

public class GUIComponents implements ActionListener {

    private static JComboBox<Object> moduleDropDown;

    private JButton cancelButton;

    private GUIManager manager;

    /**
     * Returns a button panel containing an enter button and a cancel button
     *
     * The enter button needs to be retrieved and handled in the class which calls
     * this method
     *
     * The cancel button is handled already
     *
     * @param manager Manager instance
     * @param page this (instance of class calling getButtonPanel)
     * @return JPanel
     */
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

    /**
     * Returns a clear button
     *
     * The page calling this needs a clearInput() method
     *
     * Just add this button to the page's panel
     *
     * @param page this (instance of class calling getButtonPanel)
     * @return JButton - A 'Clear' button
     */
    public JButton getClearButton(Page page) {
        JButton clearButton = new JButton("Clear");
        clearButton.setFocusable(false);

        clearButton.addActionListener(e -> page.clearInput());

        return clearButton;
    }

    /**
     * Should only be called once - creating the module drop down
     *
     * @param state State instance
     */
    public void createModuleDropDown(State state) {
        moduleDropDown = new JComboBox<>();
        // Populating drop down with the names of all current modules
        for (Module m : state.getModules()) {
            moduleDropDown.addItem(m.getName());
        }
    }

    /**
     * Returns a centralised module drop down that is updated when the module
     * list changes
     *
     * @return JComboBox containing all created modules
     */
    public JComboBox<Object> getModuleDropDown() {
        return moduleDropDown;
    }

    /**
     * Adds a new module to the module drop down
     *
     * @param name Name of module
     */
    public void addModule(String name) {
        moduleDropDown.addItem(name);
    }

    /**
     * Removes the passed in module from the module drop down
     *
     * @param name Name of module
     */
    public void removeModule(String name) {
        moduleDropDown.removeItem(name);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelButton) {
            manager.swapToPrevCard();
        }
    }

    /**
     * Returns a cancel button
     *
     * Used by the test classes to get a pages cancel button
     *
     * @return JButton
     */
    public JButton getCancelButton() {
        return cancelButton;
    }
}
