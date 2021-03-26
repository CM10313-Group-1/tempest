package tempest.GUI;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tempest.Module;
import tempest.State;
import tempest.Supervisor;
import tempest.GUI.components.ModuleDropDown;

public class ManageModulesPageTest {
    State state = new State();
    GUIManager manager = new GUIManager(state, Supervisor.getInstance());

    HomePage homePage = (HomePage) manager.getPage(HomePage.class);
    ManageModulesPage manageModulesPage = (ManageModulesPage) manager.getPage(ManageModulesPage.class);
    AddModulePage addModulePage = (AddModulePage) manager.getPage(AddModulePage.class);

    ModuleDropDown moduleDropDown = new ModuleDropDown();

    Module[] modules = state.getModules();

    @Test
    public void addModuleButton() {
        //FIXME
        homePage.getManageModulesButton().doClick();
        manageModulesPage.getAddModuleButton().doClick();

        assertEquals(addModulePage.getName(), manager.getCurrentCard());
    }

    public void clearModules() {
        for (Module module : modules) {
            moduleDropDown.removeModule(module.getName());
        }
    }

    public void createModule(String moduleName) {
        // Creating a new module called test
        addModulePage.setModuleNameInput(moduleName);
        addModulePage.getEnterButton().doClick();
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

        assertEquals(manager.getPageName(DeleteModulePage.class), manager.getCurrentCard());
    }

    @Test
    public void backButton() {
        homePage.getManageModulesButton().doClick();
        manageModulesPage.getBackButton().doClick();

        assertEquals(homePage.getName(), manager.getCurrentCard());
    }
}
