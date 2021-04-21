package tempest.ui.pages;

import tempest.State;
import tempest.Supervisor;
import tempest.ui.GUIManager;
import tempest.ui.ViewManager;
import tempest.ui.components.BackButton;
import tempest.ui.components.LinkButton;
import tempest.ui.components.charts.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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

    private JComboBox pieComboBox = new JComboBox();
    private final String[] options = {"All", "Last week", "Last month", "Last year"};

    private String destination;

    public ChartViewPage(State state, GUIManager guiManager) {
        super(guiManager);
        this.manager = guiManager;

        this.charts = new Chart[]{barChart = new BarChart(state, vm), lineChart = new LineChart(state, vm),
                pieChart = new PieChart(state, vm)};
        vm = new ViewManager<>(charts, charts[0]);
        this.add(vm);
        addNavButtons();
        addDropDownMenus();
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

    private void addDropDownMenus() {
        JPanel comboBoxPanel = new JPanel();

        pieComboBox = new JComboBox(options);
        pieComboBox.setSelectedIndex(0);
        pieComboBox.addActionListener(this);
        pieComboBox.setVisible(false);


        comboBoxPanel.add(pieComboBox);
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
        System.out.println(source.toString());

        if (source == pieComboBox) {
            setDateFilter((String) ((JComboBox) source).getSelectedItem());
            pieChart.updateChart(Supervisor.state);

        } else {
            destination = ((LinkButton) source).getDestination();

            vm.changeView(destination);
            pieComboBox.setVisible(false);

            if (destination.equals(ChartTypes.PIE)) {
                pieComboBox.setVisible(true);
            }
        }
        manager.resizeGUI();
    }

    public void setDateFilter(String selectedItem) {
        if (selectedItem.equals("All")) {
            pieChart.specifiedDate = null;
        } else {
            LocalDate localDate = LocalDate.now();

            switch (selectedItem) {
                case "Last week":
                    localDate = localDate.minusWeeks(1);
                    break;

                case "Last month":
                    localDate = localDate.minusMonths(1);
                    break;

                case "Last year":
                    localDate = localDate.minusYears(1);
                    break;
            }

            pieChart.specifiedDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
    }
}
