package tempest.ui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import tempest.Module;
import tempest.State;
import tempest.Supervisor;
import tempest.ui.components.ModuleDropDown;
import tempest.ui.pages.AddModulePage;
import tempest.ui.pages.AddSessionPage;
import tempest.ui.pages.ChartViewPage;
import tempest.ui.pages.DeleteModulePage;
import tempest.ui.pages.DeleteSessionPage;
import tempest.ui.pages.HomePage;
import tempest.ui.pages.ManageModulesPage;
import tempest.ui.pages.ManageSessionsPage;
import tempest.ui.pages.Page;
import tempest.ui.pages.PageNames;

public class GUIManager extends JFrame {
    private static final long serialVersionUID = -4398929329322784483L;

    /** The view manager for the GUI. Handles which page should be visible. */
    private static ViewManager<Page> vm;

    /** All pages that can be displayed by the view manager. */
    private final Page[] pages;
    private final Supervisor supervisor;

    private final State state;

    private final ManageSessionsPage manageSessions;
    private final ManageModulesPage manageModules;
    private final DeleteSessionPage deleteSession;

    private LayoutManager layout;

    public GUIManager(State state, Supervisor supervisor) {
        this.state = state;
        this.supervisor = supervisor;

        new ModuleDropDown(state); // Creating the DefaultComboBoxModel

        this.pages = new Page[] {
            new HomePage(this),
            manageModules = new ManageModulesPage(this),
            new AddModulePage(state, this),
            new DeleteModulePage(state, this),
            manageSessions = new ManageSessionsPage(this),
            new AddSessionPage(state, this),
            deleteSession = new DeleteSessionPage(state, this),
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

        //this.setPreferredSize(new Dimension(500, 150));

        //this.setSize(500, 150);
        this.pack();

        this.setTitle("Tempest");
        this.setLocationRelativeTo(null); // Centering GUI
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        layout = vm.getLayout();
    }

    /**
     * Responsible for calling certain page's methods when they are visible
     *
     * @param name Page's name
     */
    private void updatePages(String name) {
        Module[] modules = state.getModules();

        switch (name) {
        case PageNames.MANAGE_SESSIONS:
            manageSessions.setDeleteButtonActivity(modules);
            manageSessions.setAddButtonActivity(modules);
            break;
        case PageNames.MANAGE_MODULES:
            manageModules.setButtonActivity(modules);
            break;
        case PageNames.DELETE_SESSION:
            deleteSession.updateTable();
            break;
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
        updatePages(vm.getVisible());
        layout.preferredLayoutSize(this);
        this.pack();
        this.setLocationRelativeTo(null); // Centering GUI
    }

    /**
     * Switches to the previous card
     */
    public void swapToPrevCard() {
        vm.changeToPrevious();
        updatePages(vm.getVisible());
        layout.preferredLayoutSize(this);
        this.pack();
        this.setLocationRelativeTo(null); // Centering GUI
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

    /**
     * Used by tests to close the GUI - saving modules and sessions
     */
    public void closeGUI() {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    public void updatePanel() {
        layout.preferredLayoutSize(this);
        this.pack();
        this.setLocationRelativeTo(null); // Centering GUI
    }
}
