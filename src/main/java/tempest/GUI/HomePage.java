package tempest.GUI;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import tempest.GUI.components.LinkButton;

public class HomePage extends Page {
    private final GUIManager manager;

    private JButton addModuleButton;
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

        addModuleButton = new LinkButton("Add a new module", manager.getPageName(AddModulePage.class), this);
        addSessionButton = new LinkButton("Add a new session", manager.getPageName(AddSessionPage.class), this);

        buttonPanel.add(addModuleButton);
        buttonPanel.add(addSessionButton);

        homePage.add(buttonPanel);

        return homePage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LinkButton source = (LinkButton) e.getSource();
        manager.swapCard(source.getDestination());
    }

    public JButton getAddModuleButton() {
        return addModuleButton;
    }

    public JButton getAddSessionButton() {
        return addSessionButton;
    }
}
