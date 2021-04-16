package tempest.ui.pages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import tempest.Module;
import tempest.helpers.GUIHelper;

import java.util.Random;

public class GoalEntryPageTest extends GUIHelper{

    @Test
    public void validGoalEntry() {
        Random random = new Random();

        Module testModule = createModule("test");

        int hours = random.nextInt(24 * 7);
        int mins = random.nextInt(60);

        goalEntry.setDropDown("test");
        goalEntry.setHours(String.valueOf(hours));
        goalEntry.setMins(String.valueOf(mins));

        goalEntry.getEnterButton().doClick();

        assertEquals(hours * 60 + mins, testModule.getWeeklyGoal());
    }

    @Test
    public void backButton(){
        homePage.getManageModulesButton().doClick();
        createModule("test");
        manageModules.getBackButton().doClick();

        homePage.getEnterGoalsButton().doClick();

        goalEntry.getActionButtons().getBackButton().doClick();

        assertEquals(PageNames.HOME, manager.getCurrentCard());
    }

    @Test
    public void hourGoalOverAWeek(){
        Module testModule = createModule("test");

        goalEntry.setDropDown("test");
        goalEntry.setHours("2000");
        goalEntry.setMins("0");

        goalEntry.getEnterButton().doClick();

        assertEquals(0, testModule.getWeeklyGoal());
    }

    @Test
    public void minGoalOverAWeek(){
        Module testModule = createModule("test");

        goalEntry.setDropDown("test");
        goalEntry.setHours("0");
        goalEntry.setMins("100000");

        goalEntry.getEnterButton().doClick();

        assertEquals(0, testModule.getWeeklyGoal());
    }
}
