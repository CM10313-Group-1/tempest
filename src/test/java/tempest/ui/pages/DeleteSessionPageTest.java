package tempest.ui.pages;

import org.junit.Test;
import tempest.Module;
import tempest.State;
import tempest.Supervisor;
import tempest.ui.GUIManager;

import static org.junit.Assert.assertEquals;

public class DeleteSessionPageTest {

    State state = new State();
    GUIManager manager = new GUIManager(state, Supervisor.getInstance());

    HomePage homePage = (HomePage) manager.getPage(HomePage.class);
    ManageSessionsPage manageSessions = (ManageSessionsPage) manager.getPage(ManageSessionsPage.class);
    DeleteSessionPage deleteSession = (DeleteSessionPage) manager.getPage(DeleteSessionPage.class);

    GUIHelper helper = new GUIHelper(manager, state);

    @Test
    public void backButton() {
        helper.createSession("1", "", helper.createModule("test"));

        homePage.getManageSessionsButton().doClick();
        manageSessions.getDelSessionsButton().doClick();
        deleteSession.getBackButton().doClick();

        assertEquals(PageNames.MANAGE_SESSIONS, manager.getCurrentCard());
    }

    @Test
    public void deleteSession() {
        Module test = helper.createModule("test");

        helper.createSession("4", "25", test);
        helper.createSession("", "15", test);

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
        Module test = helper.createModule("test");

        helper.createSession("4", "25", test);

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
        Module test = helper.createModule("test");

        helper.createSession("", "25", test);

        Module test2 = helper.createModule("test2");

        helper.createSession("4", "", test2);
        helper.createSession("2", "12", test2);

        homePage.getManageSessionsButton().doClick();
        manageSessions.getDelSessionsButton().doClick();

        deleteSession.setDropDown(test.getName());
        assertEquals(test.getStudySessions().length, deleteSession.getRowCount());

        deleteSession.setDropDown(test2.getName());
        assertEquals(test2.getStudySessions().length, deleteSession.getRowCount());
    }
}