package tempest.ui.pages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tempest.Module;
import tempest.helpers.GUIHelper;

public class DeleteSessionPageTest extends GUIHelper{

    @Test
    public void backButton() {
        createSession("1", "", createModule("test"));

        homePage.getManageSessionsButton().doClick();
        manageSessions.getDelSessionsButton().doClick();
        deleteSession.getBackButton().doClick();

        assertEquals(PageNames.MANAGE_SESSIONS, manager.getCurrentCard());
    }

    @Test
    public void deletingASession() {
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
        createSession("4", "25", createModule("test"));

        // Deleting this session
        homePage.getManageSessionsButton().doClick();
        manageSessions.getDelSessionsButton().doClick();
        deleteSession.selectRow(0);
        deleteSession.getDeleteButton().doClick();

        assertEquals(PageNames.MANAGE_SESSIONS, manager.getCurrentCard());
    }

    @Test
    public void remainOnPageIfMoreSessions() {
        Module test = createModule("test");

        createSession("4", "25", test);
        createSession("", "15", test);

        // Deleting one of the sessions
        homePage.getManageSessionsButton().doClick();
        manageSessions.getDelSessionsButton().doClick();
        deleteSession.selectRow(0);
        deleteSession.getDeleteButton().doClick();

        assertEquals(PageNames.DELETE_SESSION, manager.getCurrentCard());
    }

    @Test
    public void changeTableDisplay() {
        Module test = createModule("test");

        createSession("", "25", test);

        Module test2 = createModule("test2");

        createSession("4", "", test2);
        createSession("2", "12", test2);

        homePage.getManageSessionsButton().doClick();
        manageSessions.getDelSessionsButton().doClick();

        deleteSession.setDropDown(test.getName());

        deleteSession.setDropDown(test2.getName());
        assertEquals(test2.getStudySessions().length, deleteSession.getRowCount());
    }
}
