package tempest.GUI;

import tempest.State;
import tempest.Supervisor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Stack;

//TODO:
// - Change AddModule and AddSession pages to be Modules and Session pages with options to delete, view & add
// - Do addModule() and deleteModule() need to be in the manager?

public class GUIManager {
    private static JPanel cardPanel;
    private static CardLayout cl;

    private HomePage home;
    private AddModulePage addModule;
    private AddSessionPage addSession;

    private String currentCard;
    private final Stack<String> cards = new Stack<>();

    private final State state;
    private final Supervisor supervisor;

    public GUIManager(State state, Supervisor supervisor){
        this.state = state;
        this.supervisor = supervisor;
        run();
    }

    /**
     * Sets up and runs the GUI
     */
    private void run(){
        JFrame frame = new JFrame();

        GUIComponents components = new GUIComponents();
        components.createModuleDropDown(state);

        home = new HomePage(this);
        addModule = new AddModulePage(state, this);
        addSession = new AddSessionPage(state, this);

        cardPanel = new JPanel();
        cl = new CardLayout();

        cardPanel.setLayout(cl);

        // Adding panels to cardPanel
        cardPanel.add(home.getPanel(), "home");
        cardPanel.add(addModule.getPanel(), "addModule");
        cardPanel.add(addSession.getPanel(), "addSession");

        currentCard = "home"; // 1st Card

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

    public HomePage getHomePage() {
        return home;
    }

    public AddModulePage getModulePage() {
        return addModule;
    }

    public AddSessionPage getSessionPage() {
        return addSession;
    }

    public String getCurrentCard() {
        return currentCard;
    }
}
