package tempest.ui.pages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tempest.State;
import tempest.Supervisor;
import tempest.helpers.GUIHelper;
import tempest.ui.GUIManager;

public class DeleteModulePageTest {
    State state = new State();
    GUIManager manager = new GUIManager(state, Supervisor.getInstance());

    HomePage homePage = (HomePage) manager.getPage(HomePage.class);
    ManageModulesPage manageModules = (ManageModulesPage) manager.getPage(ManageModulesPage.class);
    DeleteModulePage deleteModule = (DeleteModulePage) manager.getPage(DeleteModulePage.class);

    GUIHelper helper = new GUIHelper(manager, state);

    @Test
    public void backButton() {
        helper.createModule("test");

        homePage.getManageModulesButton().doClick();
        manageModules.getDeleteModuleButton().doClick();
        deleteModule.getBackButton().doClick();

        assertEquals(manageModules.getName(), manager.getCurrentCard());
    }

    @Test
    public void deleteModule() {
        helper.createModule("test");

        int length = state.getModules().length;

        homePage.getManageModulesButton().doClick();
        manageModules.getDeleteModuleButton().doClick();
        deleteModule.getDeleteButton().doClick();

        assertEquals(length - 1, state.getModules().length);
    }

    @Test
    public void swapToPrevIfNoModules() {
        helper.createModule("test");

        homePage.getManageModulesButton().doClick();
        manageModules.getDeleteModuleButton().doClick();
        deleteModule.getDeleteButton().doClick();

        assertEquals(PageNames.MANAGE_MODULES, manager.getCurrentCard());
    }

    @Test
    public void stayIfMoreModules() {
        helper.createModule("test");
        helper.createModule("test2");

        homePage.getManageModulesButton().doClick();
        manageModules.getDeleteModuleButton().doClick();
        deleteModule.getDeleteButton().doClick();

        assertEquals(PageNames.DELETE_MODULE, manager.getCurrentCard());
    }
}
