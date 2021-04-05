package tempest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StateTest {
  private final State s = new State();

  // TODO: This is a sad test class

  @Test
  public void addsModule() {
    int prevModuleNum = s.getModules().length;
    s.createModule("Test");
    assertEquals(s.getModules().length, prevModuleNum + 1);
    assertEquals("Test", s.getModules()[prevModuleNum].getName());
  }

  @Test
  public void removesModule() {
    s.createModule("Test");
    int prevModuleNum = s.getModules().length;
    s.deleteModule("Test");
    assertEquals(prevModuleNum - 1, s.getModules().length);
  }

  @Test
  public void failsToRemoveNonExistentModule() {
    int prevModuleNum = s.getModules().length;
    s.deleteModule("Test");
    assertEquals(s.getModules().length, prevModuleNum);
  }
}
