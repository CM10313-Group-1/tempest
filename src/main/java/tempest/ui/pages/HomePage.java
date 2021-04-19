package tempest.ui.pages;

import java.awt.event.ActionEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import tempest.Module;
import tempest.State;
import tempest.StudySession;
import tempest.ui.GUIManager;
import tempest.ui.components.LinkButton;

//TODO:
// - Decide on how to label the different bars
//      1. Keep as atm with name in the bar
//      2. Have a JLabel attached - will need to create layouts to get the labels looking good

public class HomePage extends Page {
    private static final long serialVersionUID = -6085163013456560971L;

    private final LinkButton manageModulesLink = new LinkButton("Modules", PageNames.MANAGE_MODULES, this);
    private final LinkButton manageSessionsLink = new LinkButton("Sessions", PageNames.MANAGE_SESSIONS, this);
    private final LinkButton chartsLink = new LinkButton("View Data", PageNames.CHART_VIEW, this);
    private final LinkButton goalEntryLink = new LinkButton("Enter Goals", PageNames.GOAL_ENTRY, this);
    private final LinkButton DataLink = new LinkButton("Data Protection Information", PageNames.DATA_PROTECTION, this);

    private final State state;

    private final HashMap<Module , JProgressBar> progressBars = new HashMap<>();
    private final Date prevMonDate;

    public HomePage(State state, GUIManager guiManager) {
        super(guiManager);
        this.state = state;

        addNavButtons();
        updatePage(state.getModules());

        System.out.println("constructor");

        LocalDate prevMon = LocalDate.now(ZoneId.systemDefault()).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        prevMonDate = java.util.Date.from(prevMon.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        makeProgressBars();
    }

    private void addNavButtons() {
        this.add(manageModulesLink);
        this.add(manageSessionsLink);
        this.add(chartsLink);
        this.add(goalEntryLink);
        this.add(DataLink);
    }

    private void makeProgressBars() {
        for (Module m : state.getModules()) {
            if (m.getWeeklyGoal() == 0) {
                continue;
            }
            System.out.println("creating bars");
            createProgressBar(m);
        }
    }

    private void createProgressBar(Module m) {
        JProgressBar bar = new JProgressBar(0, m.getWeeklyGoal());

        updateProgressBar(m, bar);
        bar.setStringPainted(true);

        progressBars.put(m, bar);
        this.add(bar);
    }

    @Override
    public String getName() {
        return PageNames.HOME;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LinkButton source = (LinkButton) e.getSource();
        manager.swapCard(source.getDestination());
    }

    /**
     * Updates the Progress Bars
     *
     * Disables the view data button if there are no sessions, otherwise
     * it's enabled
     *
     * Disables the sessions & enter goals buttons if there are no modules, otherwise
     * they're enabled
     *
     * @param modules Array of modules from state
     */
    public void updatePage(Module[] modules) {
        for (Map.Entry<Module, JProgressBar> barMap : progressBars.entrySet()) {
            Module m = barMap.getKey();
            JProgressBar bar = barMap.getValue();

            updateProgressBar(m, bar);
        }

        if (modules.length > 0) {
            manageSessionsLink.setEnabled(true);
            goalEntryLink.setEnabled(true);
            for (Module m : modules) {
                if (m.getStudySessions().length > 0) {
                    chartsLink.setEnabled(true);
                    return;
                }
            }
            return;
        }

        manageSessionsLink.setEnabled(false);
        chartsLink.setEnabled(false);
        goalEntryLink.setEnabled(false);
    }

    private void updateProgressBar(Module m, JProgressBar bar) {
        int totalTime = 0;

        for (StudySession s : m.getStudySessions()) {
            if (s.date.after(prevMonDate) || s.date.equals(prevMonDate)) {
                totalTime += s.duration.toMinutes();
            }
        }

//        JLabel name = new JLabel(m.getName());
//        name.setLabelFor(bar);
//        this.add(name);

        // Set the progress as total time
        bar.setValue(totalTime);
        bar.setString(totalTime + " / " + m.getWeeklyGoal() + " mins: " + m.getName());
    }

    public void createBar(Module m) {
        createProgressBar(m);
    }

    public LinkButton getManageModulesButton() {
        return manageModulesLink;
    }

    public LinkButton getManageSessionsButton() {
        return manageSessionsLink;
    }

    public LinkButton getChartViewButton() {
        return chartsLink;
    }

    public LinkButton getEnterGoalsButton() {
        return goalEntryLink;
    }

    public LinkButton getDataProtectionButton() {
        return DataLink;
    }
}
