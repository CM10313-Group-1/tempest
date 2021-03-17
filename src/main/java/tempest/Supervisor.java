package tempest;

import java.io.IOException;
import java.text.ParseException;

import tempest.interfaces.CSVInterface;

public class Supervisor {
    private static final String STORE = "store.csv";
    private static State state; // Static so the state made in onStart can be used in onClose
    private CSVInterface csvInterface = new CSVInterface();

    // TODO:

    private void onStart() {
        try {
            state = csvInterface.getState(STORE);
        } catch (IOException | ParseException e) {
            System.err.println("Failed to retrieve state"); // Want to print an error, what if just the 1st time?
            state = new State();
        }

        new ModuleView(state, this);
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
