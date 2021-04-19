package tempest.ui;

import java.awt.LayoutManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import tempest.Module;
import tempest.State;
import tempest.Supervisor;
import tempest.ui.components.ModuleDropDown;
import tempest.ui.pages.*;
import tempest.ui.pages.charts.*;

public class GUIManager extends JFrame {
    private static final long serialVersionUID = -4398929329322784483L;

    /** The view manager for the GUI. Handles which page should be visible. */
    private static PageManager pm;

    /** All pages that can be displayed by the view manager. */
    private final Page[] pages;
    private final Chart[] charts;
    private final Supervisor supervisor;

    private final State state;

    private final HomePage home;
    private final ManageSessionsPage manageSessions;
    private final ManageModulesPage manageModules;
    private final DeleteSessionPage deleteSession;
    private final ChartViewPage chartView;

    private LayoutManager layout;

    public GUIManager(State state, Supervisor supervisor) {
        this.state = state;
        this.supervisor = supervisor;

        new ModuleDropDown(state); // Creating the DefaultComboBoxModel

        StackedBarChart stackedBarChart;
        LineChart lineChart;
        PieChart pieChart;
        TimeBarChart timeBarChart;

        this.pages = new Page[] {
                home = new HomePage(state,this),
                manageModules = new ManageModulesPage(this),
                new AddModulePage(state, this),
                new DeleteModulePage(state, this),
                manageSessions = new ManageSessionsPage(this),
                new AddSessionPage(state, this),
                deleteSession = new DeleteSessionPage(state, this),
                chartView = new ChartViewPage(this),
                stackedBarChart = new StackedBarChart(state, this),
                lineChart = new LineChart(state, this),
                pieChart = new PieChart(state, this),
                timeBarChart = new TimeBarChart(state, this),
                new GoalEntryPage(state, this, home),
                new DataProtectionPage(this),

                // All new pages should be added here.
        };

        this.charts = new Chart[] {stackedBarChart, lineChart, pieChart, timeBarChart};

        start();
    }

    /**
     * Sets up and runs the GUI.
     */
    private void start() {
        pm = new PageManager(pages, pages[0]);
        this.getContentPane().add(pm);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                supervisor.onClose();
            }
        });

        // Frame Settings
        this.pack();
        this.setTitle("Tempest");
        this.setLocationRelativeTo(null); // Centering GUI
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        layout = pm.getLayout();
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
                manageSessions.setButtonActivity(modules);
                break;
            case PageNames.MANAGE_MODULES:
                manageModules.setButtonActivity(modules);
                break;
            case PageNames.DELETE_SESSION:
                deleteSession.updateTable();
                break;
            case PageNames.HOME:
                home.updatePage(modules);
                break;
            case PageNames.CHART_VIEW:
                chartView.updateCharts(charts, state);
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
        pm.changePage(cardName);
        updatePages(pm.getVisible());

        resizeGUI();
    }

    /**
     * Switches to the previous card
     */
    public void swapToPrevCard() {
        pm.changeToPrevious();
        updatePages(pm.getVisible());

        resizeGUI();
    }

    /**
     * Resizes the frame for the new card
     */
    private void resizeGUI() {
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
        return pm.getPage(classObject);
    }

    public String getCurrentCard() {
        return pm.getVisible();
    }

    /**
     * Used by tests to close the GUI - saving modules and sessions
     */
    public void closeGUI() {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}
