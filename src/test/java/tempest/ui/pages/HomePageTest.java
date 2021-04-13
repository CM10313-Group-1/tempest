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

        createSession("", "5", createModule("test"));

        manageSessions.getBackButton().doClick();

        homePage.getChartViewButton().doClick();

        assertEquals(PageNames.CHART_VIEW, manager.getCurrentCard());
    }
}
