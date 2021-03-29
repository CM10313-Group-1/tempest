package tempest.ui.pages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tempest.State;
import tempest.Supervisor;
import tempest.ui.GUIManager;

public class DeleteModulePageTest {
    State state = new State();
    GUIManager manager = new GUIManager(state, Supervisor.getInstance());

    HomePage homePage = (HomePage) manager.getPage(HomePage.class);
    ManageModulesPage manageModules = (ManageModulesPage) manager.getPage(ManageModulesPage.class);
    DeleteModulePage deleteModule = (DeleteModulePage) manager.getPage(DeleteModulePage.class);
    AddModulePage addModule = (AddModulePage) manager.getPage(AddModulePage.class);

    @Test
    public void backButton() {
        createModule("test");

        homePage.getManageModulesButton().doClick();
        manageModules.getDeleteModuleButton().doClick();
        deleteModule.getBackButton().doClick();

        assertEquals(manageModules.getName(), manager.getCurrentCard());
    }

    @Test
    public void deleteModule() {
        createModule("test");

        int length = state.getModules().length;

        homePage.getManageModulesButton().doClick();
        manageModules.getDeleteModuleButton().doClick();
        deleteModule.getDeleteButton().doClick();

        assertEquals(length - 1, state.getModules().length);
    }

    @Test
    public void swapToPrevIfNoModules() {
        createModule("test");

        homePage.getManageModulesButton().doClick();
        manageModules.getDeleteModuleButton().doClick();
        deleteModule.getDeleteButton().doClick();

        assertEquals(PageNames.MANAGE_MODULES, manager.getCurrentCard());
    }

    @Test
    public void stayIfMoreModules() {
        createModule("test");
        createModule("test2");

        homePage.getManageModulesButton().doClick();
        manageModules.getDeleteModuleButton().doClick();
        deleteModule.getDeleteButton().doClick();

        assertEquals(PageNames.DELETE_MODULE, manager.getCurrentCard());
    }

    public void createModule(String moduleName) {
        // Creating a new module called test
        addModule.setModuleNameInput(moduleName);
        addModule.getEnterButton().doClick();
    }
}
