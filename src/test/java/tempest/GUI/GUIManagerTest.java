package tempest.GUI;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import tempest.State;
import tempest.Supervisor;
import tempest.ui.ErrorMessage;
import tempest.ui.GUIManager;
import tempest.ui.pages.HomePage;
import tempest.ui.pages.ManageModulesPage;
import tempest.ui.pages.ManageSessionsPage;

public class GUIManagerTest {
    State state = new State();
    GUIManager manager = new GUIManager(state, Supervisor.getInstance());

    HomePage homePage = (HomePage) manager.getPage(HomePage.class);

    // *** Button Tests ***

    // Turns off the error message popups so the tests are not blocked
    @Before
    public void turnOffErrorMessages() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessagesShown(false);
    }

    @Test
    public void manageModulesButton() {
        homePage.getManageModulesButton().doClick();

        assertEquals(manager.getCurrentCard(), manager.getPageName(ManageModulesPage.class));
    }

    @Test
    public void manageSessionsButton() {
        homePage.getManageSessionsButton().doClick();

        assertEquals(manager.getCurrentCard(), manager.getPageName(ManageSessionsPage.class));
    }
}
