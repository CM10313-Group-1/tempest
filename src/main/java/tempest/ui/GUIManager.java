//package tempest.ui;
//
//import java.awt.CardLayout;
//import java.awt.Component;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.util.ArrayList;
//import java.util.Stack;
//
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//
//import tempest.ui.components.ModuleDropDown;
//import tempest.Module;
//import tempest.State;
//import tempest.Supervisor;
//import tempest.ui.pages.*;
//
//public class GUIManager {
//    private static JPanel cardPanel;
//    private static CardLayout cl;
//
//    private ArrayList<Page> pages;
//
//    private String currentCard;
//    private final Stack<String> cards = new Stack<>();
//
//    private final State state;
//    private final Supervisor supervisor;
//
//    private ManageModulesPage manageModulesPage;
//    private ManageSessionsPage manageSessionsPage;
//    private DeleteSessionPage deleteSessionPage;
//
//    public GUIManager(State state, Supervisor supervisor) {
//        this.state = state;
//        this.supervisor = supervisor;
//
//        new ModuleDropDown(state); // Creating the DefaultComboBoxModel
//
//        start();
//    }
//
//    /**
//     * All new pages should be added to the list here
//     */
//    private void getAllInstances() {
//        pages = new ArrayList<>();
//
//        manageModulesPage = new ManageModulesPage(this);
//        manageSessionsPage = new ManageSessionsPage(this);
//
//        deleteSessionPage = new DeleteSessionPage(state, this);
//
//        pages.add(new HomePage(this));
//        pages.add(manageModulesPage);
//        pages.add(manageSessionsPage);
//        pages.add(new AddModulePage(state, this));
//        pages.add(new AddSessionPage(state, this));
//        pages.add(new DeleteModulePage(state, this));
//        pages.add(deleteSessionPage);
//    }
//
//    /**
//     * Sets up and runs the GUI
//     */
//    private void start() {
//        getAllInstances();
//
//        JFrame frame = new JFrame();
//
//        cardPanel = new JPanel();
//        cl = new CardLayout();
//
//        cardPanel.setLayout(cl);
//
//        // Adding each page's panel to cardPanel
//        for (Page p : pages) {
//            cardPanel.add(p.getPanel(), p.getName());
//        }
//
//        currentCard = "homePage"; // 1st Card
//
//        frame.getContentPane().add(cardPanel);
//
//        frame.addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                supervisor.onClose();
//            }
//        });
//
//        // Frame Settings
//        frame.setSize(500, 150);
//        frame.setTitle("Tempest");
//        frame.setLocationRelativeTo(null); // Centering GUI
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
//    }
//
//    /**
//     * Responsible for actually changing what card is showing
//     *
//     * Should only be called to move down the tree of cards/pages
//     *
//     * @param cardName The name of the card to switch to
//     */
//    private void changeCard(String cardName) {
//        if (currentCard.equals(cardName)) {
//            System.err.println("You are trying to swap to the same card");
//        }
//
//        Module[] modules = state.getModules();
//
//        if (cardName.equals(getPageName(ManageModulesPage.class))) {
//            // Toggling "delete a module" button
//            manageModulesPage.toggleDeleteButton(modules);
//
//        } else if (cardName.equals(getPageName(ManageSessionsPage.class))) {
//            // Toggling "View and delete sessions" button
//            manageSessionsPage.toggleDeleteButton(modules);
//
//        } else if (cardName.equals(getPageName(DeleteSessionPage.class))) {
//            // Updating the table when its page is active
//            deleteSessionPage.updateTable();
//        }
//
//        Component prevPanel = getVisibleCard();
//
//        cl.show(cardPanel, cardName);
//
//        Component currentPanel = getVisibleCard();
//
//        if (prevPanel == currentPanel) {
//            System.err.println("The card/page you are trying to swap to doesn't exist");
//        }
//    }
//
//    /**
//     * Used to find which panel is currently showing
//     *
//     * Method from:
//     * https://stackoverflow.com/questions/6040989/check-if-a-card-with-a-name-is-present-in-a-cardlayout
//     *
//     * @return Component - The currently showing card
//     */
//    private Component getVisibleCard() {
//        for (Component c : cardPanel.getComponents()) {
//            if (c.isVisible()) {
//                return c;
//            }
//        }
//
//        return null;
//    }
//
//    /**
//     * Swaps to the entered card name
//     *
//     * Used to move back up the tree of cards/pages along the path taken on the way
//     * down
//     *
//     * @param cardName Name of the card to swap to
//     */
//    public void swapCard(String cardName) {
//        changeCard(cardName);
//        cards.add(currentCard);
//        currentCard = cardName;
//    }
//
//    /**
//     * Switches to the previous card
//     */
//    public void swapToPrevCard() {
//        String prevCard = cards.pop();
//        changeCard(prevCard);
//        currentCard = prevCard;
//    }
//
//    /**
//     * Returns an instance of a page in the cardLayout
//     *
//     * @param className A class extending page (e.g. HomePage.class)
//     * @return Page - The instance of the required class
//     */
//    public Page getPage(Class<? extends Page> className) {
//        for (Page p : pages) {
//            if (p.getClass() == className) {
//                return p;
//            }
//        }
//
//        System.err.println("Couldn't find a card with an instance of this class");
//        return null;
//    }
//
//    /**
//     * Returns the name of a page in the cardLayout
//     *
//     * @param className A class extending page (e.g. HomePage.class)
//     * @return String - The name of the page class
//     */
//    public String getPageName(Class<? extends Page> className) {
//        for (Page p : pages) {
//            if (p.getClass() == className) {
//                return p.getName();
//            }
//        }
//
//        System.err.println("Couldn't find a card with an instance of this class");
//        return null;
//    }
//
//    public String getCurrentCard() {
//        return currentCard;
//    }
//}

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
        // Creating the module drop down //TODO: ???
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
     * Swaps to the entered card name
     *
     * Used to move back up the tree of cards/pages along the path taken on the way
     * down
     *
     * @param cardName Name of the card to swap to
     */
    public void swapCard(String cardName) {
        if (vm.getVisible().equals(cardName)) {
            System.err.println("You are trying to swap to the same card");
        }

//        Module[] modules = state.getModules();
//
//        if (cardName.equals(getPageName(ManageModulesPage.class))) {
//            // Toggling "delete a module" button
//            manageModulesPage.toggleDeleteButton(modules);
//
//        } else if (cardName.equals(getPageName(ManageSessionsPage.class))) {
//            // Toggling "View and delete sessions" button
//            manageSessionsPage.toggleDeleteButton(modules);
//
//        } else if (cardName.equals(getPageName(DeleteSessionPage.class))) {
//            // Updating the table when its page is active
//            deleteSessionPage.updateTable();
//        }

//        if (cardName.equals("manageModulesPage")) {
//            ManageModulesPage p = (ManageModulesPage) getPage(ManageModulesPage.class);
//            //p.toggleDeleteButton();
//        }

        vm.changeView(cardName);
    }

    /**
     * Switches to the previous card
     */
    public void swapToPrevCard() {
        vm.changeToPrevious();
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

    /**
     * Returns the name of a page in the cardLayout
     *
     * @param classObject A class extending page (e.g. HomePage.class)
     * @return String - The name of the page class
     */
    public String getPageName(Class<? extends Page> classObject) {
        return vm.getViewName(classObject);
    }

    public String getCurrentCard() {
        return vm.getVisible();
    }
}
