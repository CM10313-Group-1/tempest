package tempest.GUI;

import org.junit.Before;
import org.junit.Test;
import tempest.Module;
import tempest.State;
import tempest.Supervisor;

import static org.junit.Assert.assertEquals;

public class AddModulePageTest{
    State state = new State();
    GUIManager manager = new GUIManager(state, new Supervisor());

    HomePage homePage = manager.getHomePage();
    AddModulePage modulePage = manager.getModulePage();

    GUIComponents moduleComponents = modulePage.getComponents();

    @Before
    public void turnOffErrorMessages(){
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessagesShown(false);
    }

    @Test
    public void moduleCancelButton() {
        homePage.getAddModuleButton().doClick();
        moduleComponents.getCancelButton().doClick();

        assertEquals(manager.getCurrentCard(), "home");
    }

    public Module createModule(String moduleName){
        Module testModule = null;

        // Creating a new module called test2
        homePage.getAddModuleButton().doClick();
        modulePage.setModuleNameInput(moduleName);
        modulePage.getEnterButton().doClick();

        // Getting the created module
        for (Module m : state.getModules()) {
            if (m.getName().equals(moduleName)) {
                testModule = m;
                break;
            }
        }

        return testModule;
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
