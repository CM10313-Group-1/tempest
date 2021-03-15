package tempest;

import java.io.IOException;
import java.text.ParseException;

import tempest.interfaces.CSVInterface;

public class Supervisor {
    private static State state;
    private CSVInterface csvInterface = new CSVInterface();
    // TODO UserInterface

    private void onStart() {
        try {
            state = csvInterface.getState("store");
        } catch (IOException | ParseException e) {
            System.err.println("Failed to retrieve state");
        }

        // Run CSV code first
        // - Check if CSV empty/nothing to load ?
        // - Load all the study session for these modules
        // Start GUI last - by calling new ModuleView()
    }

    public void onClose() {

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
