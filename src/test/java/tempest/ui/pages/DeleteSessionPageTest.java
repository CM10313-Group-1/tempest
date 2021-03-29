package tempest.ui.pages;

import org.junit.Before;
import org.junit.Test;
import tempest.Module;
import tempest.State;
import tempest.Supervisor;
import tempest.ui.ErrorMessage;
import tempest.ui.GUIManager;

import static org.junit.Assert.assertEquals;

public class DeleteSessionPageTest {

    State state = new State();
    GUIManager manager = new GUIManager(state, Supervisor.getInstance());

    HomePage homePage = (HomePage) manager.getPage(HomePage.class);
    ManageModulesPage manageModules = (ManageModulesPage) manager.getPage(ManageModulesPage.class);
    ManageSessionsPage manageSessionsPage = (ManageSessionsPage) manager.getPage(ManageSessionsPage.class);
    DeleteSessionPage deleteSession = (DeleteSessionPage) manager.getPage(DeleteSessionPage.class);

    AddModulePage addModule = (AddModulePage) manager.getPage(AddModulePage.class);

    // Test changing the drop fills table with correct sessions???

    // A newly added sessions appears in the table???

    @Before
    public void turnOffErrorMessages() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessagesShown(false);
    }

    @Test
    public void backButton() {
        homePage.getManageSessionsButton().doClick();
        manageSessionsPage.getDelSessionsButton().doClick();
        deleteSession.getBackButton().doClick();

        assertEquals(PageNames.MANAGE_SESSIONS, manager.getCurrentCard());
    }

    @Test
    public void deleteSession() {
        //Create module

        //Create a session

        //Create another session

        //Select module

        //Delete one session

        //assert session length of module = -1
    }

    @Test
    public void deleteLastSession() {
        //Create module

        //Create a session

        //Select module

        //Delete session

        //assert session length of module = -1 (or 0)

        //assert current page == PageNames.MANAGE_SESSIONS
    }

    @Test
    public void changeTableDisplay() {
        //Create module

        //Add a session

        //Create module

        //Add session
        //Add session

        //assert table rows w/ 1st module = 1

        //assert table rows w/ 2nd module = 1
    }

    public Module createModule(String moduleName) {
        Module testModule = null;

        // Creating a new module

        homePage.getManageModulesButton().doClick();
        manageModules.getAddModuleButton().doClick();
        addModule.setModuleNameInput(moduleName);
        addModule.getEnterButton().doClick();

        // Getting the created module
        for (Module m : state.getModules()) {
            if (moduleName.equals(m.getName())) {
                testModule = m;
                break;
            }
        }

        return testModule;
    }

    public void createSession() {

    }
}
