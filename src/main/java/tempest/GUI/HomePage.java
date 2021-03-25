package tempest.GUI;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import tempest.GUI.components.LinkButton;

public class HomePage extends Page {
    private final GUIManager manager;

    private JButton manageModulesButton;
    private JButton addSessionButton;

    public HomePage(GUIManager guiManager) {
        this.manager = guiManager;
    }

    @Override
    public String getName() {
        return "homePage";
    }

    public JPanel getPanel() {
        JPanel homePage = new JPanel();

        JPanel buttonPanel = new JPanel();

        manageModulesButton = new LinkButton("Manage modules", manager.getPageName(ManageModulesPage.class), this);
        addSessionButton = new LinkButton("Manage Sessions", manager.getPageName(ManageSessionsPage.class), this);

        buttonPanel.add(manageModulesButton);
        buttonPanel.add(addSessionButton);

        homePage.add(buttonPanel);

        return homePage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LinkButton source = (LinkButton) e.getSource();
        manager.swapCard(source.getDestination());
    }

    public JButton getManageModulesButton() {
        return manageModulesButton;
    }

    public JButton getAddSessionButton() {
        return addSessionButton;
    }
}
