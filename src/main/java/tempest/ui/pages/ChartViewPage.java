package tempest.ui.pages;

import java.awt.event.ActionEvent;

import javax.swing.*;

import tempest.State;
import tempest.ui.GUIManager;
import tempest.ui.components.BackButton;
import tempest.ui.components.LinkButton;
import tempest.ui.pages.charts.Chart;

public class ChartViewPage extends Page {
    private static final long serialVersionUID = -7397536728116537358L;

    private BackButton backButton;

    private final LinkButton stackedBarChartLink = new LinkButton("Stacked Bar Chart", PageNames.STACKED_BAR, this);
    private final LinkButton lineChartLink = new LinkButton("Line Chart", PageNames.LINE, this);
    private final LinkButton pieChartLink = new LinkButton("Pie Chart", PageNames.PIE, this);
    private final LinkButton barChartLink = new LinkButton("Bar Chart", PageNames.BAR, this);

    public ChartViewPage(GUIManager guiManager) {
        super(guiManager);

        addNavButtons();
    }

    @Override
    public String getName() {
        return PageNames.CHART_VIEW;
    }

    private void addNavButtons() {
        JPanel chartButtons = new JPanel();
        JPanel backPanel = new JPanel();

        chartButtons.add(stackedBarChartLink);
        chartButtons.add(lineChartLink);
        chartButtons.add(pieChartLink);
        chartButtons.add(barChartLink);

        this.add(chartButtons);

        backButton = new BackButton(manager);
        backPanel.add(backButton);

        this.add(backPanel);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LinkButton source = (LinkButton) e.getSource();
        manager.swapCard(source.getDestination());
    }

    public void updateCharts(Chart[] charts, State state) {
        for (Chart c : charts) {
            c.updateChart(state);
        }
    }

    public BackButton getBackButton() {
        return backButton;
    }

}
