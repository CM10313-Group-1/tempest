package tempest.ui.pages.charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import tempest.Module;
import tempest.State;
import tempest.StudySession;
import tempest.ui.GUIManager;
import tempest.ui.pages.PageNames;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.Objects;

public class PieChart extends Chart {
    private static final long serialVersionUID = 7074811797165362922L;
    public Date specifiedDate = null;

    private PiePlot<Integer> plot;

    private final JComboBox<String> pieComboBox;

    //TODO: Only populate with modules with sessions

    public PieChart(State state, GUIManager manager) {
        super(state, manager);

        String[] options = {"All", "Last week", "Last month", "Last year"};
        pieComboBox = new JComboBox<>(options);

        pieComboBox.addActionListener(e -> {
            setDateFilter((String) Objects.requireNonNull(pieComboBox.getSelectedItem()));
            updateChart(state);
            manager.resizeGUI();
        });

        setupUI();
    }

    private void setupUI() {
        this.removeAll();

        this.add(createChart());

        JPanel dropDownPanel = new JPanel();
        dropDownPanel.add(pieComboBox);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backPanel);
        buttonPanel.add(dropDownPanel);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        this.add(buttonPanel);
    }

    @SuppressWarnings("unchecked")
    private ChartPanel createChart() {
        PieDataset<String> dataset = generateDataset(state);
        String title = "Total Time Studied Per Module";
        ChartPanel pieChart = new ChartPanel(ChartFactory.createPieChart(title, dataset, true, true, false));

        // Changes the label formatting to allow minutes to be shown.
        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator("{0} = {1} mins");
        plot = (PiePlot<Integer>) pieChart.getChart().getPlot();
        plot.setLabelGenerator(labelGenerator);
        plot.setBackgroundPaint(Color.DARK_GRAY);

        setModuleColors(state.getModules());
        plot.setShadowPaint(null); // Removing the shadow

        // When hovering over sections hours are displayed
        plot.setToolTipGenerator((pieDataset, comparable) -> {
            int sectionMins = pieDataset.getValue(comparable).intValue();

            return String.format("%s: %d hrs %02d mins", comparable.toString(), sectionMins / 60, sectionMins % 60);
        });

        return pieChart;
    }

    @Override
    public void updateChart(State state) {
        this.state = state;
        setupUI();
    }

    /**
     * Creates the dataset used by the pie chart.
     *
     * @param state Instance of state
     * @return The dataset consisting of module names and total study time in
     * minutes
     */
    private PieDataset<String> generateDataset(State state) {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        LinkedList<StudySession> studySessions;
        int totalStudyTime;

        for (Module m : state.getModules()) {
            if (m.getStudySessions().length <= 0) {
                continue;
            }

            totalStudyTime = 0;

            // Filters the list and picks sessions for the valid time period
            studySessions = new LinkedList<>(Arrays.asList(m.getStudySessions()));
            studySessions = filterList(studySessions);

            for (StudySession studySession : studySessions) {
                totalStudyTime += studySession.duration.toMinutes();
            }

            dataset.setValue(m.getName(), totalStudyTime);
        }

        return dataset;
    }

    private LinkedList<StudySession> filterList(LinkedList<StudySession> studySessions) {
        // If the date is null, return the list. No filtering required
        if (specifiedDate == null) {
            return studySessions;
        }

        LinkedList<StudySession> filteredSessions = new LinkedList<>();

        for (StudySession session : studySessions) {
            if ((session.date).after(specifiedDate)) {
                filteredSessions.add(session);
            }
        }

        return filteredSessions;
    }

    private void setDateFilter(String selectedItem) {
        if (selectedItem.equals("All")) {
            this.specifiedDate = null;
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

            this.specifiedDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
    }

    private void setModuleColors(Module[] modules) {
        for (Module module : modules) {
            if (module.getStudySessions().length > 0) {
                plot.setSectionPaint(module.getName(), module.getColor());
            }
        }
    }

    @Override
    public String getName() {
        return PageNames.PIE;
    }
}
