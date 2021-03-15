package tempest.interfaces;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import tempest.Module;
import tempest.State;
import tempest.StudySession;

public class CSVInterface {
  // TODO move all IO exceptions to InvalidStateExceptions outside of getFile
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
   * @deprecated Supplanted by the {@link #saveState(State, String)}.
   * @param state The overall state of the application.
   * @throws FileNotFoundException If no file was previously accessed.
   * @throws IOException           If the previously loaded file is a directory.
   */
  @Deprecated
  public void saveState(State state) throws FileNotFoundException, IOException {
    File destination = getFile(loadedFilename);
    writeState(state, destination);
  }

  /**
   * Takes a given state and stores it in the specified file.
   * 
   * @param state    The overall state of the application.
   * @param fallback The filename the state is to be stored in.
   * @throws FileNotFoundException If no file was previously accessed.
   * @throws IOException           If filename is a directory.
   */
  public void saveState(State state, String fallback) throws FileNotFoundException, IOException {
    File destination;
    if (loadedFilename != null) {
      destination = getFile(loadedFilename);
    } else {
      destination = getFile(fallback);
    }

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
    writeModules(state.getModules(), destination);
  }

  /**
   * Writes all module data to a given file.
   * 
   * @param modules     The modules.
   * @param destination The destination file.
   * @throws IOException If the destination file is a directory.
   */
  public void writeModules(Module[] modules, File destination) throws IOException {
    try (FileWriter fw = new FileWriter(destination)) {
      BufferedWriter writer = new BufferedWriter(fw);
      writer.write(generateHeaderRow());
      writer.newLine();
      for (Module m : modules) {
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
   * Writes a module to a given file.
   * 
   * @param module      The module to be written out.
   * @param destination The destination to write the module to.
   * @throws IOException If the file provided is a directory.
   */
  public void exportModule(Module module, File destination) throws IOException {
    try (FileWriter fw = new FileWriter(destination)) {
      BufferedWriter writer = new BufferedWriter(fw);
      writer.write(generateHeaderRow());
      writer.newLine();

      String[] rows = module.toRows();
      for (String row : rows) {
        writer.write(row);
        writer.newLine();
      }

      writer.close();
    } catch (IOException e) {
      throw new IOException(destination.getName() + " is a directory.");
    }
  }

  /**
   *
   * @param filename The filename where the state is stored.
   * @return State retrieved from file.
   * @throws FileNotFoundException If filename is invalid or not found.
   * @throws ParseException        If the file is not compatible with the current
   *                               version of state.
   */
  public State getState(String filename) throws IOException, ParseException {
    File source = getFile(filename);
    return readState(source);
  }

  /**
   * Reads the state stored in the given file.
   * 
   * @param source The file containing the state.
   * @return The state stored in the file.
   * @throws IOException    If the file provided is a directory.
   * @throws ParseException If the file cannot be parsed for any reason.
   */
  private State readState(File source) throws IOException, ParseException {
    try (FileReader fr = new FileReader(source)) {
      BufferedReader reader = new BufferedReader(fr);
      String header = reader.readLine();

      if (!isCorrectFormat(header)) {
        reader.close();
        throw new ParseException("Format of " + source.getName() + " is incompatible.", 0);
      } else {
        Module[] modules = parseModules(reader);
        reader.close();
        return new State(modules);
      }
    } catch (IOException e) {
      throw new IOException(source.getName() + " is a directory.");
    }
  }

  /**
   * Generates modules for all modules stored in the file accessed by a
   * {@link BufferedReader}.
   * 
   * @param reader The reader of the storage file.
   * @return The modules stored in the file.
   * @throws IOException    If the reader cannot read the file.
   * @throws ParseException If the file cannot be parsed.
   */
  protected Module[] parseModules(BufferedReader reader) throws IOException, ParseException {
    HashMap<UUID, Module> modules = new HashMap<UUID, Module>();
    String line;

    while ((line = reader.readLine()) != null) {
      String[] attributes = line.split(String.valueOf(DELIMITER));
      UUID id = UUID.fromString(attributes[0]);
      if (modules.containsKey(id)) {
        Module acquired = modules.get(id);
        StudySession s = new StudySession(StudySession.STORED_DATE_FORMAT.parse(attributes[2]),
            Duration.ofMinutes(Long.valueOf(attributes[3])));
        acquired.addSession(s);
        modules.put(id, acquired);
      } else {
        Module newModule = new Module(attributes[0], attributes[1]);
        if (!attributes[2].isEmpty()) {
          StudySession s = new StudySession(StudySession.STORED_DATE_FORMAT.parse(attributes[2]),
              Duration.ofMinutes(Long.valueOf(attributes[3])));
          newModule.addSession(s);
        }
        modules.put(id, newModule);
      }
    }

    return modules.values().toArray(new Module[] {});
  }

  /**
   * Compares the headers of the stored file with the currently used ones.
   * 
   * @param headerRow The headerRow in the database.
   * @return True if the headers match.
   */
  protected boolean isCorrectFormat(String headerRow) {
    String[] headers = headerRow.split(String.valueOf(DELIMITER));
    return Arrays.equals(headers, HEADINGS);
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
