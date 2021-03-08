package tempest.interfaces;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;

public class CSVInterface {
  protected File getFile(String filename) throws FileNotFoundException, URISyntaxException {
    URL resource = getClass().getClassLoader().getResource(filename);
    if (resource == null) {
      throw new FileNotFoundException(filename + " does not exist in resources directory.");
    } else {
      return new File(resource.toURI());
    }
  }
}
