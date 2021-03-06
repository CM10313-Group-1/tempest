package tempest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class State {
  private List<Module> modules = new ArrayList<>();

  //TODO:
  // - deleteModule()
  // - Trophies

  public State() {
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

  public void deleteModule(String moduleID) {

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
