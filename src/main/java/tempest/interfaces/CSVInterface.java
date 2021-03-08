package tempest.interfaces;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;

public class CSVInterface {

  /**
   * Acquires a given file in the <code>resources</code> directory.
   * 
   * @param filename The filename to be acquired.
   * @return A file object representing the acquired file.
   * @throws FileNotFoundException
   * @throws URISyntaxException
   */
  protected File getFile(String filename) throws FileNotFoundException, URISyntaxException {
    URL resource = getClass().getClassLoader().getResource(filename);
    if (resource == null) {
      throw new FileNotFoundException(filename + " does not exist in resources directory.");
    } else {
      return new File(resource.toURI());
    }
  }
}
