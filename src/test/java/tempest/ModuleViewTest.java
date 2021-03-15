package tempest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ModuleViewTest {
    State state = new State();
    ModuleView gui = new ModuleView(state);

    @Test
    public void testAddModuleButton() {
        gui.getAddModuleButton().doClick();
        assertEquals(gui.getCard(), 2);
    }

    @Test
    public void testAddSessionButton() {
        gui.getAddSessionButton().doClick();
        assertEquals(gui.getCard(), 3);
    }

    @Test
    public void testCancelButton() {
        gui.getAddSessionButton().doClick();

        gui.getCancelButton().doClick();
        assertEquals(gui.getCard(), 1);
    }

    @Test
    public void testAddingAModule() {
        int previousModules = state.getModules().length;

        // Creating a new module
        gui.getAddModuleButton().doClick();
        gui.setModuleNameInput("test");
        gui.getEnterButton().doClick();

        // Seeing if the modules list has been increased
        assertEquals(previousModules + 1, state.getModules().length);
    }

    @Test
    public void testAddingASession() {

        // Adding a module called test
        gui.getAddModuleButton().doClick();
        gui.setModuleNameInput("testing session");
        gui.getEnterButton().doClick();

        // Getting the created module
        Module testModule = null;

        for (Module m : state.getModules()) {
            if (m.getName().equals("test")) {
                testModule = m;
                break;
            }
        }
        int previousSessions = testModule.getStudySessions().length;
        // Adding a study session to test
        gui.getAddSessionButton().doClick();
        gui.setHours("1");
        gui.setMins("16");
        gui.getEnterButton().doClick();

        // Checking if this study session has been added
        assert testModule != null;
        assertEquals(previousSessions + 1, testModule.getStudySessions().length);
    }
}