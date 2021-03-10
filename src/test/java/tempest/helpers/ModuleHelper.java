package tempest.helpers;

import java.time.Duration;
import java.util.Date;

import tempest.Module;
import tempest.StudySession;

public class ModuleHelper {
  public static final Date TEST_DATE = new Date();
  public static final Duration TEST_DURATION = Duration.ofHours(1);

  public static Module createTestModule(String name) {
    return new Module("Test");
  }

  public static StudySession createTestSession() {
    return new StudySession(TEST_DATE, TEST_DURATION);
  }
}
