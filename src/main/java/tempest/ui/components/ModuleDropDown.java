package tempest.ui.components;

import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import tempest.Module;
import tempest.State;

public class ModuleDropDown {

    private static DefaultComboBoxModel<Object> model;

    /**
     * This constructor should be used by all classes that need a ModuleDropDown
     * instance
     */
    public ModuleDropDown() {

    }

    /**
     * This constructor should only be called once and only by the GUIManager
     *
     * Creates the drop down model that all drop downs will use
     *
     * @param state Instance of state from the manager
     */
    public ModuleDropDown(State state) {
        ArrayList<Object> names = new ArrayList<>();

        for (Module m : state.getModules()) {
            names.add(m.getName());
        }

        model = new DefaultComboBoxModel<>(names.toArray());
    }

    /**
     * Returns a module drop down
     *
     * @return JComboBox containing all created module's names
     */
    public JComboBox<Object> getModuleDropDown() {
        return new JComboBox<>(model);
    }

    /**
     * Should be called after creating a new module with state
     *
     * Updates all the drop downs to have this new module
     *
     * @param name Name of the new module
     */
    public void addModule(String name) {
        model.addElement(name);
    }

    /**
     * Should be called after deleting a module with state
     *
     * Updates all the drop downs to no longer have this module
     *
     * @param name Name of the deleted module
     */
    public void removeModule(String name) {
        model.removeElement(name);
    }
}