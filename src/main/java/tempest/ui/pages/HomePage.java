package tempest.ui.pages;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import tempest.Module;
import tempest.State;
import tempest.ui.GUIManager;
import tempest.ui.components.LinkButton;

public class HomePage extends Page {
    private static final long serialVersionUID = -6085163013456560971L;

    private final LinkButton manageModulesLink = new LinkButton("Modules", PageNames.MANAGE_MODULES, this);
    private final LinkButton manageSessionsLink = new LinkButton("Sessions", PageNames.MANAGE_SESSIONS, this);
    private final LinkButton chartsLink = new LinkButton("View Data", PageNames.CHART_VIEW, this);

    public HomePage(State state, GUIManager guiManager) {
        super(guiManager);
        addNavButtons();
        setButtonActivity(state.getModules());
    }

    private void addNavButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(manageModulesLink);
        buttonPanel.add(manageSessionsLink);
        buttonPanel.add(chartsLink);

        this.add(buttonPanel);
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
     * Disables the view data button if there are no sessions, otherwise
     * it's enabled
     *
     * @param modules Array of modules from state
     */
    public void setButtonActivity(Module[] modules) {
        for (Module m : modules) {
            if (m.getStudySessions().length > 0) {
                chartsLink.setEnabled(true);
                return;
            }
        if (modules.length > 0){
            manageSessionsLink.setEnabled(true);
            return; }
        }
        manageSessionsLink.setEnabled(false);
        chartsLink.setEnabled(false);
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
}
