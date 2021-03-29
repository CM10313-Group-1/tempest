package tempest.ui.pages;

import tempest.Module;
import tempest.Supervisor;

public class GUIHelper {

    public GUIHelper() {
        Supervisor supervisor = Supervisor.getInstance();
        supervisor.start();
    }

    public void getState() {

    }

    public void createModule() {
//        manageModulesPage.getAddModuleButton().doClick(); // Needed for the back button in the duplicate test to work
//        modulePage.setModuleNameInput(moduleName);
//        modulePage.getEnterButton().doClick();
//
//        Module testModule = null;
//
//        // Creating a new module
//
//        //TODO: Test if need to move to the modules page
//        homePage.getManageModulesButton().doClick();
//        manageModules.getAddModuleButton().doClick();
//        modulePage.setModuleNameInput(moduleName);
//        modulePage.getEnterButton().doClick();
//
//        // Getting the created module
//        for (Module m : state.getModules()) {
//            if (moduleName.equals(m.getName())) {
//                testModule = m;
//                break;
//            }
//        }
//
//        return testModule;
    }

    public void createSession() {

    }

}
