package tempest.interfaces;

import java.io.IOException;

public class FileFormatException extends IOException {
  private static final long serialVersionUID = 3333L;

  public FileFormatException() {
  }

  public FileFormatException(String message) {
    super(message);
  }

}
