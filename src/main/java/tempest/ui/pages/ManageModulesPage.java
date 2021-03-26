package tempest.ui.pages;

import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import tempest.ui.GUIManager;
import tempest.ui.components.BackButton;
import tempest.ui.components.LinkButton;
import tempest.ui.components.ModuleDropDown;

public class ManageModulesPage extends Page {
    private static final long serialVersionUID = -3556076472382354523L;
    private final GUIManager manager;
    private final ModuleDropDown moduleDropDown = new ModuleDropDown();

    private JButton addModuleButton;
    private JButton deleteModuleButton;
    private BackButton backButton;
    private JComboBox<Object> dropDown;

    public ManageModulesPage(GUIManager guiManager) {
        super();
        this.manager = guiManager;
        setupUI();
    }

    @Override
    public String getName() {
        return "manageModulesPage";
    }

    private void setupUI() {
        JPanel pageSwapPanel = new JPanel();
        JPanel backPanel = new JPanel();

        dropDown = moduleDropDown.getModuleDropDown();

        addModuleButton = new LinkButton("Add a module", manager.getPageName(AddModulePage.class), this);
        deleteModuleButton = new LinkButton("Delete a module", manager.getPageName(DeleteModulePage.class), this);
        backButton = new BackButton(manager);

        pageSwapPanel.add(deleteModuleButton);
        pageSwapPanel.add(addModuleButton);
        backPanel.add(backButton);

        this.add(pageSwapPanel);
        this.add(backPanel);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    /**
     * Updates the delete module button to be disabled if there are no modules to
     * delete.
     */
    public void update() {
        // If a module/modules have been created then the button is active again
        deleteModuleButton.setEnabled(dropDown.getItemCount() != 0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LinkButton source = (LinkButton) e.getSource();
        manager.swapCard(source.getDestination());
    }

    public JButton getAddModuleButton() {
        return addModuleButton;
    }

    public JButton getDeleteModuleButton() {
        return deleteModuleButton;
    }

    public JButton getBackButton() {
        return backButton;
    }
}
