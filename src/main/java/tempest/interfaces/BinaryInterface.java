package tempest.interfaces;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;

import tempest.State;

public class BinaryInterface {

  /**
   * Writes the application state in a binary format to a binary file.
   * 
   * @param state The current state of the application.
   * @throws IOException If the output stream fails fo write the application
   *                     state.
   */
  public void saveState(State state, String filename) throws IOException {
    try {
      URL locator = getClass().getClassLoader().getResource(filename);
      if (locator == null)
        throw new FileNotFoundException(filename + " does not exist.");
      File destination = new File(locator.toURI());
      FileOutputStream fileOutputStream = new FileOutputStream(destination);
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
      objectOutputStream.writeObject(state);
      objectOutputStream.close();
      fileOutputStream.close();
    } catch (Exception e) {
      throw new IOException("Failed to write state to " + filename);
    }
  }

  /**
   * Loads a stored representation of the application state from a binary file.
   */
  public State loadState(String filename) throws IOException {
    try {
      URL locator = getClass().getClassLoader().getResource(filename);
      if (locator == null)
        throw new FileNotFoundException(filename + " does not exist.");
      File origin = new File(locator.toURI());
      FileInputStream fileInputStream = new FileInputStream(origin);
      ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
      State stored = (State) objectInputStream.readObject();
      objectInputStream.close();
      fileInputStream.close();
      return stored;
    } catch (Exception e) {
      throw new IOException("Failed to load state.");
    }
  }
}