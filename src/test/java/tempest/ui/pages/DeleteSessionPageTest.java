package tempest.ui.pages;

import org.junit.Before;
import org.junit.Test;
import tempest.Module;
import tempest.State;
import tempest.Supervisor;
import tempest.ui.ErrorMessage;
import tempest.ui.GUIManager;

import static org.junit.Assert.assertEquals;

public class DeleteSessionPageTest {

    State state = new State();
    GUIManager manager = new GUIManager(state, Supervisor.getInstance());

    HomePage homePage = (HomePage) manager.getPage(HomePage.class);
    ManageModulesPage manageModules = (ManageModulesPage) manager.getPage(ManageModulesPage.class);
    ManageSessionsPage manageSessions = (ManageSessionsPage) manager.getPage(ManageSessionsPage.class);
    AddSessionPage addSession = (AddSessionPage) manager.getPage(AddSessionPage.class);
    DeleteSessionPage deleteSession = (DeleteSessionPage) manager.getPage(DeleteSessionPage.class);

    AddModulePage addModule = (AddModulePage) manager.getPage(AddModulePage.class);

    // Test changing the drop fills table with correct sessions???

    // A newly added sessions appears in the table???

    @Before
    public void turnOffErrorMessages() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessagesShown(false);
    }

    @Test
    public void backButton() {
        createSession("1", "", createModule("test"));

        homePage.getManageSessionsButton().doClick();
        manageSessions.getDelSessionsButton().doClick();
        deleteSession.getBackButton().doClick();

        assertEquals(PageNames.MANAGE_SESSIONS, manager.getCurrentCard());
    }

    @Test
    public void deleteSession() {
        Module test = createModule("test");

        createSession("4", "25", test);
        createSession("", "15", test);

        int prevLen = test.getStudySessions().length;

        // Deleting one of the sessions
        homePage.getManageSessionsButton().doClick();
        manageSessions.getDelSessionsButton().doClick();
        deleteSession.selectRow(1);
        deleteSession.getDeleteButton().doClick();

        assertEquals(prevLen - 1, test.getStudySessions().length);
    }

    @Test
    public void deleteLastSession() {
        Module test = createModule("test");

        createSession("4", "25", test);

        int prevLen = test.getStudySessions().length;

        // Deleting this session
        homePage.getManageSessionsButton().doClick();
        manageSessions.getDelSessionsButton().doClick();
        deleteSession.selectRow(0);
        deleteSession.getDeleteButton().doClick();

        assertEquals(prevLen - 1, test.getStudySessions().length);

        assertEquals(PageNames.MANAGE_SESSIONS, manager.getCurrentCard());
    }

    @Test
    public void changeTableDisplay() {
        Module test = createModule("test");

        createSession("", "25", test);

        //int prevLen = test.getStudySessions().length;

        Module test2 = createModule("test2");

        createSession("4", "", test2);
        createSession("2", "12", test2);

        homePage.getManageSessionsButton().doClick();
        manageSessions.getDelSessionsButton().doClick();

        deleteSession.setDropDown(test.getName());
        assertEquals(test.getStudySessions().length, deleteSession.getRowCount());

        deleteSession.setDropDown(test2.getName());
        assertEquals(test2.getStudySessions().length, deleteSession.getRowCount());
    }

    public Module createModule(String moduleName) {
        Module testModule = null;

        // Creating module
        homePage.getManageModulesButton().doClick();
        manageModules.getAddModuleButton().doClick();
        addModule.setModuleNameInput(moduleName);
        addModule.getEnterButton().doClick();

        // Getting the created module
        for (Module m : state.getModules()) {
            if (moduleName.equals(m.getName())) {
                testModule = m;
                break;
            }
        }

        return testModule;
    }

    public void createSession(String hours, String mins, Module module) {
        addSession.setDropDown(module.getName());

        addSession.setHours(hours);
        addSession.setMins(mins);
        addSession.getEnterButton().doClick();
    }
}
