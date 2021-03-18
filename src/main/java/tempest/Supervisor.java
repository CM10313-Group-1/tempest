package tempest;

import java.io.IOException;
import java.text.ParseException;

import tempest.GUI.GUIManager;
import tempest.interfaces.CSVInterface;

public class Supervisor {
    private static final String STORE = "store.csv";
    private static State state; // Static so the state made in onStart can be used in onClose
    private CSVInterface csvInterface = new CSVInterface();

    // TODO UserInterface

    private void onStart() {
        try {
            state = csvInterface.getState(STORE);
        } catch (IOException | ParseException e) {
            System.err.println("Failed to retrieve state");
            state = new State();
        }

        // Run CSV code first
        // - Check if CSV empty/nothing to load ?
        // - Load all the study session for these modules
        // Start GUI last - by calling new ModuleView()
        new GUIManager(state, this);
    }

    public void onClose() {
        try {
            csvInterface.saveState(state, STORE);
        } catch (IOException e) {
            System.err.println("Failed to save state");
        }
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        Supervisor.state = state;
    }

    public static void main(String[] args) {
        Supervisor s = new Supervisor();
        s.onStart();
    }
}
