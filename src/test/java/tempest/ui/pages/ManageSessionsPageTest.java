package tempest.ui.pages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tempest.Module;
import tempest.helpers.GUIHelper;

public class ManageSessionsPageTest extends GUIHelper {

    @Test
    public void backButton() {
        // Creating a module to enable the manage sessions button
        homePage.getManageModulesButton().doClick();
        createModule("test");
        manageModules.getBackButton().doClick();

        homePage.getManageSessionsButton().doClick();
        manageSessions.getBackButton().doClick();

        assertEquals(PageNames.HOME, manager.getCurrentCard());
    }

    @Test
    public void addSessionButton() {
        // Creating a module to enable the manage sessions button
        homePage.getManageModulesButton().doClick();
        createModule("test");
        manageModules.getBackButton().doClick();

        homePage.getManageSessionsButton().doClick();
        manageSessions.getAddSessionsButton().doClick();

        assertEquals(PageNames.ADD_SESSION, manager.getCurrentCard());
    }


    @Test
    public void deleteSessionButton_NoSessions() {
        // Creating a module to enable the manage sessions button
        homePage.getManageModulesButton().doClick();
        createModule("test");
        manageModules.getBackButton().doClick();

        homePage.getManageSessionsButton().doClick();
        manageSessions.getDelSessionsButton().doClick();

        assertEquals(PageNames.MANAGE_SESSIONS, manager.getCurrentCard());
    }

    @Test
    public void deleteSessionButton_Sessions() {
        // Creating a session to enable the delete sessions button
        homePage.getManageModulesButton().doClick();
        createSession("1", "5", createModule("test"));
        manageModules.getBackButton().doClick();

        homePage.getManageSessionsButton().doClick();
        manageSessions.getDelSessionsButton().doClick();

        assertEquals(PageNames.DELETE_SESSION, manager.getCurrentCard());
    }
}
