package tempest.ui.pages;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import tempest.State;
import tempest.ui.GUIManager;
import tempest.ui.ViewManager;
import tempest.ui.components.LinkButton;
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

    this.charts = new Chart[] { new BarChart(), new LineChart(), new PieChart() };
    vm = new ViewManager<Chart>(charts, charts[0]);
    this.add(vm);
    addNavButtons();
  }

  @Override
  public String getName() {
    return PageNames.CHART_VIEW;
  }

  private void addNavButtons() {
    JPanel buttonPanel = new JPanel();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    LinkButton source = (LinkButton) e.getSource();
    vm.changeView(source.getDestination());
  }

}
