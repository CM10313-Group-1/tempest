package tempest.GUI;

import tempest.Supervisor;

public class GUIHelper {

    public GUIHelper() {
        Supervisor supervisor = Supervisor.getInstance();
        supervisor.start();
    }
}
