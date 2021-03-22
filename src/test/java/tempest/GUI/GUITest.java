package tempest.GUI;

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

    // *** Button Tests ***

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

    // *** Module Tests ***

    public Module createModule(String moduleName){
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
    public void validModule() {
        int prevModuleNum = state.getModules().length;
        createModule("test");

        assertEquals(prevModuleNum + 1, state.getModules().length);
    }

    @Test
    public void nullModule(){
        int prevModuleNum = state.getModules().length;
        createModule("");

        assertEquals(prevModuleNum, state.getModules().length);
    }

    @Test
    public void duplicateModules(){
        int prevModuleNum = state.getModules().length;

        createModule("test");
        createModule("test");

        assertEquals(prevModuleNum + 1, state.getModules().length);
    }

    // *** Session Tests ***

    public int[] createSession(String hours, String mins, Module testModule){
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
    public void hourMinSession() {
        Module testModule = createModule("test");
        int[] result = createSession("1", "47", testModule);
        assertEquals(result[0] + 1, result[1]);
    }

    @Test
    public void hourSession() {
        Module testModule = createModule("test");
        int[] result = createSession("6", "", testModule);
        assertEquals(result[0] + 1, result[1]);
    }

    @Test
    public void minSession(){
        Module testModule = createModule("test");
        int[] result = createSession("", "51", testModule);
        assertEquals(result[0] + 1, result[1]);
    }

    @Test
    public void emptySession() {
        Module testModule = createModule("test");
        int[] result = createSession("", "", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void negHourSession() {
        Module testModule = createModule("test");
        int[] result = createSession("-8", "", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void negMinSession() {
        Module testModule = createModule("test");
        int[] result = createSession("", "-123", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void negHourPosMinSession() {
        Module testModule = createModule("test");
        int[] result = createSession("-5", "242", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void posHourNegMinSession() {
        Module testModule = createModule("test");
        int[] result = createSession("7", "-35", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void negHourMinSession() {
        Module testModule = createModule("test");
        int[] result = createSession("-5", "-41", testModule);
        assertEquals(result[0], result[1]);
    }

    // > 24 hrs

    @Test
    public void hoursSessionOverADay() {
        Module testModule = createModule("test");
        int[] result = createSession("25", "", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void minsSessionOverADay() {
        Module testModule = createModule("test");
        int[] result = createSession("", "1448", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void hourMinSessionOverADay() {
        Module testModule = createModule("test");
        int[] result = createSession("18", "946", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void totalSessionsOverADay() {
        Module testModule = createModule("test");

        int[] result1 = createSession("23", "58", testModule);
        int[] result2 = createSession("", "3", testModule);

        assertEquals(result1[0] + 1, result2[1]);
    }

    // Non-integer

    @Test
    public void nonNumHourSession() {
        Module testModule = createModule("test");
        int[] result = createSession("a", "42", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void nonNumMinSession() {
        Module testModule = createModule("test");
        int[] result = createSession("12", "ab", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void nonNumHourMinSession() {
        Module testModule = createModule("test");
        int[] result = createSession("a", "b", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void decimalHourMinSession() {
        Module testModule = createModule("test");
        int[] result = createSession("3.5", "24.7", testModule);
        assertEquals(result[0], result[1]);
    }

}
