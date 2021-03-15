package tempest;

import java.io.IOException;
import java.text.ParseException;

import tempest.interfaces.CSVInterface;

public class Supervisor {
    private State state;
    private CSVInterface csvInterface = new CSVInterface();
    // TODO UserInterface

    private void onStart() {
        try {
            state = csvInterface.getState("store");
        } catch (IOException | ParseException e) {
            System.err.println("Failed to retrieve state");
        }
        // Run CSV code first
        // - Check if CSV empty/nothing to load
        // - Create modules using state
        // - Load all the study session for these modules
        // Start GUI last - by calling new ModuleView()
    }

    private void onClose() {
        // Detect the GUI closing?
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public static void main(String[] args) {
        Supervisor s = new Supervisor();
        s.onStart();
    }
}
