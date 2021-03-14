package tempest;

import org.junit.Test;

import static org.junit.Assert.*;

public class ModuleViewTest {

    @Test
    public void testAddingAModule() {
        ModuleView gui = new ModuleView();

        assertEquals(gui.getDropSize(), 0);

        gui.getAddModuleButton().doClick();
        gui.setModuleInput("test");
        gui.getEnterButton().doClick();

        assertEquals(gui.getDropSize(), 1);
    }

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
}