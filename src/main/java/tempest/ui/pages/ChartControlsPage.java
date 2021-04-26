package tempest.ui.pages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;

import tempest.State;
import tempest.ui.GUIManager;
import tempest.ui.components.ChartControls;
import tempest.ui.components.ModuleControl;

public class ChartControlsPage extends Page implements ActionListener {
  private static final long serialVersionUID = -8177080116982919423L;
  private State state;
  private ChartControls controls;
  private final JButton resetButton = new JButton("Reset All");

  public ChartControlsPage(State state, GUIManager manager) {
    super(manager);
    this.state = state;
    setupUI();
  }

  private void setupUI() {
    this.removeAll();
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.add(controls = new ChartControls(state));
    backPanel.add(resetButton);
    resetButton.addActionListener(this);

    this.add(backPanel);
  }

  public void updateState(State state) {
    this.state = state;
    setupUI();
  }

  @Override
  public String getName() {
    return PageNames.CHART_CONTROLS;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object source = e.getSource();

    if (source == resetButton) {
      resetToDefaults();
    }
  }

  private void resetToDefaults() {
    for (ModuleControl m : controls.getModuleControls().values()) {
      m.resetToDefaultColor();
    }
  }
}