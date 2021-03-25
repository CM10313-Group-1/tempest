package tempest.GUI;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import tempest.Module;
import tempest.State;
import tempest.Supervisor;
import tempest.GUI.components.ActionButtonPanel;

public class AddSessionPageTest {
    GUIHelpher helper = new GUIHelpher();

    Supervisor supervisor = Supervisor.getInstance();

    State state = supervisor.getState();
    GUIManager manager = supervisor.getManager();

    HomePage homePage = (HomePage) manager.getPage(HomePage.class);
    ManageSessionsPage manageSessions = (ManageSessionsPage) manager.getPage(ManageSessionsPage.class);
    ManageModulesPage manageModules = (ManageModulesPage) manager.getPage(ManageModulesPage.class);

    AddModulePage modulePage = (AddModulePage) manager.getPage(AddModulePage.class);
    AddSessionPage addSessionPage = (AddSessionPage) manager.getPage(AddSessionPage.class);

    ActionButtonPanel actionButtonPanel = addSessionPage.getComponents();

    @Before
    public void turnOffErrorMessages() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessagesShown(false);
    }

    @Test
    public void addSessionCancelButton() {
        homePage.getManageSessionsButton().doClick();
        manageSessions.getAddSessionsButton().doClick();
        actionButtonPanel.getBackButtonInstance().doClick();

        assertEquals(manageSessions.getName(), manager.getCurrentCard());
    }

    public Module createModule(String moduleName) {
        Module testModule = null;

        // Creating a new module

        //TODO: Test if need to move to the modules page
        homePage.getManageModulesButton().doClick();
        manageModules.getAddModuleButton().doClick();
        modulePage.setModuleNameInput(moduleName);
        modulePage.getEnterButton().doClick();

        // Getting the created module
        for (Module m : state.getModules()) {
            if (moduleName.equals(m.getName())) {
                testModule = m;
                break;
            }
        }

        return testModule;
    }

    public int[] createSession(String hours, String mins, Module testModule) {
        int prevSessionsLen = testModule.getStudySessions().length;

        // Adding a study session to test
        addSessionPage.setHours(hours);
        addSessionPage.setMins(mins);
        addSessionPage.getEnterButton().doClick();

        // Returns values to test
        return new int[] { prevSessionsLen, testModule.getStudySessions().length };
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
    public void minSession() {
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

    @Test
    public void HourMinOver59Session() {
        Module testModule = createModule("test");
        int[] result = createSession("2", "67", testModule);
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
    public void totalSessionsOverADay() {
        Module testModule = createModule("test");

        int[] result1 = createSession("23", "58", testModule);

        actionButtonPanel.getBackButtonInstance().doClick();

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
