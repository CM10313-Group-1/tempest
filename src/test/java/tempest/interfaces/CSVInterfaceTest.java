package tempest.interfaces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Test;

public class CSVInterfaceTest {

  private CSVInterface i = new CSVInterface();
  final String fixtureLocation = "test.csv";
  final String nonExistentFixture = "VvaPjqzkTNvSULkKfBXViw.csv";

  @Test
  public void findsExistingFile() {
    try {
      File found = i.getFile(fixtureLocation);
      assumeTrue("File does not exist", found.exists());
      assertEquals("Names should match", found.getName(), fixtureLocation);
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
  public void savesState() {

  }

  @Test
  public void failsToSaveToInvalidFile() {

  }

  @Test
  public void exportsModuleData() {

  }

}
