package tempest.ui.pages;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import tempest.State;
import tempest.Module;
import tempest.Supervisor;
import tempest.ui.GUIManager;
import tempest.ui.ErrorMessage;
import tempest.ui.components.ActionButtonPanel;

public class AddSessionPageTest {

    State state = new State();
    GUIManager manager = new GUIManager(state, Supervisor.getInstance());

    HomePage homePage = (HomePage) manager.getPage(HomePage.class);
    ManageSessionsPage manageSessions = (ManageSessionsPage) manager.getPage(ManageSessionsPage.class);

    AddSessionPage addSession = (AddSessionPage) manager.getPage(AddSessionPage.class);

    ActionButtonPanel actionButtonPanel = addSession.getActionButtons();

    GUIHelper helper = new GUIHelper(manager, state);

    @BeforeClass
    public static void turnOffErrorMessages() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessagesShown(false);
    }

    @Test
    public void backButton() {
        helper.createModule("test");

        homePage.getManageSessionsButton().doClick();
        manageSessions.getAddSessionsButton().doClick();
        actionButtonPanel.getBackButtonInstance().doClick();

        assertEquals(PageNames.MANAGE_SESSIONS, manager.getCurrentCard());
    }

    @Test
    public void hourMinSession() {
        Module testModule = helper.createModule("test");
        int[] result = helper.createSessionReturn("1", "47", testModule);
        assertEquals(result[0] + 1, result[1]);
    }

    @Test
    public void hourSession() {
        Module testModule = helper.createModule("test");
        int[] result = helper.createSessionReturn("6", "", testModule);
        assertEquals(result[0] + 1, result[1]);
    }

    @Test
    public void minSession() {
        Module testModule = helper.createModule("test");
        int[] result = helper.createSessionReturn("", "51", testModule);
        assertEquals(result[0] + 1, result[1]);
    }

    @Test
    public void emptySession() {
        Module testModule = helper.createModule("test");
        int[] result = helper.createSessionReturn("", "", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void negHourSession() {
        Module testModule = helper.createModule("test");
        int[] result = helper.createSessionReturn("-8", "", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void negMinSession() {
        Module testModule = helper.createModule("test");
        int[] result = helper.createSessionReturn("", "-123", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void negHourPosMinSession() {
        Module testModule = helper.createModule("test");
        int[] result = helper.createSessionReturn("-5", "242", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void posHourNegMinSession() {
        Module testModule = helper.createModule("test");
        int[] result = helper.createSessionReturn("7", "-35", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void negHourMinSession() {
        Module testModule = helper.createModule("test");
        int[] result = helper.createSessionReturn("-5", "-41", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void HourMinOver59Session() {
        Module testModule = helper.createModule("test");
        int[] result = helper.createSessionReturn("2", "67", testModule);
        assertEquals(result[0], result[1]);
    }

    // > 24 hrs

    @Test
    public void hoursSessionOverADay() {
        Module testModule = helper.createModule("test");
        int[] result = helper.createSessionReturn("25", "", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void minsSessionOverADay() {
        Module testModule = helper.createModule("test");
        int[] result = helper.createSessionReturn("", "1448", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void totalSessionsOverADay() {
        Module testModule = helper.createModule("test");

        int[] result1 = helper.createSessionReturn("23", "58", testModule);

        int[] result2 = helper.createSessionReturn("", "3", testModule);

        assertEquals(result1[0] + 1, result2[1]);
    }

    // Non-integer

    @Test
    public void nonNumHourSession() {
        Module testModule = helper.createModule("test");
        int[] result = helper.createSessionReturn("a", "42", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void nonNumMinSession() {
        Module testModule = helper.createModule("test");
        int[] result = helper.createSessionReturn("12", "ab", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void nonNumHourMinSession() {
        Module testModule = helper.createModule("test");
        int[] result = helper.createSessionReturn("a", "b", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void decimalHourMinSession() {
        Module testModule = helper.createModule("test");
        int[] result = helper.createSessionReturn("3.5", "24.7", testModule);
        assertEquals(result[0], result[1]);
    }
}
