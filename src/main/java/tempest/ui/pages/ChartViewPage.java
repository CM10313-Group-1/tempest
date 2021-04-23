package tempest.ui.pages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import tempest.State;
import tempest.ui.GUIManager;
import tempest.ui.components.BackButton;
import tempest.ui.components.LinkButton;
import tempest.ui.pages.charts.Chart;

public class ChartViewPage extends Page implements ActionListener {
    private static final long serialVersionUID = -7397536728116537358L;

    private BackButton backButton;

    private final LinkButton stackedBarChartLink = new LinkButton("Stacked Bar Chart", PageNames.STACKED_BAR, this);
    private final LinkButton lineChartLink = new LinkButton("Line Chart", PageNames.LINE, this);
    private final LinkButton pieChartLink = new LinkButton("Pie Chart", PageNames.PIE, this);
    private final LinkButton timeBarChartLink = new LinkButton("% Time Studied per Hour", PageNames.TIME_BAR, this);

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
        chartButtons.add(timeBarChartLink);

        this.add(chartButtons);

        backButton = new BackButton(manager);
        backPanel.add(backButton);
        backPanel.add(new LinkButton("Chart Controls", PageNames.CHART_CONTROLS, this));

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

    public LinkButton getStackedBarChartButton() {
        return stackedBarChartLink;
    }

    public LinkButton getLineChartButton() {
        return lineChartLink;
    }

    public LinkButton getPieChartButton() {
        return pieChartLink;
    }

    public LinkButton getTimeBarChartButton() {
        return timeBarChartLink;
    }

}
