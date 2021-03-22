package tempest.GUI.components;

import tempest.Module;
import tempest.State;

import javax.swing.*;

public class ModuleDropDown {

    private static JComboBox<Object> moduleDropDown;

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
}
