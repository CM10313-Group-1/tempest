package tempest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StateTest {
  private final State s = new State();

  @Test
  public void addsModule() {
    int prevModuleNum = s.getModules().length;
    s.createModule("Test");
    assertEquals(prevModuleNum + 1, s.getModules().length);
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
    assertEquals(prevModuleNum, s.getModules().length);
  }
}
