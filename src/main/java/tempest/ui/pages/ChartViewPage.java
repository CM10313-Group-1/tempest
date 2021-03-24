package tempest.ui.pages;

import javax.swing.JPanel;

import tempest.State;
import tempest.ui.GUIManager;

public class ChartViewPage extends Page {
  private final GUIManager manager;
  private final State state;

  private JPanel chartPanel;

  public ChartViewPage(State state, GUIManager guiManager) {
    this.state = state;
    this.manager = guiManager;
  }

  @Override
  public JPanel getPanel() {
    chartPanel = new JPanel();
    return chartPanel;
  }

  @Override
  public String getName() {
    return "chartView";
  }

}
