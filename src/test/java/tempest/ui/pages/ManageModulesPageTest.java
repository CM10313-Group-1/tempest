package tempest.ui.pages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tempest.State;
import tempest.Supervisor;
import tempest.ui.GUIManager;

public class ManageModulesPageTest {
    State state = new State();
    GUIManager manager = new GUIManager(state, Supervisor.getInstance());

    HomePage homePage = (HomePage) manager.getPage(HomePage.class);
    ManageModulesPage manageModules = (ManageModulesPage) manager.getPage(ManageModulesPage.class);
    AddModulePage addModule = (AddModulePage) manager.getPage(AddModulePage.class);

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

    public void createModule(String moduleName) {
        // Creating a new module called test
        addModule.setModuleNameInput(moduleName);
        addModule.getEnterButton().doClick();
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
