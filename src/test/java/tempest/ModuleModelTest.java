package tempest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.time.Duration;
import java.util.Date;

/**
 * Unit test for simple App.
 */
public class ModuleModelTest {
    private ModuleModelTest i = new ModuleModelTest();

    @Test
    public void addSessionByCreatingSession() {
        Module module = new Module();
        StudySession today = new StudySession(new Date(), Duration.ofHours(1));
        module.addSession(today);
        assertEquals(today, module.studySessionsList.get(-1));
    }

    @Test
    public void addSessionWithDateDuration() {
        Module module = new Module();
        Date date = new Date();
        module.addSession(date, Duration.ofHours(1));
        assertEquals(date, module.studySessionsList.get(-1).date);
        assertEquals(Duration.ofHours(1), module.studySessionsList.get(-1).duration);
    }
}
