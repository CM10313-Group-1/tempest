package tempest.interfaces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Test;

import tempest.State;

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

  @Test
  public void failsToFindNonExistentFile() {
    assertThrows(FileNotFoundException.class, () -> i.getFile(nonExistentFixture));
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

  }

  @Test
  public void failsToSaveToInvalidFile() {

  }

  @Test
  public void exportsModuleData() {

  }

}
