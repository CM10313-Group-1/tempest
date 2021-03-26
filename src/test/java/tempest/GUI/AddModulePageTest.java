package tempest.GUI;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import tempest.State;
import tempest.Supervisor;
import tempest.GUI.components.ActionButtonPanel;

public class AddModulePageTest {
    State state = new State();
    GUIManager manager = new GUIManager(state, Supervisor.getInstance());

    HomePage homePage = (HomePage) manager.getPage(HomePage.class);
    AddModulePage modulePage = (AddModulePage) manager.getPage(AddModulePage.class);
    ManageModulesPage manageModulesPage = (ManageModulesPage) manager.getPage(ManageModulesPage.class);

    ActionButtonPanel actionButtonPanel = modulePage.getComponents();

    @Before
    public void turnOffErrorMessages() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessagesShown(false);
    }

    @Test
    public void moduleBackButton() {
        homePage.getManageModulesButton().doClick();
        manageModulesPage.getAddModuleButton().doClick();
        actionButtonPanel.getBackButtonInstance().doClick();

        assertEquals(manageModulesPage.getName(), manager.getCurrentCard());
    }

    public void createModule(String moduleName) {
        manageModulesPage.getAddModuleButton().doClick(); // Needed for the back button in the duplicate test to work
        modulePage.setModuleNameInput(moduleName);
        modulePage.getEnterButton().doClick();
    }

    @Test
    public void validModule() {
        int prevModuleNum = state.getModules().length;
        createModule("test");

        assertEquals(prevModuleNum + 1, state.getModules().length);
    }

    @Test
    public void nullModule() {
        int prevModuleNum = state.getModules().length;
        createModule("");

        assertEquals(prevModuleNum, state.getModules().length);
    }

    @Test
    public void spaceModule() {
        int prevModuleNum = state.getModules().length;
        createModule(" ");

        assertEquals(prevModuleNum, state.getModules().length);
    }

    @Test
    public void duplicateModules() {
        int prevModuleNum = state.getModules().length;

        createModule("test");

        actionButtonPanel.getBackButtonInstance().doClick();

        createModule("test");

        assertEquals(prevModuleNum + 1, state.getModules().length);
    }
}
