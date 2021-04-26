package tempest.ui.pages;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;

import tempest.Module;
import tempest.State;
import tempest.StudySession;
import tempest.ui.GUIManager;
import tempest.ui.components.LinkButton;

public class HomePage extends Page implements ActionListener {
    private static final long serialVersionUID = -6085163013456560971L;

    private final LinkButton manageModulesLink = new LinkButton("Modules", PageNames.MANAGE_MODULES, this);
    private final LinkButton manageSessionsLink = new LinkButton("Sessions", PageNames.MANAGE_SESSIONS, this);
    private final LinkButton chartsLink = new LinkButton("View Data", PageNames.CHART_VIEW, this);
    private final LinkButton goalEntryLink = new LinkButton("Enter Goals", PageNames.GOAL_ENTRY, this);
    private final LinkButton DataLink = new LinkButton("Data Protection Information", PageNames.DATA_PROTECTION, this);

    private final State state;

    private final HashMap<Module , JProgressBar> progressBars = new HashMap<>();
    private Date prevMonDate;

    private final JPanel progressPanel = new JPanel();

    public HomePage(State state, GUIManager guiManager) {
        super(guiManager);
        this.state = state;

        JPanel panel = new JPanel();

        setButtonActivity(state.getModules()); // Greying out buttons if required
        panel.add(navButtons());

        panel.add(progressBars());

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        this.add(panel);
    }

    private JPanel navButtons() {
        JPanel navPanel = new JPanel();

        navPanel.add(manageModulesLink);
        navPanel.add(manageSessionsLink);
        navPanel.add(chartsLink);
        navPanel.add(goalEntryLink);
        navPanel.add(DataLink);

        return navPanel;
    }

    private JPanel progressBars() {
        // Getting date of the most recent Monday
        LocalDate prevMon = LocalDate.now(ZoneId.systemDefault()).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        prevMonDate = java.util.Date.from(prevMon.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        // Setting progress bar text colour
        UIManager.put("ProgressBar.selectionForeground", Color.darkGray);
        UIManager.put("ProgressBar.selectionBackground", Color.darkGray);

        // Create progress bars
        createBarPerModule();

        // Setting grid layout for bars
        double rows = progressBars.size() / 2.0;
        progressPanel.setLayout(new GridLayout((int) Math.round(rows), 2, 5, 5));

        return progressPanel;
    }

    /**
     * For any modules that have a goal a progress bar is created for this module
     */
    private void createBarPerModule() {
        for (Module m : state.getModules()) {
            if (m.getWeeklyGoal() == 0) {
                continue; // Don't create a bar for modules with no goal
            }

            createProgressBar(m);
        }
    }

    /**
     * Creates a progress bar for Module m
     *
     * @param m The module for the bar
     */
    private void createProgressBar(Module m) {
        JProgressBar bar = new JProgressBar();

        populateBar(m, bar); // Set progress of bar
        bar.setStringPainted(true);
        bar.setFocusable(false);

        progressBars.put(m, bar);
        progressPanel.add(bar);

        double rows = progressBars.size() / 2.0;
        progressPanel.setLayout(new GridLayout((int) Math.round(rows), 2, 5, 5));
    }

    /**
     * Populates the bar with the total time of sessions studied from
     * the most recent Monday
     *
     * @param m The module for the bar
     * @param bar The JProgressBar to be populated
     */
    private void populateBar(Module m, JProgressBar bar) {
        int totalTime = 0;

        for (StudySession s : m.getStudySessions()) {
            if (s.date.after(prevMonDate) || s.date.equals(prevMonDate)) {
                totalTime += s.duration.toMinutes();
            }
        }

        // Set bar's min and max
        bar.setMinimum(0);
        bar.setMaximum(m.getWeeklyGoal());

        // Set the bar's progress to be the total time
        bar.setValue(totalTime);
        bar.setString(totalTime + " / " + m.getWeeklyGoal() + " mins: " + m.getName());
    }

    /**
     * Updates the Progress Bars
     *
     * Updates the pages buttons
     *
     * @param modules Array of modules from state
     */
    public void updatePage(Module[] modules) {
        updateBars();
        setButtonActivity(modules);
    }

    /**
     * Creates new bars for each goal
     */
    private void updateBars() {
        progressPanel.removeAll();
        progressBars.clear();
        createBarPerModule();
    }

    /**
     * Disables the view data button if there are no sessions, otherwise
     * it's enabled
     *
     * Disables the sessions & enter goals buttons if there are no modules, otherwise
     * they're enabled
     *
     * @param modules Array of modules from state
     */
    private void setButtonActivity(Module[] modules) {
        if (modules.length > 0) {
            manageSessionsLink.setEnabled(true);
            goalEntryLink.setEnabled(true);

            for (Module m : modules) {
                if (m.getStudySessions().length > 0) {
                    chartsLink.setEnabled(true);
                    return;
                }
                chartsLink.setEnabled(false);
            }
            return;
        }

        manageSessionsLink.setEnabled(false);
        chartsLink.setEnabled(false);
        goalEntryLink.setEnabled(false);
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
