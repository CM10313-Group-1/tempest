package tempest.ui.components;

import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.JPanel;

import tempest.Module;
import tempest.State;

public class ChartControls extends JPanel {
  private static final long serialVersionUID = -6226829577531742719L;
  private final HashMap<Long, ModuleControl> moduleControls = new HashMap<>();

  public ChartControls(State state) {
    for (Module m : state.getModules()) {
      moduleControls.put(m.hash(), new ModuleControl(this, m));
    }
    setupUI();
  }

  private void setupUI() {
    this.removeAll();
    this.setLayout(new GridLayout(moduleControls.size(), 2));
    for (ModuleControl m : moduleControls.values()) {
      add(m.label);
      add(m.colorPicker);
    }
  }

  public HashMap<Long, ModuleControl> getModuleControls() {
    return moduleControls;
  }
}
