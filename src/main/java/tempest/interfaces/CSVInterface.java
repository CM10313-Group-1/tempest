package tempest.interfaces;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import tempest.Module;
import tempest.State;

public class CSVInterface {
  private String loadedFilename;
  public static final String[] HEADINGS = { "id", "moduleName", "date", "duration" };
  public static final char DELIMITER = ',';

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
   * @throws IOException           If the previously loaded file is a directory.
   */
  public void saveState(State state) throws FileNotFoundException, IOException {
    File destination = getFile(loadedFilename);
    writeState(state, destination);
  }

  /**
   * Takes a given state and stores it in the specified file.
   * 
   * @param state    The overall state of the application.
   * @param filename The filename the state is to be stored in.
   * @throws FileNotFoundException If no file was previously accessed.
   * @throws IOException           If filename is a directory.
   */
  public void saveState(State state, String filename) throws FileNotFoundException, IOException {
    File destination = getFile(filename);
    writeState(state, destination);
  }

  /**
   * Writes the state to the specified destination in CSV format.
   * 
   * @param state       The state to write.
   * @param destination The destination to write to.
   * @throws IOException If the file provided is a directory.
   */
  private void writeState(State state, File destination) throws IOException {
    try (FileWriter fw = new FileWriter(destination)) {
      BufferedWriter writer = new BufferedWriter(fw);
      writer.write(generateHeaderRow());
      writer.newLine();
      for (Module m : state.getModules()) {
        String[] rows = m.toRows();
        for (String row : rows) {
          writer.write(row);
          writer.newLine();
        }
      }
      writer.close();
    } catch (IOException e) {
      throw new IOException(destination.getName() + " is a directory.");
    }
  }

  /**
   *
   * @param filename The filename where the state is stored
   * @return State retrieved from file
   * @throws FileNotFoundException If filename is invalid or not found
   */
  public State getState(String filename) throws FileNotFoundException {
    File destination = getFile(filename);
    return readState(destination);
  }

  private State readState(File destination) {
    try {
      Scanner reader = new Scanner(destination);
      return new State();
    } catch (FileNotFoundException e) {
      // TODO: handle exception
      return null;
    }
  }

  /**
   * Generates column headers for storage in the CSV file.
   * 
   * @return A string of delimited column headers.
   */
  protected String generateHeaderRow() {
    return String.join(String.valueOf(DELIMITER), HEADINGS);
  }
}
