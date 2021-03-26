package tempest.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import tempest.State;
import tempest.Supervisor;
import tempest.ui.components.ModuleDropDown;
import tempest.ui.pages.AddModulePage;
import tempest.ui.pages.AddSessionPage;
import tempest.ui.pages.ChartViewPage;
import tempest.ui.pages.HomePage;
import tempest.ui.pages.Page;

public class GUIManager extends JFrame {
    private static final long serialVersionUID = -4398929329322784483L;
    /** The view manager for the GUI. Handles which page should be visible. */
    private static ViewManager<Page> mv;
    /** All pages that can be displayed by the view manager. */
    private final Page[] pages;
    private final Supervisor supervisor;

    public GUIManager(State state, Supervisor supervisor) {
        super();
        this.supervisor = supervisor;
        new ModuleDropDown(state);
        this.pages = new Page[] { new HomePage(this), new AddModulePage(state, this), new AddSessionPage(state, this),
                new ChartViewPage(state, this)
                // All new pages should be added here.
        };
        start();
    }

    /**
     * Sets up and runs the GUI.
     */
    private void start() {
        // Creating the module drop down
        mv = new ViewManager<Page>(pages, pages[0]);
        this.getContentPane().add(mv);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                supervisor.onClose();
            }
        });

        // Frame Settings
        this.setSize(500, 150);
        this.setTitle("Tempest");
        this.setLocationRelativeTo(null); // Centering GUI
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Swaps to the entered card name
     *
     * Used to move back up the tree of cards/pages along the path taken on the way
     * down
     *
     * @param cardName Name of the card to swap to
     */
    public void swapCard(String cardName) {
        mv.changeView(cardName);
    }

    /**
     * Switches to the previous card
     */
    public void swapToPrevCard() {
        mv.changeToPrevious();
    }

    /**
     * Returns an instance of a page in the cardLayout
     *
     * @param classObject A class extending page (e.g. HomePage.class)
     * @return Page - The instance of the required class
     */
    public Page getPage(Class<? extends Page> classObject) {
        return mv.getView(classObject);
    }

    /**
     * Returns the name of a page in the cardLayout
     *
     * @param classObject A class extending page (e.g. HomePage.class)
     * @return String - The name of the page class
     */
    public String getPageName(Class<? extends Page> classObject) {
        return mv.getViewName(classObject);
    }

    public String getCurrentCard() {
        return mv.getVisible();
    }
}
