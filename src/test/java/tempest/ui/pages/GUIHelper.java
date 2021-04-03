package tempest.ui.pages;

import tempest.Module;
import tempest.State;
import tempest.ui.GUIManager;

public class GUIHelper {

    private final AddModulePage addModule;
    private final AddSessionPage addSession;
    private final State state;

    public GUIHelper(GUIManager manager, State state) {
        this.state = state;

        addModule = (AddModulePage) manager.getPage(AddModulePage.class);
        addSession = (AddSessionPage) manager.getPage(AddSessionPage.class);
    }

    public Module createModule(String moduleName) {
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

    public void createSession(String hours, String mins, Module module) {
        addSession.setDropDown(module.getName());

        addSession.setHours(hours);
        addSession.setMins(mins);
        addSession.getEnterButton().doClick();
    }

    public int[] createSessionReturn(String hours, String mins, Module testModule) {
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
