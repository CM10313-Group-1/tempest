package tempest.ui.pages;

import org.junit.Test;
import tempest.State;
import tempest.Supervisor;
import tempest.ui.GUIManager;


import static org.junit.Assert.assertEquals;

public class DataProtectionPageTest {
    State state = new State();
    GUIManager manager = new GUIManager(state, Supervisor.getInstance());

    HomePage homePage = (HomePage) manager.getPage(HomePage.class);
    DataProtectionPage dataProtection = (DataProtectionPage) manager.getPage(DataProtectionPage.class);

    @Test
    public void backButtonTest() {
        homePage.getDataProtectionButton().doClick();
        dataProtection.getBackButton().doClick();

        assertEquals(PageNames.HOME, manager.getCurrentCard());
    }
}
