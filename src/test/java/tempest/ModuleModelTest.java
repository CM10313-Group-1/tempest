package tempest;

import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.util.Date;

import org.junit.Test;

/**
 * Unit test for the Module class.
 */
public class ModuleModelTest {
    @Test
    public void addSessionByCreatingSession() {
        Module module = new Module();
        StudySession today = new StudySession(new Date(), Duration.ofHours(1));
        module.addSession(today);
        assertEquals(today, module.studySessionsList.get(0));
    }

    @Test
    public void addSessionWithDateDuration() {
        Module module = new Module();
        Date date = new Date();
        module.addSession(date, Duration.ofHours(1));
        assertEquals(date, module.studySessionsList.get(0).date);
        assertEquals(Duration.ofHours(1), module.studySessionsList.get(0).duration);
    }
}
