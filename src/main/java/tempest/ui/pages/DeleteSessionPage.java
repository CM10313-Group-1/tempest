package tempest.ui.pages;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import tempest.Module;
import tempest.State;
import tempest.StudySession;
import tempest.ui.ErrorMessage;
import tempest.ui.GUIManager;
import tempest.ui.components.BackButton;
import tempest.ui.components.ModuleDropDown;

public class DeleteSessionPage extends Page {
    private static final long serialVersionUID = -8474248378801773736L;

    private final State state;

    private final ErrorMessage errorMessage = new ErrorMessage();
    private final ModuleDropDown moduleDropDown = new ModuleDropDown();

    private JButton deleteButton;
    private BackButton backButton;
    private JComboBox<Object> dropDown;

    private StudySession[] sessions;

    private JTable table;
    private DefaultTableModel tableModel;

    public DeleteSessionPage(State state, GUIManager manager) {
        super(manager);

        this.state = state;

        setupUI();
    }

    @Override
    public String getName() {
        return PageNames.DELETE_SESSION;
    }

    public void setupUI() {
        JPanel optionsPanel = new JPanel();

        // Drop down
        dropDown = moduleDropDown.getModuleDropDown();
        this.add(dropDown);

        // Changing the sessions shown in the table when a module is selected
        dropDown.addActionListener(e -> {

            // Only want this running when this page is active
            if (getName().equals(manager.getCurrentCard())) {
                tableModel.setRowCount(0); // Clearing the table
                populateTable();
            }
        });

        // Creating the table
        JScrollPane scroll = createTable(); // Scroll contains the JTable
        this.add(scroll);

        // Back Button
        backButton = new BackButton(manager);
        optionsPanel.add(backButton);

        // Delete Button
        deleteButton = new JButton("Delete Session");
        deleteButton.setFocusable(false);
        optionsPanel.add(deleteButton);

        // Deleting the selected session
        deleteButton.addActionListener(e -> handleDeletingSession());

        this.add(optionsPanel);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    /**
     * Handles deleting sessions when the delete button is pressed
     */
    private void handleDeletingSession() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow < 0) {
            errorMessage.showMessage(this, new Exception("Please select a row"));
            return;
        }

        getModule().removeSession(sessions[selectedRow]);

        sessions = getModule().getStudySessions();
        tableModel.removeRow(selectedRow);

        for (Module m : state.getModules()) {
            if (m.getStudySessions().length > 0) {
                return; // Still sessions that can be deleted
            }
        }

        // No more study sessions for any modules
        manager.swapToPrevCard();
    }

    /**
     * @return Module - Instance of dropDown's selected module
     */
    private Module getModule() {
        Module module = null;

        String moduleName = (String) dropDown.getSelectedItem();

        // moduleName is null when there are no modules in the drop down
        if (moduleName == null) {
            System.err.println("No modules, therefore no sessions to delete");
            manager.swapToPrevCard();

        } else {
            for (Module m : state.getModules()) {
                if (moduleName.equals(m.getName())) {
                    module = m;
                    break;
                }
            }

            if (module == null) {
                System.err.println("Couldn't find a module with the name: " + moduleName);
            }
        }

        return module;
    }

    /**
     * Populates the table model with all the sessions from the currently selected
     * module
     */
    private void populateTable() {
        sessions = getModule().getStudySessions();

        // Populating the table with the modules sessions
        for (StudySession session : sessions) {
            String date = session.date.toString();
            String duration = session.duration.toMinutes() + " minutes";

            tableModel.addRow(new String[] { date, duration });
        }
    }

    /**
     * Creates the JTable
     *
     * @return JScrollPane - Containing the created JTable
     */
    private JScrollPane createTable() {
        String[] columns = new String[] { "Date", "Duration" };

        tableModel = new DefaultTableModel(columns, 0) {
            private static final long serialVersionUID = 5689863494172287549L;

            @Override
            public boolean isCellEditable(int row, int column) {
                // Setting all cells to be uneditable
                return false;
            }
        };

        // Stopping population if there aren't any modules
        if (dropDown.getItemCount() != 0) {
            populateTable();
        }

        table = new JTable(tableModel);

        table.setRowHeight(30);
        table.setFillsViewportHeight(true); // Table uses entire height of the scroll pane
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Prevents user from selecting > 1 row

        // Centering all the cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        return new JScrollPane(table);
    }

    /**
     * Called when the delete and view sessions button is pressed
     *
     * Without this if the first module in the drop down has had its sessions
     * changed they wouldn't be seen
     */
    public void updateTable() {
        tableModel.setRowCount(0); // Clearing the table
        populateTable();
    }

    public BackButton getBackButton() {
        return backButton;
    }

    public void selectRow(int row) {
        table.setRowSelectionInterval(row, row);
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public void setDropDown(String moduleName) {
        dropDown.setSelectedItem(moduleName);
    }

    public int getRowCount() {
        return table.getRowCount();
    }
}
