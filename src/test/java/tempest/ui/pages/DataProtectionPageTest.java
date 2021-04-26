package tempest.ui.pages;

import org.junit.Test;
import tempest.helpers.GUIHelper;

import static org.junit.Assert.assertEquals;

public class DataProtectionPageTest extends GUIHelper {

    @Test
    public void backButtonTest() {
        homePage.getDataProtectionButton().doClick();
        dataProtection.getBackButton().doClick();

        assertEquals(PageNames.HOME, manager.getCurrentCard());
    }
}
