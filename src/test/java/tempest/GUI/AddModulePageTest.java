package tempest.GUI;

import org.junit.Before;
import org.junit.Test;
import tempest.GUI.components.ActionButtonPanel;
import tempest.State;
import tempest.Supervisor;

import static org.junit.Assert.assertEquals;

public class AddModulePageTest{
    State state = new State();
    GUIManager manager = new GUIManager(state, new Supervisor());

    HomePage homePage = (HomePage) manager.getPage("homePage");
    AddModulePage modulePage = (AddModulePage) manager.getPage("addModulePage");

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

        assertEquals(manager.getCurrentCard(), homePage.getName());
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
        //TODO: Fix
        int prevModuleNum = state.getModules().length;

        createModule("test");
        createModule("test");

        assertEquals(prevModuleNum + 1, state.getModules().length);
    }
}
