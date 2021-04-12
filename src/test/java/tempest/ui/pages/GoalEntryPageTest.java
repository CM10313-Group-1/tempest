package tempest.ui.pages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import tempest.Module;
import tempest.State;
import tempest.Supervisor;
import tempest.helpers.GUIHelper;
import tempest.ui.GUIManager;
import tempest.ui.components.ActionButtonPanel;

import java.util.Random;

public class GoalEntryPageTest {
    State state = new State();
    GUIManager manager = new GUIManager(state, Supervisor.getInstance());

    GoalEntryPage goalEntryPage = new GoalEntryPage(state, manager);

    ActionButtonPanel actionButtonPanel = goalEntryPage.getActionButtons();

    GUIHelper helper = new GUIHelper(manager, state);

    @Test
    public void validGoalEntry(){
        Random random = new Random();

        Module testModule = helper.createModule("test");

        int hours = random.nextInt(24*7);
        int mins = random.nextInt(60);

        goalEntryPage.setDropDown("test");
        goalEntryPage.setHours(String.valueOf(hours));
        goalEntryPage.setMins(String.valueOf(mins));

        goalEntryPage.getEnterButton().doClick();

        assertEquals(hours * 60 + mins, testModule.getWeeklyGoal());
    }
}
