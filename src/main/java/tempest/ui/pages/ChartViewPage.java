package tempest.ui.pages;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;

import tempest.State;
import tempest.Supervisor;
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
    private BarChart barChart;
    private LineChart lineChart;
    private PieChart pieChart;

    private ViewManager<Chart> vm;

    private final LinkButton barChartLink = new LinkButton("Bar Chart", ChartTypes.BAR, this);
    private final LinkButton lineChartLink = new LinkButton("Line Chart", ChartTypes.LINE, this);
    private final LinkButton pieChartLink = new LinkButton("Pie Chart", ChartTypes.PIE, this);
    private BackButton backButton;

    public ChartViewPage(State state, GUIManager guiManager) {
        super(guiManager);
        this.manager = guiManager;

        this.charts = new Chart[] { barChart = new BarChart(state, vm), lineChart = new LineChart(state, vm),
                pieChart = new PieChart(state, vm) };
        vm = new ViewManager<>(charts, charts[0]);
        this.add(vm);
        addNavButtons();
        addVisibilityListener();
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

    private void addVisibilityListener() {
        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                return;
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                return;
            }

            @Override
            public void componentShown(ComponentEvent e) {
                State currentState = Supervisor.state;
                lineChart.updateChart(currentState);
                barChart.updateChart(currentState);
                pieChart.updateChart(currentState);
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                return;
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LinkButton source = (LinkButton) e.getSource();
        String destination = (String) source.getDestination();

        vm.changeView(destination);
        manager.resizeGUI();
    }

}
