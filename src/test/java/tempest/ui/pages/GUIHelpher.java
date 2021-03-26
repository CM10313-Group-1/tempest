package tempest.GUI;

import tempest.Supervisor;

public class GUIHelpher {

    public GUIHelpher() {
        Supervisor supervisor = Supervisor.getInstance();
        supervisor.start();
    }

    public void getState() {

    }

}
