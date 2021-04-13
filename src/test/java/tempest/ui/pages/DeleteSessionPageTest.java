package tempest.ui.pages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tempest.Module;
import tempest.State;
import tempest.Supervisor;
import tempest.helpers.GUIHelper;
import tempest.ui.GUIManager;

public class DeleteSessionPageTest {

    State state = new State();
    GUIManager manager = new GUIManager(state, Supervisor.getInstance());
    ManageModulesPage manageModules = (ManageModulesPage) manager.getPage(ManageModulesPage.class);
    HomePage homePage = (HomePage) manager.getPage(HomePage.class);
    ManageSessionsPage manageSessions = (ManageSessionsPage) manager.getPage(ManageSessionsPage.class);
    DeleteSessionPage deleteSession = (DeleteSessionPage) manager.getPage(DeleteSessionPage.class);
    AddSessionPage addSessionPage = (AddSessionPage) manager.getPage(AddSessionPage.class);

    GUIHelper helper = new GUIHelper(manager, state);

    @Test
    public void backButton() {
        createModuleAndSession();

        manageSessions.getDelSessionsButton().doClick();
        deleteSession.getBackButton().doClick();

        assertEquals(PageNames.MANAGE_SESSIONS, manager.getCurrentCard());
    }

    @Test
    public void deletingASession() {
        Module test = helper.createModule("test");

        helper.createSession("4", "25", test);
        helper.createSession("", "15", test);

        int prevLen = test.getStudySessions().length;

        // Deleting one of the sessions
        manageSessions.getDelSessionsButton().doClick();
        deleteSession.selectRow(1);
        deleteSession.getDeleteButton().doClick();

        assertEquals(prevLen - 1, test.getStudySessions().length);
    }

    @Test
    public void deleteLastSession() {
        createModuleAndSession();

        // Deleting the only session
        deleteSession(0);

        // Asserting the user was kicked out of the delete session page
        assertEquals(PageNames.MANAGE_SESSIONS, manager.getCurrentCard());
    }

    @Test
    public void remainOnPageIfMoreSessions() {
        // Creating a module - enabling the manage sessions button
        homePage.getManageModulesButton().doClick();
        Module test = helper.createModule("test");
        manageModules.getBackButton().doClick();

        homePage.getManageSessionsButton().doClick();

        // Creating two sessions (enabling the delete session button)
        manageSessions.getAddSessionsButton().doClick();
        helper.createSession("4", "25", test);
        helper.createSession("", "15", test);
        addSessionPage.getActionButtons().getBackButtonInstance().doClick();

        // Deleting one of the sessions
        deleteSession(1);

        // Asserting the user remains on the delete session page
        assertEquals(PageNames.DELETE_SESSION, manager.getCurrentCard());
    }

    @Test
    public void changeTableDisplay() {
        //Creating two modules and adding sessions
        Module test = helper.createModule("test");

        helper.createSession("", "25", test);

        Module test2 = helper.createModule("test2");

        helper.createSession("4", "", test2);
        helper.createSession("2", "12", test2);

        manageSessions.getDelSessionsButton().doClick();
        deleteSession.setDropDown(test.getName()); // Selecting 1st module
        deleteSession.setDropDown(test2.getName()); // Selecting 2nd module

        // Asserting the length of sessions displayed matches the selected module's sessions length
        assertEquals(test2.getStudySessions().length, deleteSession.getRowCount());
    }

    /**
     * Deletes the session on the row passed in
     *
     * @param row The row of the session
     */
    private void deleteSession(int row) {
        manageSessions.getDelSessionsButton().doClick();
        deleteSession.selectRow(row);
        deleteSession.getDeleteButton().doClick();
    }

    /**
     * Creates 1 module and 1 study sessions, leaving the GUI on
     * the manageSessionsPage
     *
     * This is a useful method for creating a module and session
     * when the test uses the back button
     *
     */
    private void createModuleAndSession() {
        // Creating a module - enabling the manage sessions button
        homePage.getManageModulesButton().doClick();
        Module module = helper.createModule("test");
        manageModules.getBackButton().doClick();

        homePage.getManageSessionsButton().doClick();

        // Creating a session - enabling the delete session button
        manageSessions.getAddSessionsButton().doClick();
        helper.createSession("1", "", module);
        addSessionPage.getActionButtons().getBackButtonInstance().doClick();
    }
}
