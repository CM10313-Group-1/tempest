package tempest.ui.pages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tempest.Module;
import tempest.helpers.GUIHelper;

public class AddSessionPageTest extends GUIHelper{

    @Test
    public void backButton() {
        // Creating a module to enable the manage sessions button
        createModuleChangePage("test");

        homePage.getManageSessionsButton().doClick();
        manageSessions.getAddSessionsButton().doClick();
        addSession.getBackButton().doClick();

        assertEquals(PageNames.MANAGE_SESSIONS, manager.getCurrentCard());
    }

    @Test
    public void hourMinSession() {
        Module testModule = createModule("test");
        int[] result = createSessionReturn("1", "47", testModule);
        assertEquals(result[0] + 1, result[1]);
    }

    @Test
    public void hourSession() {
        Module testModule = createModule("test");
        int[] result = createSessionReturn("6", "", testModule);
        assertEquals(result[0] + 1, result[1]);
    }

    @Test
    public void minSession() {
        Module testModule = createModule("test");
        int[] result = createSessionReturn("", "51", testModule);
        assertEquals(result[0] + 1, result[1]);
    }

    @Test
    public void emptySession() {
        Module testModule = createModule("test");
        int[] result = createSessionReturn("", "", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void negHourSession() {
        Module testModule = createModule("test");
        int[] result = createSessionReturn("-8", "", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void negMinSession() {
        Module testModule = createModule("test");
        int[] result = createSessionReturn("", "-123", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void negHourPosMinSession() {
        Module testModule = createModule("test");
        int[] result = createSessionReturn("-5", "242", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void posHourNegMinSession() {
        Module testModule = createModule("test");
        int[] result = createSessionReturn("7", "-35", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void negHourMinSession() {
        Module testModule = createModule("test");
        int[] result = createSessionReturn("-5", "-41", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void HourMinOver59Session() {
        Module testModule = createModule("test");
        int[] result = createSessionReturn("2", "67", testModule);
        assertEquals(result[0], result[1]);
    }

    // > 24 hrs

    @Test
    public void hoursSessionOverADay() {
        Module testModule = createModule("test");
        int[] result = createSessionReturn("25", "", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void minsSessionOverADay() {
        Module testModule = createModule("test");
        int[] result = createSessionReturn("", "1448", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void totalSessionsOverADay_1Module() {
        Module testModule = createModule("test");

        int[] result1 = createSessionReturn("23", "58", testModule);

        int[] result2 = createSessionReturn("", "3", testModule);

        assertEquals(result1[0] + 1, result2[1]);
    }

    @Test
    public void totalSessionsOverADay_MultipleModules() {
        createSession("10", "45",  createModule("test1"));

        createSession("10", "", createModule("test2"));

        int[] result3 = createSessionReturn("4", "50", createModule("test3"));

        assertEquals(result3[0], result3[1]);
    }

    // Non-integer

    @Test
    public void nonNumHourSession() {
        Module testModule = createModule("test");
        int[] result = createSessionReturn("a", "42", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void nonNumMinSession() {
        Module testModule = createModule("test");
        int[] result = createSessionReturn("12", "ab", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void nonNumHourMinSession() {
        Module testModule = createModule("test");
        int[] result = createSessionReturn("a", "b", testModule);
        assertEquals(result[0], result[1]);
    }

    @Test
    public void decimalHourMinSession() {
        Module testModule = createModule("test");
        int[] result = createSessionReturn("3.5", "24.7", testModule);
        assertEquals(result[0], result[1]);
    }
}
