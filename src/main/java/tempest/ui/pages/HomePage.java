package tempest.ui.pages;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import tempest.ui.GUIManager;
import tempest.ui.components.LinkButton;

public class HomePage extends Page {
    private static final long serialVersionUID = -6085163013456560971L;

    private final GUIManager manager;

    private final LinkButton manageModulesLink = new LinkButton("Add a new module", PageNames.MANAGE_MODULES, this);
    private final LinkButton addSessionLink = new LinkButton("Add a new session", PageNames.ADD_SESSION, this);
    private final LinkButton chartsLink = new LinkButton("View Data", PageNames.CHART_VIEW, this);

    public HomePage(GUIManager guiManager) {
        super();
        this.manager = guiManager;
        addNavButtons();
    }

    private void addNavButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(manageModulesLink);
        buttonPanel.add(addSessionLink);
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

    public LinkButton getManageModulesButton() {
        return manageModulesLink;
    }

    public LinkButton getAddSessionButton() {
        return addSessionLink;
    }
}
