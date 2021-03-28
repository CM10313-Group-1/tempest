package tempest.ui.pages;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import tempest.ui.GUIManager;
import tempest.ui.components.LinkButton;

public class HomePage extends Page {
    private static final long serialVersionUID = -6085163013456560971L;

    private final GUIManager manager;

    private final JButton manageModulesButton;
    private final JButton addSessionButton;

    public HomePage(GUIManager guiManager) {
        super();
        this.manager = guiManager;

        JPanel buttonPanel = new JPanel();
        manageModulesButton = new LinkButton("Add a new module", PageNames.MANAGE_MODULES, this);
        addSessionButton = new LinkButton("Add a new session", PageNames.ADD_SESSION, this);

        buttonPanel.add(manageModulesButton);
        buttonPanel.add(addSessionButton);
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

    public JButton getManageModulesButton() {
        return manageModulesButton;
    }

    public JButton getAddSessionButton() {
        return addSessionButton;
    }
}
