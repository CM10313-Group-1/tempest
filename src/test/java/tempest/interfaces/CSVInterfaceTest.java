package tempest.interfaces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import org.junit.Test;

import tempest.Module;
import tempest.State;
import tempest.StudySession;
import tempest.helpers.ModuleHelper;
import tempest.helpers.StateHelper;

public class CSVInterfaceTest {
  private CSVInterface i = new CSVInterface();
  private final String VALID_READ_FIXTURE = "valid.csv";
  private final String INVALID_READ_FIXTURE = "invalid.csv";
  private final String WRITE_FIXTURE = "target.csv";
  private final String NONEXISTENT_FIXTURE = "VvaPjqzkTNvSULkKfBXViw.csv";

  @Test
  public void findsExistingFile() {
    try {
      File found = i.getFile(VALID_READ_FIXTURE);
      assumeTrue("File does not exist", found.exists());
      assertEquals("Names should match", found.getName(), VALID_READ_FIXTURE);
      assumeTrue("Cannot read to file", found.canRead());
      assumeTrue("Cannot write to file", found.canWrite());
    } catch (Exception e) {
      System.err.println(e.toString());
      fail("File should exist");
    }
  }

  @Test(expected = FileNotFoundException.class)
  public void failsToFindNonExistentFile() throws FileNotFoundException {
    i.getFile(NONEXISTENT_FIXTURE);
  }

  @Test
  public void readsValidState() {
    try {
      State s = i.getState(VALID_READ_FIXTURE);
      assertEquals("Module names match", "Programming", s.getModules()[0].getName());
    } catch (Exception e) {
      System.err.println(e.toString());
      fail("No exceptions should be thrown");
    }
  }

  @Test(expected = ParseException.class)
  public void failsToReadInvalidState() throws ParseException {
    try {
      i.getState(INVALID_READ_FIXTURE);
    } catch (IOException e) {
      System.err.println(e.toString());
      fail("No IO exceptions should be thrown");
    }
  }

  @Test
  public void writesFileHeaders() {
    try {
      i.saveState(new State(), WRITE_FIXTURE);
      BufferedReader tReader = new BufferedReader(new FileReader(i.getFile(WRITE_FIXTURE)));
      String storedHeader = tReader.readLine();
      assertEquals("Header row should be correct", i.generateHeaderRow(), storedHeader);
    } catch (Exception e) {
      System.err.println(e.toString());
      fail("Exceptions should not occur.");
    }
  }

  @Test
  public void writesValidState() {
    try {
      State tState = StateHelper.generateTestState();
      i.saveState(tState, WRITE_FIXTURE);
      BufferedReader tReader = new BufferedReader(new FileReader(i.getFile(WRITE_FIXTURE)));
      tReader.readLine(); // Skip header.
      for (Module m : tState.getModules()) {
        if (m.getStudySessions().length > 0) {
          for (StudySession session : m.getStudySessions()) {
            String storedSession = tReader.readLine();
            assertEquals("Session row should match state", session.toRow(m), storedSession);
          }
        } else {
          String moduleLine = tReader.readLine();
          assertEquals("Module line should match state", m.toBlankRow(), moduleLine);
        }
      }
    } catch (Exception e) {
      System.err.println(e.toString());
      fail("Exceptions should not occur.");
    }
  }

  @Test(expected = FileNotFoundException.class)
  public void failsToSaveToInvalidFile() throws IOException {
    State tState = StateHelper.generateTestState();
    i.saveState(tState, "folder");
  }

  @Test
  public void exportsEmptyModuleData() {
    try {
      Module module = ModuleHelper.createTestModule("Test");
      File destination = i.getFile(WRITE_FIXTURE);
      i.exportModule(module, destination);
      BufferedReader tReader = new BufferedReader(new FileReader(destination));
      tReader.readLine(); // Skip header
      String storedModule = tReader.readLine();
      assertEquals("Stored module should match", module.toBlankRow(), storedModule);
      tReader.close();
    } catch (IOException e) {
      fail("No exceptions should be thrown");
    }
  }

  @Test
  public void exportFullModule() {
    try {
      Module module = ModuleHelper.createTestModule("Test");
      StudySession studySession = ModuleHelper.createTestSession();
      module.addSession(studySession);
      File destination = i.getFile(WRITE_FIXTURE);
      i.exportModule(module, destination);
      BufferedReader tReader = new BufferedReader(new FileReader(destination));
      tReader.readLine(); // Skip header
      for (StudySession session : module.getStudySessions()) {
        String storedSession = tReader.readLine();
        assertEquals("Session row should match state", session.toRow(module), storedSession);
      }
      tReader.close();
    } catch (IOException e) {
      fail("No exceptions should be thrown");
    }
  }
}
