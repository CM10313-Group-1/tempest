package tempest.GUI;

import org.junit.Test;
import tempest.State;
import tempest.Supervisor;

import static org.junit.Assert.assertEquals;

public class DeleteModulePageTest {
    State state = new State();
    GUIManager manager = new GUIManager(state, Supervisor.getInstance());

    HomePage homePage = (HomePage) manager.getPage(HomePage.class);
    ManageModulesPage manageModulesPage = (ManageModulesPage) manager.getPage(ManageModulesPage.class);
    DeleteModulePage deleteModulePage = (DeleteModulePage) manager.getPage(DeleteModulePage.class);
    AddModulePage modulePage = (AddModulePage) manager.getPage(AddModulePage.class);

    public void createModule(String moduleName) {
        // Creating a new module called test
        modulePage.setModuleNameInput(moduleName);
        modulePage.getEnterButton().doClick();
    }

    @Test
    public void cancelButton() {
        createModule("test");

        homePage.getManageModulesButton().doClick();
        manageModulesPage.getDeleteModuleButton().doClick();
        deleteModulePage.getBackButton().doClick();

        assertEquals(manager.getCurrentCard(), manager.getPageName(ManageModulesPage.class));
    }

    @Test
    public void deleteModule() {
        createModule("test");

        homePage.getManageModulesButton().doClick();
        manageModulesPage.getDeleteModuleButton().doClick();
        deleteModulePage.getDeleteButton().doClick();

        assertEquals(state.getModules().length, 0);
    }

    @Test
    public void swapToPrevIfNoModules() {
        createModule("test");

        homePage.getManageModulesButton().doClick();
        manageModulesPage.getDeleteModuleButton().doClick();
        deleteModulePage.getDeleteButton().doClick();

        assertEquals(manager.getCurrentCard(), manager.getPageName(ManageModulesPage.class));
    }

    @Test
    public void stayIfMoreModules() {
        createModule("test");
        createModule("test2");

        homePage.getManageModulesButton().doClick();
        manageModulesPage.getDeleteModuleButton().doClick();
        deleteModulePage.getDeleteButton().doClick();

        assertEquals(manager.getCurrentCard(), manager.getPageName(DeleteModulePage.class));
    }
}
