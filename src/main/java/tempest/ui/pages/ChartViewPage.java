package tempest.ui.pages;

import java.awt.CardLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;

import tempest.State;
import tempest.ui.GUIManager;
import tempest.ui.components.charts.Chart;

public class ChartViewPage extends Page {
  private final GUIManager manager;
  private final State state;
  private final Map<String, Chart> charts = new HashMap<String, Chart>();
  private String activeChart;

  private ChartPanel chartPanel;
  private CardLayout cardLayout;

  public ChartViewPage(State state, GUIManager guiManager) {
    this.state = state;
    this.manager = guiManager;
  }

  @Override
  public JPanel getPanel() {
    return chartPanel;
  }

  @Override
  public String getName() {
    return "chartView";
  }

  private void addNavButtons() {
    // TODO add buttons for changing charts
  }

}
