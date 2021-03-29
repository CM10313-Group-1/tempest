package tempest.ui.pages;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import tempest.State;
import tempest.ui.GUIManager;
import tempest.ui.ViewManager;
import tempest.ui.components.BackButton;
import tempest.ui.components.LinkButton;
import tempest.ui.components.charts.BarChart;
import tempest.ui.components.charts.Chart;
import tempest.ui.components.charts.ChartTypes;
import tempest.ui.components.charts.LineChart;
import tempest.ui.components.charts.PieChart;

public class ChartViewPage extends Page {
  private static final long serialVersionUID = -7397536728116537358L;
  private Chart[] charts;
  private ViewManager<Chart> vm;

  private LinkButton barChartLink = new LinkButton("Bar Chart", ChartTypes.BAR, this);
  private LinkButton lineChartLink = new LinkButton("Line Chart", ChartTypes.LINE, this);
  private LinkButton pieChartLink = new LinkButton("Pie Chart", ChartTypes.PIE, this);
  private BackButton backButton;

  public ChartViewPage(State state, GUIManager guiManager) {
    super(guiManager);
    this.manager = guiManager;

    this.charts = new Chart[] { new BarChart(state, vm), new LineChart(state, vm), new PieChart(state, vm) };
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
    backButton = new BackButton(manager);

    buttonPanel.add(barChartLink);
    buttonPanel.add(lineChartLink);
    buttonPanel.add(pieChartLink);
    buttonPanel.add(backButton);
    this.add(buttonPanel);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    LinkButton source = (LinkButton) e.getSource();
    System.out.println(source.getDestination());
    vm.changeView(source.getDestination());
  }

}
