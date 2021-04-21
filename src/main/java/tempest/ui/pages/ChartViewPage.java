package tempest.ui.pages;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;

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
    private JComboBox comboBox = new JComboBox();
    private BackButton backButton;

    public ChartViewPage(State state, GUIManager guiManager) {
        super(guiManager);
        this.manager = guiManager;

        this.charts = new Chart[] { barChart = new BarChart(state, vm), lineChart = new LineChart(state, vm),
                pieChart = new PieChart(state, vm) };
        vm = new ViewManager<>(charts, charts[0]);
        this.add(vm);
        addNavButtons();
        addDropDownMenu();
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

    private void addDropDownMenu(){
        JPanel comboBoxPanel = new JPanel();

        String[] options = {"last week", "last month", "last year", "all"};
        comboBox = new JComboBox(options);
        comboBox.setSelectedIndex(3);
        comboBox.addActionListener(this);
        comboBox.setVisible(false);

        comboBoxPanel.add(comboBox);
        this.add(comboBoxPanel);
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
        Object source = e.getSource();
        if(source == comboBox){
            setDateFilter((String) ((JComboBox)source).getSelectedItem());
            pieChart.updateChart(Supervisor.state);

        } else {
            String destination = ((LinkButton)source).getDestination();

            vm.changeView(destination);
            comboBox.setVisible(destination.equals(ChartTypes.PIE));

        }
        manager.resizeGUI();
    }

    public void setDateFilter(String selectedItem){
        if(selectedItem.equals("all")){
            pieChart.specifiedDate = null;
        } else {
            LocalDate localDate = LocalDate.now();

            switch (selectedItem){
                case "last week":
                    localDate = localDate.minusWeeks(1);
                    break;

                case "last month":
                    localDate = localDate.minusMonths(1);
                    break;

                case "last year":
                    localDate = localDate.minusYears(1);
                    break;
            }

            pieChart.specifiedDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

    }

}
