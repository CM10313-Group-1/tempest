package tempest.ui.pages;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import tempest.State;
import tempest.Supervisor;
import tempest.ui.ErrorMessage;
import tempest.ui.GUIManager;

public class HomePageTest {
    State state = new State();
    GUIManager manager = new GUIManager(state, Supervisor.getInstance());

    HomePage homePage = (HomePage) manager.getPage(HomePage.class);

    // Turns off the error message popups so the tests are not blocked
    @Before
    public void turnOffErrorMessages() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessagesShown(false);
    }

    @Test
    public void manageModulesButton() {
        homePage.getManageModulesButton().doClick();

        assertEquals(PageNames.MANAGE_MODULES, manager.getCurrentCard());
    }

    @Test
    public void manageSessionsButton() {
        homePage.getManageSessionsButton().doClick();

        assertEquals(PageNames.ADD_SESSION, manager.getCurrentCard());
    }

    //TODO: Chart Test
}
