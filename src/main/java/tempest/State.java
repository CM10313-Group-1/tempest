package tempest;

import java.util.List;

public class State {
  private List<Module> modules;
  // TODO Trophies

  public State() {
  }

  public State(Module[] modules) {
  }

  public void createModule(String moduleName) {

  }

  public void deleteModule(String moduleID) {

  }

  public Module[] getModules() {
    return (Module[]) this.modules.toArray();
  }

}
