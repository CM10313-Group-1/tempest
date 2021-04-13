package tempest.ui.pages;

import org.junit.Test;
import tempest.helpers.GUIHelper;

import static org.junit.Assert.assertEquals;

public class ChartViewPageTest extends GUIHelper{

    private void createSession() {
        homePage.getManageSessionsButton().doClick();
        createSession("", "5", createModule("test"));
        manageSessions.getBackButton().doClick();
    }

    @Test
    public void backButton() {
        // Creating a session so the chart view button isn't greyed out
        createSession();

        // Going to the chart view page and then pressing the back button
        homePage.getChartViewButton().doClick();
        chartView.getBackButton().doClick();

        assertEquals(PageNames.HOME, manager.getCurrentCard());
    }

    @Test
    public void stackedBarChartButton() {
        chartView.getStackedBarChartButton().doClick();

        assertEquals(PageNames.STACKED_BAR, manager.getCurrentCard());
    }

    @Test
    public void lineChartButton() {
        chartView.getLineChartButton().doClick();

        assertEquals(PageNames.LINE, manager.getCurrentCard());
    }

    @Test
    public void pieChartButton() {
        chartView.getPieChartButton().doClick();

        assertEquals(PageNames.PIE, manager.getCurrentCard());
    }

    @Test
    public void timeBarChartButton() {
        chartView.getTimeBarChartButton().doClick();

        assertEquals(PageNames.TIME_BAR, manager.getCurrentCard());
    }
}
