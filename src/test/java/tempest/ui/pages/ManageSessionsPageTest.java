package tempest.ui.pages;

import org.junit.Before;
import org.junit.Test;
import tempest.State;
import tempest.Supervisor;
import tempest.ui.ErrorMessage;
import tempest.ui.GUIManager;

import static org.junit.Assert.*;

public class ManageSessionsPageTest {

    //TODO:
    // - Helper class contains methods to create modules and sessions

    State state = new State();
    GUIManager manager = new GUIManager(state, Supervisor.getInstance());

    HomePage homePage = (HomePage) manager.getPage(HomePage.class);
    ManageSessionsPage manageSessionsPage = (ManageSessionsPage) manager.getPage(ManageSessionsPage.class);
    AddSessionPage addSessionPage = (AddSessionPage) manager.getPage(AddSessionPage.class);

    @Before
    public void turnOffErrorMessages() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessagesShown(false);
    }

    // Buttons

    @Test
    public void backButton() {
        homePage.getManageSessionsButton().doClick();
        manageSessionsPage.getBackButton().doClick();

        assertEquals(homePage.getName(), manager.getCurrentCard());
    }

    @Test
    public void deleteSessionButtonNoSessions() {
        assertEquals(0, state.getModules().length);

        homePage.getManageSessionsButton().doClick();

        manageSessionsPage.getDelSessionsButton().doClick();

        // Delete sessions button is disabled as no sessions so current page is still manage sessions

        assertEquals(PageNames.MANAGE_SESSIONS, manager.getCurrentCard());
    }

    @Test
    public void deleteSessionButtonSessions() {
        // Add a module

        // Add a session for this module

        homePage.getManageSessionsButton().doClick();

        manageSessionsPage.getDelSessionsButton().doClick();

        assertEquals(PageNames.DELETE_SESSION, manager.getCurrentCard());
    }

    @Test
    public void addSessionButton() {
        homePage.getManageSessionsButton().doClick();

        //FIXME: Won't work when greying out implemented
        manageSessionsPage.getAddSessionsButton().doClick();

        assertEquals(PageNames.ADD_SESSION, manager.getCurrentCard());
    }
}
