package tempest.GUI;

import org.junit.Before;
import org.junit.Test;
import tempest.GUI.components.ActionButtonPanel;
import tempest.State;
import tempest.Supervisor;

import static org.junit.Assert.assertEquals;

public class AddModulePageTest{
    State state = new State();
    GUIManager manager = new GUIManager(state, Supervisor.getInstance());

    HomePage homePage = manager.getHomePage();
    AddModulePage modulePage = manager.getModulePage();

    ActionButtonPanel actionButtonPanel = modulePage.getComponents();

    @Before
    public void turnOffErrorMessages(){
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessagesShown(false);
    }

    @Test
    public void moduleCancelButton() {
        homePage.getAddModuleButton().doClick();
        actionButtonPanel.getCancelButtonInstance().doClick();

        assertEquals(manager.getCurrentCard(), "home");
    }

    public void createModule(String moduleName){
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
    public void nullModule(){
        int prevModuleNum = state.getModules().length;
        createModule("");

        assertEquals(prevModuleNum, state.getModules().length);
    }

    @Test
    public void duplicateModules(){
        int prevModuleNum = state.getModules().length;

        createModule("test");
        createModule("test");

        assertEquals(prevModuleNum + 1, state.getModules().length);
    }
}
