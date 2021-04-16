package tempest.ui.pages;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import tempest.Module;
import tempest.State;
import tempest.Supervisor;
import tempest.helpers.GUIHelper;
import tempest.ui.ErrorMessage;
import tempest.ui.GUIManager;
import tempest.ui.components.ActionButtonPanel;

import java.util.Random;

public class GoalEntryPageTest {
    State state = new State();
    GUIManager manager = new GUIManager(state, Supervisor.getInstance());

    GoalEntryPage goalEntryPage = (GoalEntryPage) manager.getPage(GoalEntryPage.class);
    HomePage homePage = (HomePage) manager.getPage(HomePage.class);
    ManageModulesPage manageModules = (ManageModulesPage) manager.getPage(ManageModulesPage.class);

    ActionButtonPanel actionButtonPanel = goalEntryPage.getActionButtons();

    GUIHelper helper = new GUIHelper(manager, state);

    @BeforeClass
    public static void turnOffErrorMessages() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessagesShown(false);
    }

    @Test
    public void validGoalEntry() {
        Random random = new Random();

        Module testModule = helper.createModule("test");

        int hours = random.nextInt(24 * 7);
        int mins = random.nextInt(60);

        goalEntryPage.setDropDown("test");
        goalEntryPage.setHours(String.valueOf(hours));
        goalEntryPage.setMins(String.valueOf(mins));

        goalEntryPage.getEnterButton().doClick();

        assertEquals(hours * 60 + mins, testModule.getWeeklyGoal());
    }

    @Test
    public void backButton(){
        homePage.getManageModulesButton().doClick();
        helper.createModule("test");
        manageModules.getBackButton().doClick();

        homePage.getEnterGoalsButton().doClick();

        actionButtonPanel.getBackButtonInstance().doClick();

        assertEquals(PageNames.HOME, manager.getCurrentCard());
    }

    @Test
    public void hourGoalOverAWeek(){
        Module testModule = helper.createModule("test");

        goalEntryPage.setDropDown("test");
        goalEntryPage.setHours("2000");
        goalEntryPage.setMins("0");

        goalEntryPage.getEnterButton().doClick();

        assertEquals(0, testModule.getWeeklyGoal());
    }

    @Test
    public void minGoalOverAWeek(){
        Module testModule = helper.createModule("test");

        goalEntryPage.setDropDown("test");
        goalEntryPage.setHours("0");
        goalEntryPage.setMins("100000");

        goalEntryPage.getEnterButton().doClick();

        assertEquals(0, testModule.getWeeklyGoal());
    }
}
