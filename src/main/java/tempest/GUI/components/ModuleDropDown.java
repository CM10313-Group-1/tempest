package tempest.GUI.components;

import tempest.Module;
import tempest.State;

import javax.swing.*;
import java.util.ArrayList;

public class ModuleDropDown {

    private final State state;

    private static final ArrayList<JComboBox<Object>> dropDowns = new ArrayList<>();

    public ModuleDropDown(State state) {
        this.state = state;
    }

    /**
     * Returns a module drop down
     *
     * @return JComboBox containing all created module's names
     */
    public JComboBox<Object> getModuleDropDown() {
        JComboBox<Object> dropDown = new JComboBox<>();

        setNames(dropDown, state.getModules());

        dropDowns.add(dropDown);

        return dropDown;
    }

    /**
     * Sets the contents of dropDown to hold the names of the
     * module array
     *
     * @param dropDown The drop down to be changed
     * @param modules Array of modules
     */
    private void setNames(JComboBox<Object> dropDown, Module[] modules) {
        dropDown.removeAllItems();

        // Populating drop down with the names of all current modules
        for (Module m : modules) {
            dropDown.addItem(m.getName());
        }
    }

    /**
     * Should be called after creating a new module with state
     *
     * Updates all the drop downs to have this new module
     *
     * @param name Name of module
     */
    public void addModule(String name) {
        for (JComboBox<Object> dropDown : dropDowns) {
            dropDown.addItem(name);
        }
    }

    /**
     * Should be called after deleting a module with state
     *
     * Updates all the drop downs to no longer have this module
     *
     * @param name Name of module
     */
    public void removeModule(String name) {
        for (JComboBox<Object> dropDown : dropDowns) {
            dropDown.removeItem(name);
        }
    }
}
