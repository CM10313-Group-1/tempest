package tempest.GUI;

import org.junit.Test;
import tempest.GUI.components.ModuleDropDown;
import tempest.Module;
import tempest.State;
import tempest.Supervisor;

import static org.junit.Assert.*;

public class ManageModulesPageTest{
    Supervisor supervisor = new Supervisor();
    State state = new State();
    GUIManager manager = new GUIManager(state, supervisor);

    HomePage homePage = manager.getHomePage();
    ManageModulesPage manageModulesPage = manager.getManageModulesPage();
    AddModulePage modulePage = manager.getModulePage();

    ModuleDropDown moduleDropDown = new ModuleDropDown();

    Module[] modules = state.getModules();

    @Test
    public void addModuleButton(){
        homePage.getManageModulesButton().doClick();
        manageModulesPage.getAddModuleButton().doClick();

        assertEquals(manager.getCurrentCard(), "addModule");
    }

    public void clearModules(){
        for(Module module: modules){
            moduleDropDown.removeModule(module.getName());
        }
    }

    public void createModule(String moduleName){
        // Creating a new module called test
        modulePage.setModuleNameInput(moduleName);
        modulePage.getEnterButton().doClick();
    }

    @Test
    public void noModulesDeleteModuleButton(){
        clearModules();

        homePage.getManageModulesButton().doClick();
        manageModulesPage.getDeleteModuleButton().doClick();

        assertEquals(manager.getCurrentCard(), "manageModules");
    }

    @Test
    public void onePlusModulesDeleteButton(){
        createModule("test");

        homePage.getManageModulesButton().doClick();
        manageModulesPage.getDeleteModuleButton().doClick();

        assertEquals(manager.getCurrentCard(), "deleteModule");
    }

    @Test
    public void cancelButton(){
        homePage.getManageModulesButton().doClick();
        manageModulesPage.getCancelButton().doClick();

        assertEquals(manager.getCurrentCard(), "home");
    }
}
