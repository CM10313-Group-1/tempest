package tempest.GUI;

import tempest.GUI.components.ModuleDropDown;
import tempest.State;
import tempest.Supervisor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Stack;

public class GUIManager {
    private static JPanel cardPanel;
    private static CardLayout cl;

    private ArrayList<Page> pages;

    private String currentCard;
    private final Stack<String> cards = new Stack<>();

    private final State state;
    private final Supervisor supervisor;

    public GUIManager(State state, Supervisor supervisor){
        this.state = state;
        this.supervisor = supervisor;
        start();
    }

    /**
     * All new pages should be added to the list here
     */
    public void getAllInstances() {
        pages = new ArrayList<>();

        pages.add(new HomePage(this));
        pages.add(new AddModulePage(state, this));
        pages.add(new AddSessionPage(state, this));
    }

    /**
     * Sets up and runs the GUI
     */
    private void start(){
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
        frame.setSize(500,150);
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
    private void changeCard(String cardName){
        cl.show(cardPanel, cardName);
    }

    /**
     * Swaps to the entered card name
     *
     * Used to move back up the tree of cards/pages along the path taken on the way down
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

    public Page getPage(String pageName) {
        for (Page p : pages) {
            if (pageName.equals(p.getName())) {
                return p;
            }
        }
        System.err.println("Could not find a page with the name: " + pageName);
        return null;
    }

    public String getCurrentCard() {
        return currentCard;
    }
}
