package tempest.ui.pages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import tempest.helpers.GUIHelper;

public class ManageModulesPageTest extends GUIHelper {

    @Test
    public void backButton() {
        homePage.getManageModulesButton().doClick();
        manageModules.getBackButton().doClick();

        assertEquals(PageNames.HOME, manager.getCurrentCard());
    }

    @Test
    public void addModuleButton() {
        manageModules.getAddModuleButton().doClick();

        assertEquals(PageNames.ADD_MODULE, manager.getCurrentCard());
    }

    @Test
    public void deleteModuleButton_NoModules() {
        assertEquals(0, state.getModules().length);

        homePage.getManageModulesButton().doClick();
        manageModules.getDeleteModuleButton().doClick();

        assertEquals(PageNames.MANAGE_MODULES, manager.getCurrentCard());
    }

    @Test
    public void deleteModuleButton_Modules() {
        createModule("test");

        homePage.getManageModulesButton().doClick();
        manageModules.getDeleteModuleButton().doClick();

        assertEquals(PageNames.DELETE_MODULE, manager.getCurrentCard());
    }
}
