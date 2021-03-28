package tempest.ui.pages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tempest.Module;
import tempest.State;
import tempest.Supervisor;
import tempest.ui.GUIManager;
import tempest.ui.components.ModuleDropDown;

public class ManageModulesPageTest {
    Supervisor supervisor = Supervisor.getInstance();
    State state = new State();
    GUIManager manager = new GUIManager(state, supervisor);

    HomePage homePage = (HomePage) manager.getPage(HomePage.class);
    ManageModulesPage manageModulesPage = (ManageModulesPage) manager.getPage(ManageModulesPage.class);
    AddModulePage modulePage = (AddModulePage) manager.getPage(AddModulePage.class);
    DeleteModulePage deleteModulePage = (DeleteModulePage) manager.getPage(DeleteModulePage.class);

    ModuleDropDown moduleDropDown = new ModuleDropDown();

    Module[] modules = state.getModules();

    @Test
    public void addModuleButton() {
        homePage.getManageModulesButton().doClick();
        manageModulesPage.getAddModuleButton().doClick();

        assertEquals(modulePage.getName(), manager.getCurrentCard());
    }

    public void clearModules() {
        for (Module module : modules) {
            moduleDropDown.removeModule(module.getName());
        }
    }

    public void createModule(String moduleName) {
        // Creating a new module called test
        modulePage.setModuleNameInput(moduleName);
        modulePage.getEnterButton().doClick();
    }

    @Test
    public void noModulesDeleteModuleButton() {
        clearModules();

        homePage.getManageModulesButton().doClick();
        manageModulesPage.getDeleteModuleButton().doClick();

        assertEquals(manageModulesPage.getName(), manager.getCurrentCard());
    }

    @Test
    public void onePlusModulesDeleteButton() {
        createModule("test");

        homePage.getManageModulesButton().doClick();
        manageModulesPage.getDeleteModuleButton().doClick();

        assertEquals(PageNames.DELETE_MODULE, manager.getCurrentCard());
    }

    @Test
    public void backButton() {
        homePage.getManageModulesButton().doClick();
        manageModulesPage.getBackButton().doClick();

        assertEquals(homePage.getName(), manager.getCurrentCard());
    }

    @Test
    public void buttonCorrectlyDisables(){
        createModule("test");

        homePage.getManageModulesButton().doClick();
        manageModulesPage.getDeleteModuleButton().doClick();
        deleteModulePage.getDeleteButton().doClick();
        manageModulesPage.getDeleteModuleButton().doClick();

        assertEquals(manageModulesPage.getName(), manager.getCurrentCard());
    }
}
