package tempest.GUI;

import org.junit.Before;
import org.junit.Test;
import tempest.State;
import tempest.Supervisor;

import static org.junit.Assert.*;

public class GUIManagerTest {
    State state = new State();
    GUIManager manager = new GUIManager(state, new Supervisor());

    HomePage homePage = manager.getHomePage();

    // *** Button Tests ***

    // Turns off the error message popups so the tests are not blocked
    @Before
    public void turnOffErrorMessages(){
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessagesShown(false);
    }

    @Test
    public void addModuleButton() {
        homePage.getAddModuleButton().doClick();

        assertEquals(manager.getCurrentCard(), "addModule");
    }

    @Test
    public void addSessionButton() {
        //TODO: FIX
        homePage.getSessionsButton().doClick();

        assertEquals(manager.getCurrentCard(), "addSession");
    }
}
