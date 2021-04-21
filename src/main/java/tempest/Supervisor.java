package tempest;

import tempest.interfaces.BinaryInterface;
import tempest.interfaces.CSVInterface;
import tempest.ui.GUIManager;

import java.io.IOException;
import java.text.ParseException;

public class Supervisor {

    private static final String CSV_STORE = "store.csv";
    public static final String BINARY_STORE = "store.bin";
    public static State state;
    private final CSVInterface csvInterface = new CSVInterface();
    private final BinaryInterface binInterface = new BinaryInterface();
    private static Supervisor instance;

    private Supervisor() {
    }

    public static Supervisor getInstance() {
        if (instance == null) {
            instance = new Supervisor();
        }
        return instance;
    }

    private void onStart() {
        try {
            state = binInterface.loadState(BINARY_STORE); // Try to read from binary.
        } catch (IOException b) {
            System.err.println("Failed to retrieve state from binary.");
            try {
                state = csvInterface.getState(CSV_STORE); // Try to read from csv.
            } catch (IOException | ParseException c) {
                System.err.println("Failed to retrieve state from csv.");
                state = new State(); // Generate new state.
            }
        }

        new GUIManager(state, this);
    }

    public void onClose() {
        try {
            csvInterface.saveState(state, CSV_STORE);
            binInterface.saveState(state, BINARY_STORE);
        } catch (IOException e) {
            System.err.println("Failed to save state.");
        }
    }

    public static void main(String[] args) {
        Supervisor s = Supervisor.getInstance();
        s.onStart();
    }
}
