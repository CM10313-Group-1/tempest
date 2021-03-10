package tempest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tempest.helpers.ModuleHelper;

/**
 * Unit test for the Module class.
 */
public class ModuleModelTest {
    @Test
    public void addSessionWithObject() {
        Module module = ModuleHelper.createTestModule("Test");
        StudySession ss = new StudySession(ModuleHelper.TEST_DATE, ModuleHelper.TEST_DURATION);
        module.addSession(ss);
        assertEquals(ss, module.getStudySessions()[0]);
    }

    @Test
    public void addSessionWithDateDuration() {
        Module module = ModuleHelper.createTestModule("Test");
        module.addSession(ModuleHelper.TEST_DATE, ModuleHelper.TEST_DURATION);
        assertEquals(ModuleHelper.TEST_DATE, module.getStudySessions()[0].date);
        assertEquals(ModuleHelper.TEST_DURATION, module.getStudySessions()[0].duration);
    }
}
