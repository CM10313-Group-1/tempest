package tempest.GUI;

import org.junit.Test;
import tempest.Module;
import tempest.State;
import tempest.Supervisor;

import static org.junit.Assert.*;

public class GUITest {
    State state = new State();
    GUIManager manager = new GUIManager(state, new Supervisor());

    HomePage homePage = manager.getHomePage();
    AddModulePage modulePage = manager.getModulePage();
    AddSessionPage sessionPage = manager.getSessionPage();

    GUIComponents moduleComponents = modulePage.getComponents();
    GUIComponents sessionComponents = sessionPage.getComponents();

    @Test
    public void addModuleButton() {
        homePage.getAddModuleButton().doClick();

        assertEquals(manager.getCurrentCard(), "addModule");
    }

    @Test
    public void addSessionButton() {
        homePage.getAddSessionButton().doClick();

        assertEquals(manager.getCurrentCard(), "addSession");
    }

    @Test
    public void moduleCancelButton() {
        homePage.getAddModuleButton().doClick();
        moduleComponents.getCancelButton().doClick();

        assertEquals(manager.getCurrentCard(), "home");
    }

    @Test
    public void sessionCancelButton() {
        homePage.getAddSessionButton().doClick();
        sessionComponents.getCancelButton().doClick();

        assertEquals(manager.getCurrentCard(), "home");
    }

    @Test
    public void addingAModule() {
        int prevModulesLen = state.getModules().length;

        // Creating a new module called test1
        homePage.getAddModuleButton().doClick();
        modulePage.setModuleNameInput("test1");
        modulePage.getEnterButton().doClick();

        // Checking if the modules list has been increased
        assertEquals(prevModulesLen + 1, state.getModules().length);
    }

    @Test
    public void addingASession() {
        // Creating a new module called test2
        homePage.getAddModuleButton().doClick();
        modulePage.setModuleNameInput("test2");
        modulePage.getEnterButton().doClick();

        // Getting the created module
        Module testModule = null;

        for (Module m : state.getModules()) {
            if (m.getName().equals("test2")) {
                testModule = m;
                break;
            }
        }

        assert testModule != null;
        int prevSessionsLen = testModule.getStudySessions().length;

        // Adding a study session to test
        homePage.getAddSessionButton().doClick();
        sessionPage.setHours("1");
        sessionPage.setMins("42");
        sessionPage.getEnterButton().doClick();

        // Checking if this study session has been added
        assertEquals(prevSessionsLen + 1, testModule.getStudySessions().length);
    }
}
