package tempest;

import tempest.interfaces.CSVInterface;

public class Supervisor {
    State state;
    CSVInterface csvInterface = new CSVInterface();
    // TODO UserInterface

    private void onStart() {
        // Run CSV code first
        // - Check if CSV empty/nothing to load
        // - Create modules using state
        // - Load all the study session for these modules
        // Start GUI last - by calling new ModuleView()
    }

    private void onClose() {
        // Detect the GUI closing?
    }

    public static void main(String[] args) {

    }
}
