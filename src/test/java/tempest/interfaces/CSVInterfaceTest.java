package tempest.interfaces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Test;

import tempest.Module;
import tempest.State;
import tempest.StudySession;
import tempest.helpers.StateHelper;

public class CSVInterfaceTest {
  private CSVInterface i = new CSVInterface();
  private final String readLocation = "test.csv";
  private final String writeLocation = "target.csv";
  private final String nonExistentFixture = "VvaPjqzkTNvSULkKfBXViw.csv";

  @Test
  public void findsExistingFile() {
    try {
      File found = i.getFile(readLocation);
      assumeTrue("File does not exist", found.exists());
      assertEquals("Names should match", found.getName(), readLocation);
      assumeTrue("Cannot read to file", found.canRead());
      assumeTrue("Cannot write to file", found.canWrite());
    } catch (Exception e) {
      fail("File should exist");
    }
  }

  @Test(expected = FileNotFoundException.class)
  public void failsToFindNonExistentFile() throws FileNotFoundException {
    i.getFile(nonExistentFixture);
  }

  @Test
  public void readsValidState() {

  }

  @Test
  public void failsToReadInvalidState() {

  }

  @Test
  public void writesFileHeaders() {
    try {
      i.saveState(new State(), writeLocation);
      BufferedReader tReader = new BufferedReader(new FileReader(i.getFile(writeLocation)));
      String storedHeader = tReader.readLine();
      assertEquals("Header row should be correct", i.generateHeaderRow(), storedHeader);
    } catch (Exception e) {
      fail("Exceptions should not occur.");
    }
  }

  @Test
  public void writesValidState() {
    try {
      State tState = StateHelper.generateTestState();
      i.saveState(tState, writeLocation);
      BufferedReader tReader = new BufferedReader(new FileReader(i.getFile(writeLocation)));
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
      fail("Exceptions should not occur.");
    }
  }

  @Test
  public void failsToSaveToInvalidFile() {

  }

  @Test
  public void exportsModuleData() {

  }

}
