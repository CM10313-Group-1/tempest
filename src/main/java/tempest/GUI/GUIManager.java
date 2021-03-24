package tempest.GUI;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JPanel;

import tempest.State;
import tempest.Supervisor;
import tempest.GUI.components.ModuleDropDown;

public class GUIManager {
    private static JPanel cardPanel;
    private static CardLayout cl;

    private ArrayList<Page> pages;

    private String currentCard;
    private final Stack<String> cards = new Stack<>();

    private final State state;
    private final Supervisor supervisor;

    public GUIManager(State state, Supervisor supervisor) {
        this.state = state;
        this.supervisor = supervisor;
        start();
    }

    /**
     * All new pages should be added to the list here
     */
    private void getAllInstances() {
        pages = new ArrayList<>();

        pages.add(new HomePage(this));
        pages.add(new AddModulePage(state, this));
        pages.add(new AddSessionPage(state, this));
        pages.add(new DeleteModulePage(state, this));
        pages.add(new ManageModulesPage(this));
    }

    /**
     * Sets up and runs the GUI
     */
    private void start() {
        // Creating the module drop down
        ModuleDropDown dropDown = new ModuleDropDown();
        dropDown.createModuleDropDown(state);

        getAllInstances();

        JFrame frame = new JFrame();

        cardPanel = new JPanel();
        cl = new CardLayout();

        cardPanel.setLayout(cl);

        // Adding each page's panel to cardPanel
        for (Page p : pages) {
            cardPanel.add(p.getPanel(), p.getName());
        }

        currentCard = "homePage"; // 1st Card

        frame.getContentPane().add(cardPanel);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                supervisor.onClose();
            }
        });

        // Frame Settings
        frame.setSize(500, 150);
        frame.setTitle("Tempest");
        frame.setLocationRelativeTo(null); // Centering GUI
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Responsible for actually changing what card is showing
     *
     * Should only be called to move down the tree of cards/pages
     *
     * @param cardName The name of the card to switch to
     */
    private void changeCard(String cardName) {
        // Allows the delete module button to be disabled when no modules to delete
        for(Page p: pages) {
            if(p.getName().equals("manageModulesPage")) {
                ((ManageModulesPage) p).update();
            }
        }
        Component prevPanel = getVisibleCard();

        cl.show(cardPanel, cardName);

        Component currentPanel = getVisibleCard();

        if (prevPanel == currentPanel) {
            System.err.println("The card/page you are trying to swap to doesn't exist");
        }
    }

    /**
     * Used to find which panel is currently showing
     *
     * Method from:
     * https://stackoverflow.com/questions/6040989/check-if-a-card-with-a-name-is-present-in-a-cardlayout
     *
     * @return Component - The currently showing card
     */
    private Component getVisibleCard() {
        for (Component c : cardPanel.getComponents()) {
            if (c.isVisible()) {
                return c;
            }
        }

        return null;
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
        changeCard(cardName);
        cards.add(currentCard);
        currentCard = cardName;
    }

    /**
     * Switches to the previous card
     */
    public void swapToPrevCard() {
        String prevCard = cards.pop();
        changeCard(prevCard);
        currentCard = prevCard;
    }

    /**
     * Returns an instance of a page in the cardLayout
     *
     * @param className A class extending page (e.g. HomePage.class)
     * @return Page - The instance of the required class
     */
    public Page getPage(Class<? extends Page> className) {
        for (Page p : pages) {
            if (p.getClass() == className) {
                return p;
            }
        }

        System.err.println("Couldn't find a card with an instance of this class");
        return null;
    }

    /**
     * Returns the name of a page in the cardLayout
     *
     * @param className A class extending page (e.g. HomePage.class)
     * @return String - The name of the page class
     */
    public String getPageName(Class<? extends Page> className) {
        for (Page p : pages) {
            if (p.getClass() == className) {
                return p.getName();
            }
        }

        System.err.println("Couldn't find a card with an instance of this class");
        return null;
    }

    public String getCurrentCard() {
        return currentCard;
    }
}
