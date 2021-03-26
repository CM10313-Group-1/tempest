package tempest.ui.pages;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import tempest.ui.GUIManager;
import tempest.ui.components.LinkButton;

public class HomePage extends Page {
    private static final long serialVersionUID = -6085163013456560971L;

    private final GUIManager manager;

    private JButton addModuleButton;
    private JButton addSessionButton;

    public HomePage(GUIManager guiManager) {
        super();
        this.manager = guiManager;

        JPanel buttonPanel = new JPanel();
        addModuleButton = new LinkButton("Add a new module", "addModulePage", this);
        addSessionButton = new LinkButton("Add a new session", "addSessionPage", this);
        buttonPanel.add(addModuleButton);
        buttonPanel.add(addSessionButton);
        this.add(buttonPanel);
    }

    @Override
    public String getName() {
        return "homePage";
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
