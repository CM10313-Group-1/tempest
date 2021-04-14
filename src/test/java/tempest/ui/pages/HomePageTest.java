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
    ManageModulesPage manageModules = (ManageModulesPage) manager.getPage(ManageModulesPage.class);
    GUIHelper helper = new GUIHelper(manager, state);

    @Test
    public void manageModulesButton() {
        homePage.getManageModulesButton().doClick();

        assertEquals(PageNames.MANAGE_MODULES, manager.getCurrentCard());
    }

    @Test
    public void manageSessionsButton() {
        // Creating a module to enable the manage sessions button
        homePage.getManageModulesButton().doClick();
        helper.createModule("test");
        manageModules.getBackButton().doClick();

        homePage.getManageSessionsButton().doClick();

        assertEquals(PageNames.MANAGE_SESSIONS, manager.getCurrentCard());
    }
    @Test
    public void manageSessionsButton_NoModule() {
        homePage.getManageSessionsButton().doClick();

        assertEquals(PageNames.HOME, manager.getCurrentCard());
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
        helper.createSession("", "5", helper.createModule("test"));
        manageModules.getBackButton().doClick();

        homePage.getManageSessionsButton().doClick();
        homePage.getChartViewButton().doClick();

        assertEquals(PageNames.CHART_VIEW, manager.getCurrentCard());
    }
    @Test
    public void DataProtectionButton() {
        homePage.getDataProtectionButton().doClick();

    assertEquals(PageNames.DATA_PROTECTION, manager.getCurrentCard());
    }
}
