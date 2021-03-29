package tempest.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import tempest.State;
import tempest.Supervisor;
import tempest.ui.components.ModuleDropDown;
import tempest.ui.pages.*;

public class GUIManager extends JFrame {
    private static final long serialVersionUID = -4398929329322784483L;

    /** The view manager for the GUI. Handles which page should be visible. */
    private static ViewManager<Page> vm;

    /** All pages that can be displayed by the view manager. */
    private final Page[] pages;
    private final Supervisor supervisor;

    public GUIManager(State state, Supervisor supervisor) {
        super();
        this.supervisor = supervisor;

        new ModuleDropDown(state); // Creating the DefaultComboBoxModel

        this.pages = new Page[]{
                new HomePage(this),
                new ManageModulesPage(this),
                new AddModulePage(state, this),
                new DeleteModulePage(state, this),
                new ManageSessionsPage(this),
                new AddSessionPage(state, this),
                new DeleteSessionPage(state, this),
                new ChartViewPage(state, this),

                // All new pages should be added here.
        };
        start();
    }

    /**
     * Sets up and runs the GUI.
     */
    private void start() {
        vm = new ViewManager<>(pages, pages[0]);
        this.getContentPane().add(vm);

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
     * Responsible for calling certain page's methods when they are visible
     *
     * @param name Page's name
     */
    public void checkPage(String name) {
        System.out.println(name);

        if (name.equals(PageNames.MANAGE_MODULES)) {
            ManageModulesPage mmp = (ManageModulesPage) getPage(ManageModulesPage.class);
            mmp.update();

        } else if (name.equals(PageNames.MANAGE_SESSIONS)) {
            //I'll put my code here when i push my branch
        }
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
        vm.changeView(cardName);
        checkPage(vm.getVisible());
    }

    /**
     * Switches to the previous card
     */
    public void swapToPrevCard() {
        vm.changeToPrevious();
        checkPage(vm.getVisible());
    }

    /**
     * Returns an instance of a page in the cardLayout
     *
     * @param classObject A class extending page (e.g. HomePage.class)
     * @return Page - The instance of the required class
     */
    public Page getPage(Class<? extends Page> classObject) {
        return vm.getView(classObject);
    }

    public String getCurrentCard() {
        return vm.getVisible();
    }
}
