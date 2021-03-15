package tempest;

import org.junit.Test;

import java.time.Duration;
import java.util.Date;

import static org.junit.Assert.*;

public class ModuleViewTest {
    // State state = State.getInstance(); // -> If state is a singleton
    State state = new State(); // -> If modules ArrayList is static

    @Test
    public void testAddModuleButton() {
        ModuleView gui = new ModuleView();

        gui.getAddModuleButton().doClick();
        assertEquals(gui.getCard(), 2);
    }

    @Test
    public void testAddSessionButton() {
        ModuleView gui = new ModuleView();

        gui.getAddSessionButton().doClick();
        assertEquals(gui.getCard(), 3);
    }

    @Test
    public void testCancelButton() {
        ModuleView gui = new ModuleView();

        gui.getAddSessionButton().doClick();

        gui.getCancelButton().doClick();
        assertEquals(gui.getCard(), 1);
    }


    @Test
    public void testAddingAModule() {       // Needs modules arrayList to be static
        ModuleView gui = new ModuleView();

        // Creating a new module
        gui.getAddModuleButton().doClick();
        gui.setModuleNameInput("test");
        gui.getEnterButton().doClick();

        // Seeing if the modules list has been increased
        assertEquals(state.getModules().length, 1);
    }

    @Test
    public void testAddingASession() {      // Needs modules arrayList to be static
        ModuleView gui = new ModuleView();

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

        Duration time = Duration.ofMinutes(116);

        // **** Doesn't work as Date entered by Module has different min/seconds to test ****

        // Checking if this study session has been added
        assertEquals(testModule.getStudySessions()[0], new StudySession(new Date(), time));
    }
}