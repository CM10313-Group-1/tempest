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
        module.addSession(ModuleHelper.createTestSession());
        assertEquals(ModuleHelper.TEST_DATE, module.getStudySessions()[0].date);
        assertEquals(ModuleHelper.TEST_DURATION, module.getStudySessions()[0].duration);
    }

    @Test
    public void convertModuleToArray_withZeroSessions() {
        Module module = ModuleHelper.createTestModule("Test");
        String[] outputRow = module.toRows();
        assertEquals(module.getID() + "," + module.getName() + ",", outputRow[0]);
    }

    @Test
    public void convertModuleToArray_withSessions() {
        Module module = ModuleHelper.createTestModule("Test");
        for (int i = 0; i < 5; i++) {
            module.addSession(ModuleHelper.createTestSession());
        }

        String[] output = module.toRows();

        for (int i = 0; i < 5; i++) {
            assertEquals(module.getID() + "," + module.getName() + ","
                    + StudySession.STORED_DATE_FORMAT.format(module.getStudySessions()[i].date) + ","
                    + module.getStudySessions()[i].duration.toMinutes(), output[i]);
        }

    }

}
