package tempest.ui.pages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

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
        // Creating a module to enable the manage sessions button
        homePage.getManageModulesButton().doClick();
        helper.createModule("test");
        manageModules.getBackButton().doClick();

        homePage.getManageSessionsButton().doClick();
        manageSessions.getBackButton().doClick();

        assertEquals(PageNames.HOME, manager.getCurrentCard());
    }

    @Test
    public void addSessionButton() {
        // Creating a module to enable the manage sessions button
        homePage.getManageModulesButton().doClick();
        helper.createModule("test");
        manageModules.getBackButton().doClick();

        homePage.getManageSessionsButton().doClick();
        manageSessions.getAddSessionsButton().doClick();

        assertEquals(PageNames.ADD_SESSION, manager.getCurrentCard());
    }


    @Test
    public void deleteSessionButton_NoSessions() {
        // Creating a module to enable the manage sessions button
        homePage.getManageModulesButton().doClick();
        helper.createModule("test");
        manageModules.getBackButton().doClick();

        homePage.getManageSessionsButton().doClick();
        manageSessions.getDelSessionsButton().doClick();

        assertEquals(PageNames.MANAGE_SESSIONS, manager.getCurrentCard());
    }

    @Test
    public void deleteSessionButton_Sessions() {
        // Creating a session to enable the delete sessions button
        homePage.getManageModulesButton().doClick();
        helper.createSession("1", "5", helper.createModule("test"));
        manageModules.getBackButton().doClick();

        homePage.getManageSessionsButton().doClick();
        manageSessions.getDelSessionsButton().doClick();

        assertEquals(PageNames.DELETE_SESSION, manager.getCurrentCard());
    }
}
