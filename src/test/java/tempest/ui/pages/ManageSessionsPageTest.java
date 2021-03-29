package tempest.ui.pages;

import org.junit.Before;
import org.junit.Test;
import tempest.Module;
import tempest.State;
import tempest.Supervisor;
import tempest.ui.ErrorMessage;
import tempest.ui.GUIManager;

import static org.junit.Assert.*;

public class ManageSessionsPageTest {

    State state = new State();
    GUIManager manager = new GUIManager(state, Supervisor.getInstance());

    HomePage homePage = (HomePage) manager.getPage(HomePage.class);
    ManageSessionsPage manageSessions = (ManageSessionsPage) manager.getPage(ManageSessionsPage.class);
    AddSessionPage addSession = (AddSessionPage) manager.getPage(AddSessionPage.class);

    AddModulePage addModule = (AddModulePage) manager.getPage(AddModulePage.class);

    @Before
    public void turnOffErrorMessages() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessagesShown(false);
    }

    @Test
    public void backButton() {
        homePage.getManageSessionsButton().doClick();
        manageSessions.getBackButton().doClick();

        assertEquals(PageNames.HOME, manager.getCurrentCard());
    }

    @Test
    public void addSessionButton_NoModules() {
        assertEquals(0, state.getModules().length);

        homePage.getManageSessionsButton().doClick();
        manageSessions.getAddSessionsButton().doClick();

        assertEquals(PageNames.MANAGE_SESSIONS, manager.getCurrentCard());
    }

    @Test
    public void addSessionButton_Modules() {
        createModule("test");

        homePage.getManageSessionsButton().doClick();
        manageSessions.getAddSessionsButton().doClick();

        assertEquals(PageNames.ADD_SESSION, manager.getCurrentCard());
    }


    @Test
    public void deleteSessionButton_NoSessions() {
        for (Module m : state.getModules()) {
            assertEquals(0, m.getStudySessions().length);
        }

        homePage.getManageSessionsButton().doClick();
        manageSessions.getDelSessionsButton().doClick();

        assertEquals(PageNames.MANAGE_SESSIONS, manager.getCurrentCard());
    }

    @Test
    public void deleteSessionButton_Sessions() {
        createModule("test");

        createSession("1", "5");

        homePage.getManageSessionsButton().doClick();
        manageSessions.getDelSessionsButton().doClick();

        assertEquals(PageNames.DELETE_SESSION, manager.getCurrentCard());
    }

    public void createModule(String moduleName) {
        addModule.setModuleNameInput(moduleName);
        addModule.getEnterButton().doClick();
    }

    public void createSession(String hours, String mins) {
        addSession.setHours(hours);
        addSession.setMins(mins);
        addSession.getEnterButton().doClick();
    }
}
