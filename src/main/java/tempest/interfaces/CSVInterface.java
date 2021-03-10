package tempest.interfaces;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;

import tempest.State;

public class CSVInterface {
  private String loadedFilename;

  /**
   * Acquires a given file in the <code>resources</code> directory.
   * 
   * @param filename The filename to be acquired.
   * @return A file object representing the acquired file.
   * @throws FileNotFoundException
   */
  protected File getFile(String filename) throws FileNotFoundException {
    try {
      URL resource = getClass().getClassLoader().getResource(filename);
      if (resource == null) {
        throw new FileNotFoundException(filename + " does not exist in resources directory.");
      } else {
        File foundFile = new File(resource.toURI());
        loadedFilename = filename;
        return foundFile;
      }
    } catch (URISyntaxException e) {
      throw new FileNotFoundException(filename + " url does not comply with RFC 2396.");
    }
  }

  /**
   * Takes a given state and stores it in the last accessed file.
   * 
   * @param state The overall state of the application.
   * @throws FileNotFoundException If no file was previously accessed.
   */
  public void saveState(State state) throws FileNotFoundException {
    if (loadedFilename == null)
      throw new FileNotFoundException("Could not acquire file to save to.");
  }

  /**
   * Takes a given state and stores it in the specified file.
   * 
   * @param state    The overall state of the application.
   * @param filename The filename the state is to be stored in.
   * @throws FileNotFoundException If no file was previously accessed.
   */
  public void saveState(State state, String filename) throws FileNotFoundException {
    File destination = getFile(filename);
  }
}
