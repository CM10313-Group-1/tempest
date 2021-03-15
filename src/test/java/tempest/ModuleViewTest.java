package tempest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ModuleViewTest {
    State state = new State();
    ModuleView gui = new ModuleView(state);

    /**
     * Needed to stop a headless exception by Maven
     *
     * Got this code from: https://www.baeldung.com/java-headless-mode
     */
    @Before
    public void setUpHeadlessMode() {
        System.setProperty("java.awt.headless", "true");
    }

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

        // Creating a new module
        gui.getAddModuleButton().doClick();
        gui.setModuleNameInput("test");
        gui.getEnterButton().doClick();

        // Seeing if the modules list has been increased
        assertEquals(state.getModules().length, 1);
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

        // Adding a study session to test
        gui.getAddSessionButton().doClick();
        gui.setHours("1");
        gui.setMins("16");
        gui.getEnterButton().doClick();

        // Checking if this study session has been added
        assert testModule != null;
        assertEquals(testModule.getStudySessions().length, 1);
    }
}