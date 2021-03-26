package tempest;

import java.io.IOException;
import java.text.ParseException;

import tempest.interfaces.CSVInterface;
import tempest.ui.GUIManager;

public class Supervisor {
    private static final String STORE = "store.csv";
    private static State state;
    private final CSVInterface csvInterface = new CSVInterface();
    private static Supervisor instance;

    private Supervisor() {
    }

    private void onStart() {
        try {
            state = csvInterface.getState(STORE);
        } catch (IOException | ParseException e) {
            System.err.println("Failed to retrieve state"); // Want to print an error, what if just the 1st time?
            state = new State();
        }

        new GUIManager(state, this);
    }

    public void onClose() {
        try {
            csvInterface.saveState(state, STORE);
        } catch (IOException e) {
            System.err.println("Failed to save state");
        }
    }

    public static Supervisor getInstance() {
        if (instance == null) {
            instance = new Supervisor();
        }
        return instance;
    }

    public static void main(String[] args) {
        Supervisor s = Supervisor.getInstance();
        s.onStart();
    }
}
