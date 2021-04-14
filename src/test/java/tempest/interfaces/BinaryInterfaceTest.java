package tempest.interfaces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

import tempest.State;
import tempest.helpers.StateHelper;

public class BinaryInterfaceTest {
  private BinaryInterface i = new BinaryInterface();
  private final String VALID_READ_FIXTURE = "valid.bin";
  private final String INVALID_READ_FIXTURE = "invalid.bin";
  private final String WRITE_FIXTURE = "target.bin";
  private final String NONEXISTENT_FIXTURE = "VvaPjqzkTNvSULkKfBXViw.bin";

  @Test
  public void readsValidState() {
    try {
      State s = i.loadState(VALID_READ_FIXTURE);
      assertEquals("Module names match", StateHelper.generateTestState(), s);
    } catch (Exception e) {
      System.err.println(e.toString());
      fail("No exceptions should be thrown");
    }
  }

  @Test(expected = IOException.class)
  public void failsToWriteToNonExistentFile() throws IOException {
    State tState = StateHelper.generateTestState();
    i.saveState(tState, NONEXISTENT_FIXTURE);
  }

  @Test(expected = IOException.class)
  public void failsToReadInvalidState() throws IOException {
    i.loadState(INVALID_READ_FIXTURE);
  }

  @Test
  public void writesValidState() {
    try {
      State tState = StateHelper.generateTestState();
      i.saveState(tState, WRITE_FIXTURE);
      State storedState = i.loadState(WRITE_FIXTURE);
      assertEquals("States are the same", tState, storedState);
    } catch (Exception e) {
      System.err.println(e.toString());
      fail("Exceptions should not occur.");
    }
  }

  @Test(expected = IOException.class)
  public void failsToSaveToInvalidFile() throws IOException {
    State tState = StateHelper.generateTestState();
    i.saveState(tState, "folder");
  }
}
