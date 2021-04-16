package tempest.ui.pages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tempest.helpers.GUIHelper;

public class DeleteModulePageTest extends GUIHelper{

    @Test
    public void backButton() {
        createModule("test");

        homePage.getManageModulesButton().doClick();
        manageModules.getDeleteModuleButton().doClick();
        deleteModule.getBackButton().doClick();

        assertEquals(manageModules.getName(), manager.getCurrentCard());
    }

    @Test
    public void deletingAModule() {
        createModule("test");

        int length = state.getModules().length;

        homePage.getManageModulesButton().doClick();
        manageModules.getDeleteModuleButton().doClick();
        deleteModule.getDeleteButton().doClick();

        assertEquals(length - 1, state.getModules().length);
    }

    @Test
    public void deleteLastModule() {
        createModule("test");

        homePage.getManageModulesButton().doClick();
        manageModules.getDeleteModuleButton().doClick();
        deleteModule.getDeleteButton().doClick();

        assertEquals(PageNames.MANAGE_MODULES, manager.getCurrentCard());
    }

    @Test
    public void remainOnPageIfMoreModules() {
        createModule("test");
        createModule("test2");

        homePage.getManageModulesButton().doClick();
        manageModules.getDeleteModuleButton().doClick();
        deleteModule.getDeleteButton().doClick();

        assertEquals(PageNames.DELETE_MODULE, manager.getCurrentCard());
    }
}
