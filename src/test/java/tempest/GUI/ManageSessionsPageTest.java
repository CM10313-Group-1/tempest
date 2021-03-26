package tempest.GUI;

import org.junit.Before;
import org.junit.Test;
import tempest.State;
import tempest.Supervisor;

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
    public void deleteSessionButton() {
        homePage.getManageSessionsButton().doClick();

        //FIXME: Not working as button disabled
        manageSessionsPage.getDelSessionsButton().doClick();

        assertEquals(manager.getPageName(DeleteSessionPage.class), manager.getCurrentCard());
    }

    @Test
    public void deleteSessionButton2() {
        // Add a module

        // Add a session for this module

        homePage.getManageSessionsButton().doClick();

        manageSessionsPage.getDelSessionsButton().doClick();

        assertEquals(manager.getPageName(DeleteSessionPage.class), manager.getCurrentCard());
    }

    @Test
    public void addSessionButton() {
        homePage.getManageSessionsButton().doClick();

        //FIXME: Won't work when greying out implemented
        manageSessionsPage.getAddSessionsButton().doClick();

        assertEquals(manager.getPageName(AddSessionPage.class), manager.getCurrentCard());
    }

    //

    // Delete sessions button is disabled when no sessions, otherwise enabled

    //



}