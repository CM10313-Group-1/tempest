package tempest.helpers;

import tempest.Module;
import tempest.State;
import tempest.Supervisor;
import tempest.ui.ErrorMessage;
import tempest.ui.GUIManager;
import tempest.ui.pages.*;

public class GUIHelper {

    protected State state = new State();
    protected GUIManager manager = new GUIManager(state, Supervisor.getInstance());

    protected HomePage homePage = (HomePage) manager.getPage(HomePage.class);

    protected GoalEntryPage goalEntry = (GoalEntryPage) manager.getPage(GoalEntryPage.class);

    protected ManageSessionsPage manageSessions = (ManageSessionsPage) manager.getPage(ManageSessionsPage.class);
    protected ManageModulesPage manageModules = (ManageModulesPage) manager.getPage(ManageModulesPage.class);

    protected AddSessionPage addSession = (AddSessionPage) manager.getPage(AddSessionPage.class);
    protected AddModulePage addModule = (AddModulePage) manager.getPage(AddModulePage.class);

    protected DeleteSessionPage deleteSession = (DeleteSessionPage) manager.getPage(DeleteSessionPage.class);
    protected DeleteModulePage deleteModule = (DeleteModulePage) manager.getPage(DeleteModulePage.class);

    protected ChartViewPage chartView = (ChartViewPage) manager.getPage(ChartViewPage.class);

    protected DataProtectionPage dataProtection = (DataProtectionPage) manager.getPage(DataProtectionPage.class);

    protected GUIHelper() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessagesShown(false);
    }

    protected Module createModule(String moduleName) {
        Module testModule = null;

        // Creating module
        addModule.setModuleName(moduleName);
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

    protected Module createModuleChangePage(String moduleName) {
        // Creating a module to enable the manage sessions button
        homePage.getManageModulesButton().doClick();
        Module testModule = createModule(moduleName);
        manageModules.getBackButton().doClick();

        return testModule;
    }

    protected void createSession(String hours, String mins, Module module) {
        addSession.setDropDown(module.getName());

        addSession.setHours(hours);
        addSession.setMins(mins);
        addSession.getEnterButton().doClick();
    }

    protected int[] createSessionReturn(String hours, String mins, Module testModule) {
        int prevSessionsLen = testModule.getStudySessions().length;

        // Selecting the  module in the drop down
        addSession.setDropDown(testModule.getName());

        // Adding a study session
        addSession.setHours(hours);
        addSession.setMins(mins);
        addSession.getEnterButton().doClick();

        // Returns values to test
        return new int[] { prevSessionsLen, testModule.getStudySessions().length };
    }
}
