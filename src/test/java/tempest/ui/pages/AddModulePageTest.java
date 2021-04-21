package tempest.ui.pages;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

import tempest.Module;
import tempest.helpers.GUIHelper;

public class AddModulePageTest extends GUIHelper {

    @Test
    public void backButton() {
        homePage.getManageModulesButton().doClick();
        manageModules.getAddModuleButton().doClick();

        addModule.getActionButtons().getBackButton().doClick();

        assertEquals(PageNames.MANAGE_MODULES, manager.getCurrentCard());
    }

    @Test
    public void validModule() {
        int prevModuleNum = state.getModules().length;
        createModule("test");

        assertEquals(prevModuleNum + 1, state.getModules().length);
    }

    @Test
    public void nullModule() {
        int prevModuleNum = state.getModules().length;
        createModule("");

        assertEquals(prevModuleNum, state.getModules().length);
    }

    @Test
    public void spaceModule() {
        int prevModuleNum = state.getModules().length;
        createModule(" ");

        assertEquals(prevModuleNum, state.getModules().length);
    }

    @Test
    public void duplicateModules() {
        int prevModuleNum = state.getModules().length;

        createModule("test");

        createModule("test");

        assertEquals(prevModuleNum + 1, state.getModules().length);
    }
}
