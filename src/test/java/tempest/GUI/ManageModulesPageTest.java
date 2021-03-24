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

    HomePage homePage = (HomePage) manager.getPage(HomePage.class);
    ManageModulesPage manageModulesPage = (ManageModulesPage) manager.getPage(ManageModulesPage.class);
    AddModulePage modulePage = (AddModulePage) manager.getPage(AddModulePage.class);

    ModuleDropDown moduleDropDown = new ModuleDropDown();

    Module[] modules = state.getModules();

    @Test
    public void addModuleButton(){
        homePage.getManageModulesButton().doClick();
        manageModulesPage.getAddModuleButton().doClick();

        assertEquals(manager.getCurrentCard(), manager.getPageName(AddModulePage.class));
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

        assertEquals(manager.getCurrentCard(), manager.getPageName(ManageModulesPage.class));
    }

    @Test
    public void onePlusModulesDeleteButton(){
        createModule("test");

        homePage.getManageModulesButton().doClick();
        manageModulesPage.getDeleteModuleButton().doClick();

        assertEquals(manager.getCurrentCard(), manager.getPageName(DeleteModulePage.class));
    }

    @Test
    public void cancelButton(){
        homePage.getManageModulesButton().doClick();
        manageModulesPage.getCancelButton().doClick();

        assertEquals(manager.getCurrentCard(), manager.getPageName(HomePage.class));
    }
}
