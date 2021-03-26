package tempest.ui.pages;

import tempest.State;
import tempest.ui.GUIManager;
import tempest.ui.ViewManager;
import tempest.ui.components.charts.BarChart;
import tempest.ui.components.charts.Chart;
import tempest.ui.components.charts.LineChart;
import tempest.ui.components.charts.PieChart;

public class ChartViewPage extends Page {
  private static final long serialVersionUID = -7397536728116537358L;
  private final GUIManager manager;
  private final State state;
  private Chart[] charts;
  private ViewManager<Chart> vm;


  public ChartViewPage(State state, GUIManager guiManager) {
    super();
    this.state = state;
    this.manager = guiManager;

    this.charts = new Chart[]{new BarChart(), new LineChart(), new PieChart()};
    vm = new ViewManager<Chart>(charts, charts[0]);
    this.add(vm);
  }

  @Override
  public String getName() {
    return "chartView";
  }

  private void addNavButtons() {
    // TODO add buttons for changing charts
  }

}
