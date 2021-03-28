package tempest.ui.pages;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import tempest.ui.GUIManager;
import tempest.ui.components.LinkButton;

public class HomePage extends Page {
    private static final long serialVersionUID = -6085163013456560971L;

    private final GUIManager manager;

    private JButton manageModulesButton;
    private JButton manageSessionsButton;

    public HomePage(GUIManager guiManager) {
        super();
        this.manager = guiManager;

        setupUI();
    }

    @Override
    public String getName() {
        return PageNames.HOME;
    }

    private void setupUI() {
        JPanel buttonPanel = new JPanel();

        manageModulesButton = new LinkButton("Manage modules", PageNames.MANAGE_MODULES, this);
        manageSessionsButton = new LinkButton("Manage sessions", PageNames.MANAGE_SESSIONS, this);

        buttonPanel.add(manageModulesButton);
        buttonPanel.add(manageSessionsButton);

        this.add(buttonPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LinkButton source = (LinkButton) e.getSource();
        manager.swapCard(source.getDestination());
    }

    public JButton getManageModulesButton() {
        return manageModulesButton;
    }

    public JButton getManageSessionsButton() {
        return manageSessionsButton;
    }
}
