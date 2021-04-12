package tempest.ui.pages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tempest.State;
import tempest.Supervisor;
import tempest.helpers.GUIHelper;
import tempest.ui.GUIManager;

public class HomePageTest {
    State state = new State();
    GUIManager manager = new GUIManager(state, Supervisor.getInstance());

    HomePage homePage = (HomePage) manager.getPage(HomePage.class);
    ManageSessionsPage manageSessions = (ManageSessionsPage) manager.getPage(ManageSessionsPage.class);

    GUIHelper helper = new GUIHelper(manager, state);

    @Test
    public void manageModulesButton() {
        homePage.getManageModulesButton().doClick();

        assertEquals(PageNames.MANAGE_MODULES, manager.getCurrentCard());
    }

    @Test
    public void manageSessionsButton() {
        homePage.getManageSessionsButton().doClick();

        assertEquals(PageNames.MANAGE_SESSIONS, manager.getCurrentCard());
    }

    @Test
    public void chartViewButton_NoSessions() {
        homePage.getChartViewButton().doClick();

        assertEquals(PageNames.HOME, manager.getCurrentCard());
    }

    @Test
    public void chartViewButton_Sessions() {
        homePage.getManageSessionsButton().doClick();

        helper.createSession("", "5", helper.createModule("test"));

        manageSessions.getBackButton().doClick();

        homePage.getChartViewButton().doClick();

        assertEquals(PageNames.CHART_VIEW, manager.getCurrentCard());
    }

    @Test
    public void manageGoalsButton(){
        homePage.getEnterGoalsButton().doClick();

        assertEquals(PageNames.GOAL_ENTRY, manager.getCurrentCard());
    }
}
