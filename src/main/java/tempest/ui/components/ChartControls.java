package tempest.ui.components;

import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import tempest.Module;
import tempest.State;

public class ChartControls extends JPanel {
  private static final long serialVersionUID = -6226829577531742719L;
  private HashMap<Long, ModuleControls> controls = new HashMap<>();

  public ChartControls(State state) {
    Module[] modules = state.getModules();
    for (Module m : modules) {
      try {
        controls.put(m.hash(), new ModuleControls(this, m));
      } catch (Exception e) {
        System.out.println(e);
      }

    }
    setupUI();
  }

  private void setupUI() {
    this.removeAll();
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    for (ModuleControls m : controls.values()) {
      add(m);
    }
  }
}
