package tempest.ui.pages;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import tempest.State;
import tempest.Supervisor;
import tempest.ui.ErrorMessage;
import tempest.ui.GUIManager;
import tempest.ui.components.ActionButtonPanel;

public class AddModulePageTest {
    State state = new State();
    GUIManager manager = new GUIManager(state, Supervisor.getInstance());

    HomePage homePage = (HomePage) manager.getPage(HomePage.class);
    AddModulePage addModule = (AddModulePage) manager.getPage(AddModulePage.class);
    ManageModulesPage manageModules = (ManageModulesPage) manager.getPage(ManageModulesPage.class);

    ActionButtonPanel actionButtonPanel = addModule.getActionButtons();

    @Before
    public void turnOffErrorMessages() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessagesShown(false);
    }

    @Test
    public void backButton() {
        homePage.getManageModulesButton().doClick();
        manageModules.getAddModuleButton().doClick();

        actionButtonPanel.getBackButtonInstance().doClick();

        assertEquals(PageNames.MANAGE_MODULES, manager.getCurrentCard());
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

    public void createModule(String moduleName) {
        manageModules.getAddModuleButton().doClick();
        addModule.setModuleNameInput(moduleName);
        addModule.getEnterButton().doClick();
    }
}
