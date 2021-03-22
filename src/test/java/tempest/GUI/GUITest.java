package tempest.GUI;

import org.junit.Before;
import org.junit.Test;
import tempest.Module;
import tempest.State;
import tempest.Supervisor;

import static org.junit.Assert.*;

public class GUITest {
    State state = new State();
    GUIManager manager = new GUIManager(state, new Supervisor());

    HomePage homePage = manager.getHomePage();
    AddModulePage modulePage = manager.getModulePage();
    AddSessionPage sessionPage = manager.getSessionPage();

    GUIComponents moduleComponents = modulePage.getComponents();
    GUIComponents sessionComponents = sessionPage.getComponents();

    @Test
    public void addModuleButton() {
        homePage.getAddModuleButton().doClick();

        assertEquals(manager.getCurrentCard(), "addModule");
    }

    @Test
    public void addSessionButton() {
        homePage.getAddSessionButton().doClick();

        assertEquals(manager.getCurrentCard(), "addSession");
    }

    @Test
    public void moduleCancelButton() {
        homePage.getAddModuleButton().doClick();
        moduleComponents.getCancelButton().doClick();

        assertEquals(manager.getCurrentCard(), "home");
    }

    @Test
    public void sessionCancelButton() {
        homePage.getAddSessionButton().doClick();
        sessionComponents.getCancelButton().doClick();

        assertEquals(manager.getCurrentCard(), "home");
    }

    public Module createTestModule(String moduleName){
        Module testModule = null;

        // Creating a new module called test2
        homePage.getAddModuleButton().doClick();
        modulePage.setModuleNameInput(moduleName);
        modulePage.getEnterButton().doClick();

        // Getting the created module
        for (Module m : state.getModules()) {
            if (m.getName().equals(moduleName)) {
                testModule = m;
                break;
            }
        }

        return testModule;
    }

    @Test
    public void addingValidModule() {
        Module testModule = createTestModule("test");
        assertNotNull(testModule);
    }

    @Test
    public void addingNullModule(){
        Module testModule = createTestModule("");
        assertNull(testModule);
    }

    @Test
    public void addingDuplicateModule(){
        int prevModuleNum = state.getModules().length;
        Module testModule = createTestModule("test");
        Module testModule2 = createTestModule("test");
        int newModuleNum = state.getModules().length;

        assertEquals(prevModuleNum + 1, newModuleNum);
    }

    public int[] createTestSession(String hours, String mins, Module testModule){
        int prevSessionsLen = testModule.getStudySessions().length;

        // Adding a study session to test
        homePage.getAddSessionButton().doClick();
        sessionPage.setHours(hours);
        sessionPage.setMins(mins);
        sessionPage.getEnterButton().doClick();

        // Returns values to test
        return new int[]{prevSessionsLen, testModule.getStudySessions().length};
    }

    @Test
    public void addingValidMinHourSession() {
        Module testModule = createTestModule("test");
        int[] result = createTestSession("1", "47", testModule);
        assertEquals(result[0] + 1, result[1]);
    }

    @Test
    public void addingValidMinSession(){
        Module testModule = createTestModule("test");
        int[] result = createTestSession("", "51", testModule);
        assertEquals(result[0] + 1, result[1]);
    }

    @Test
    public void addingValidHourSession() {
        Module testModule = createTestModule("test");
        int[] result = createTestSession("6", "", testModule);
        assertEquals(result[0] + 1, result[1]);
    }

    @Test
    public void addingEmptySession() {
        Module testModule = createTestModule("test");
        int[] result = createTestSession("", "", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void addingNegativeMinSession() {
        Module testModule = createTestModule("test");
        int[] result = createTestSession("", "-123", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void addingNegativeHourSession() {
        Module testModule = createTestModule("test");
        int[] result = createTestSession("-8", "", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void addingNegativeMinHourSession() {
        Module testModule = createTestModule("test");
        int[] result = createTestSession("-5", "-41", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void addingNegativeHourPositiveMinSession() {
        Module testModule = createTestModule("test");
        int[] result = createTestSession("-5", "242", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void addingPositiveHourNegativeMinSession() {
        Module testModule = createTestModule("test");
        int[] result = createTestSession("7", "-35", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void addingHourSessionOverADay() {
        Module testModule = createTestModule("test");
        int[] result = createTestSession("25", "", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void addingMinSessionOverADay() {
        Module testModule = createTestModule("test");
        int[] result = createTestSession("", "1448", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void addingMinHourSessionOverADay() {
        Module testModule = createTestModule("test");
        int[] result = createTestSession("18", "946", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void addingNonNumberMinHourSession() {
        Module testModule = createTestModule("test");
        int[] result = createTestSession("a", "b", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void addingDecimalMinHourSession() {
        Module testModule = createTestModule("test");
        int[] result = createTestSession("3.5", "24.7", testModule);
        assertEquals(result[0], result[1]);
    }

}
