package tempest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class State implements Serializable {
    private static final long serialVersionUID = 3286962657482468670L;
    public List<Module> modules;

    public State() {
        this.modules = new ArrayList<>();
    }

    public State(Module[] modules) {
        this.modules = new ArrayList<>(Arrays.asList(modules));
    }

    /**
     * Creates a new Module and adds it to the list stored.
     *
     * @param moduleName The name of the new module.
     */
    public void createModule(String moduleName) {
        Module newModule = new Module(moduleName);
        modules.add(newModule);
    }

    /**
     * Removes the given module from the list
     *
     * @param moduleName The name of the module.
     */
    public void deleteModule(String moduleName) {
        modules.removeIf(module -> module.getName().equals(moduleName));
    }

    /**
     * Gets all the modules stored in the state.
     *
     * @return All the modules stored.
     */
    public Module[] getModules() {
        return modules.toArray(new Module[0]);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof State) {
            State other = (State) obj;
            return Arrays.deepEquals(this.getModules(), other.getModules());
        } else {
            return false;
        }
    }
}
