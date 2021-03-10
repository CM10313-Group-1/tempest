package tempest.helpers;

import tempest.Module;
import tempest.State;

public class StateHelper {
  public static State generateTestState() {
    Module pop1 = new Module("Principles of Programming 1");
    Module pop2 = ModuleHelper.createTestModule("Principles of Programming 2");
    Module[] modules = new Module[] { pop1, pop2 };
    return new State(modules);
  }
}
