package tempest.ui.pages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tempest.Module;
import tempest.State;
import tempest.Supervisor;
import tempest.helpers.GUIHelper;
import tempest.ui.GUIManager;

public class ManageSessionsPageTest {

    State state = new State();
    GUIManager manager = new GUIManager(state, Supervisor.getInstance());
    ManageModulesPage manageModules = (ManageModulesPage) manager.getPage(ManageModulesPage.class);
    HomePage homePage = (HomePage) manager.getPage(HomePage.class);
    ManageSessionsPage manageSessions = (ManageSessionsPage) manager.getPage(ManageSessionsPage.class);

    GUIHelper helper = new GUIHelper(manager, state);

    @Test
    public void backButton() {
        homePage.getManageModulesButton().doClick();
        helper.createModule("test");
        manageModules.getBackButton().doClick();
        homePage.getManageSessionsButton().doClick();
        manageSessions.getBackButton().doClick();

        assertEquals(PageNames.HOME, manager.getCurrentCard());
    }

    @Test
    public void addSessionButton_Modules() {
        helper.createModule("test");

        homePage.getManageSessionsButton().doClick();
        manageSessions.getAddSessionsButton().doClick();

        assertEquals(PageNames.ADD_SESSION, manager.getCurrentCard());
    }


    @Test
    public void deleteSessionButton_NoSessions() {
        for (Module m : state.getModules()) {
            assertEquals(0, m.getStudySessions().length);
        }
        homePage.getManageModulesButton().doClick();
        helper.createModule("test");
        manageModules.getBackButton().doClick();
        homePage.getManageSessionsButton().doClick();
        manageSessions.getDelSessionsButton().doClick();

        assertEquals(PageNames.MANAGE_SESSIONS, manager.getCurrentCard());
    }

    @Test
    public void deleteSessionButton_Sessions() {
        helper.createSession("1", "5", helper.createModule("test"));

        homePage.getManageSessionsButton().doClick();
        manageSessions.getDelSessionsButton().doClick();

        assertEquals(PageNames.DELETE_SESSION, manager.getCurrentCard());
    }
}
