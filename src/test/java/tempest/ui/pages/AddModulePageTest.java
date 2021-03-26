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
    Supervisor supervisor = new Supervisor();
    GUIManager manager = new GUIManager(state, supervisor);

    HomePage homePage = (HomePage) manager.getPage(HomePage.class);
    AddModulePage modulePage = (AddModulePage) manager.getPage(AddModulePage.class);

    ActionButtonPanel actionButtonPanel = modulePage.getActionButtons();

    @Before
    public void turnOffErrorMessages() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessagesShown(false);
    }

    @Test
    public void moduleCancelButton() {
        homePage.getAddModuleButton().doClick();
        actionButtonPanel.getCancelButtonInstance().doClick();

        assertEquals(manager.getCurrentCard(), homePage.getName());
    }

    public void createModule(String moduleName) {
        homePage.getAddModuleButton().doClick();
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
    public void duplicateModules() {
        int prevModuleNum = state.getModules().length;

        createModule("test");

        actionButtonPanel.getCancelButtonInstance().doClick();

        createModule("test");

        assertEquals(prevModuleNum + 1, state.getModules().length);
    }
}
