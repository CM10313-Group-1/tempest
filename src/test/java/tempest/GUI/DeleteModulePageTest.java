package tempest.GUI;

import org.junit.Test;
import tempest.State;
import tempest.Supervisor;
import static org.junit.Assert.assertEquals;

public class DeleteModulePageTest{
    Supervisor supervisor = new Supervisor();
    State state = new State();
    GUIManager manager = new GUIManager(state, supervisor);

    HomePage homePage = manager.getHomePage();
    ManageModulesPage manageModulesPage = manager.getManageModulesPage();
    DeleteModulePage deleteModulePage = manager.getDeleteModulePage();
    AddModulePage modulePage = manager.getModulePage();

    public void createModule(String moduleName){
        // Creating a new module called test
        modulePage.setModuleNameInput(moduleName);
        modulePage.getEnterButton().doClick();
    }

    @Test
    public void cancelButton(){
        createModule("test");

        homePage.getManageModulesButton().doClick();
        manageModulesPage.getDeleteModuleButton().doClick();
        deleteModulePage.getCancelButton().doClick();

        assertEquals(manager.getCurrentCard(), "manageModules");
    }

    @Test
    public void deleteModule(){
        createModule("test");

        homePage.getManageModulesButton().doClick();
        manageModulesPage.getDeleteModuleButton().doClick();
        deleteModulePage.getDeleteButton().doClick();

        assertEquals(state.getModules().length, 0);
    }

    @Test
    public void swapToPrevIfNoModules(){
        createModule("test");

        homePage.getManageModulesButton().doClick();
        manageModulesPage.getDeleteModuleButton().doClick();
        deleteModulePage.getDeleteButton().doClick();

        assertEquals(manager.getCurrentCard(), "manageModules");
    }

    @Test
    public void stayIfMoreModules(){
        createModule("test");
        createModule("test2");

        homePage.getManageModulesButton().doClick();
        manageModulesPage.getDeleteModuleButton().doClick();
        deleteModulePage.getDeleteButton().doClick();

        assertEquals(manager.getCurrentCard(), "deleteModule");
    }
}
