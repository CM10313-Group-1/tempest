package tempest.GUI;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tempest.State;
import tempest.Supervisor;

public class DeleteModulePageTest {
    State state = new State();
    GUIManager manager = new GUIManager(state, Supervisor.getInstance());

    HomePage homePage = (HomePage) manager.getPage(HomePage.class);
    ManageModulesPage manageModulesPage = (ManageModulesPage) manager.getPage(ManageModulesPage.class);
    DeleteModulePage deleteModulePage = (DeleteModulePage) manager.getPage(DeleteModulePage.class);
    AddModulePage addModulePage = (AddModulePage) manager.getPage(AddModulePage.class);

    public void createModule(String moduleName) {
        // Creating a new module called test
        addModulePage.setModuleNameInput(moduleName);
        addModulePage.getEnterButton().doClick();
    }

    @Test
    public void backButton() {
        createModule("test");

        homePage.getManageModulesButton().doClick();
        manageModulesPage.getDeleteModuleButton().doClick();
        deleteModulePage.getBackButton().doClick();

        assertEquals(manageModulesPage.getName(), manager.getCurrentCard());
    }

    @Test
    public void deleteModule() {
        createModule("test");

        homePage.getManageModulesButton().doClick();
        manageModulesPage.getDeleteModuleButton().doClick();
        deleteModulePage.getDeleteButton().doClick();

        assertEquals(0, state.getModules().length);
    }

    @Test
    public void swapToPrevIfNoModules() {
        createModule("test");

        homePage.getManageModulesButton().doClick();
        manageModulesPage.getDeleteModuleButton().doClick();
        deleteModulePage.getDeleteButton().doClick();

        assertEquals(manageModulesPage.getName(), manager.getCurrentCard());
    }

    @Test
    public void stayIfMoreModules() {
        createModule("test");
        createModule("test2");

        homePage.getManageModulesButton().doClick();
        manageModulesPage.getDeleteModuleButton().doClick();
        deleteModulePage.getDeleteButton().doClick();

        assertEquals(deleteModulePage.getName(), manager.getCurrentCard());
    }
}
