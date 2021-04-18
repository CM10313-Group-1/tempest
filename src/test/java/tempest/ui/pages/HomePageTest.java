package tempest.ui.pages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tempest.helpers.GUIHelper;

public class HomePageTest extends GUIHelper{

    @Test
    public void manageModulesButton() {
        homePage.getManageModulesButton().doClick();

        assertEquals(PageNames.MANAGE_MODULES, manager.getCurrentCard());
    }

    @Test
    public void manageSessionsButton_NoModules() {
        homePage.getManageSessionsButton().doClick();

        assertEquals(PageNames.HOME, manager.getCurrentCard());
    }

    @Test
    public void manageSessionsButton_Modules() {
        // Creating a module to enable the manage sessions button
        createModuleChangePage("test");

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
        // Creating a session to enable the chart view button
        homePage.getManageModulesButton().doClick();
        createSession("", "5", createModule("test"));
        manageModules.getBackButton().doClick();

        homePage.getChartViewButton().doClick();

        assertEquals(PageNames.CHART_VIEW, manager.getCurrentCard());
    }

    @Test
    public void manageGoalsButton_NoModules(){
        homePage.getEnterGoalsButton().doClick();

        assertEquals(PageNames.HOME, manager.getCurrentCard());
    }

    @Test
    public void manageGoalsButton_Modules(){
        createModuleChangePage("test");

        homePage.getEnterGoalsButton().doClick();

        assertEquals(PageNames.GOAL_ENTRY, manager.getCurrentCard());
    }

    @Test
    public void DataProtectionButton() {
        homePage.getDataProtectionButton().doClick();
        assertEquals(PageNames.DATA_PROTECTION, manager.getCurrentCard());
    }
}
