package tempest.GUI;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import tempest.State;
import tempest.Supervisor;

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
    public void addModuleButton() {
        homePage.getAddModuleButton().doClick();

        assertEquals(manager.getCurrentCard(), manager.getPageName(AddModulePage.class));
    }

    @Test
    public void addSessionButton() {
        //TODO: FIX
        homePage.getSessionsButton().doClick();

        assertEquals(manager.getCurrentCard(), manager.getPageName(AddSessionPage.class));
    }
}
