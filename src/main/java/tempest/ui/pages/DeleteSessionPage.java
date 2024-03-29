package tempest.ui.pages;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import tempest.ui.components.ModuleDropDown;

import java.util.ArrayList;
import java.util.Arrays;

public class DeleteSessionPage extends Page {
    private static final long serialVersionUID = -8474248378801773736L;

    private final State state;

    private final ErrorMessage errorMessage = new ErrorMessage();
    private final ModuleDropDown moduleDropDown = new ModuleDropDown();

    private JButton deleteButton;
    private JComboBox<Object> dropDown;

    private ArrayList<StudySession> sessions;

    private JTable table;
    private DefaultTableModel tableModel;

    private JCheckBox allCheckBox;

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
        // All Sessions CheckBox
        JPanel checkBox = new JPanel();

        allCheckBox = new JCheckBox("All Sessions");
        allCheckBox.setFocusable(false);

        // Changing the table when the check box is toggled
        allCheckBox.addActionListener(e -> handleCheckBox());

        checkBox.add(allCheckBox);
        this.add(checkBox);

        // Drop down
        dropDown = moduleDropDown.getModuleDropDown();
        this.add(dropDown);

        // Changing the sessions shown in the table when a module is selected
        dropDown.addActionListener(e -> handleDropDown());

        // Creating the table
        JScrollPane scroll = createTable(); // Scroll contains the JTable
        this.add(scroll);

        // Delete Button
        deleteButton = new JButton("Delete Session");
        deleteButton.setFocusable(false);

        // Deleting the selected session
        deleteButton.addActionListener(e -> handleDeletingSession());

        backPanel.add(deleteButton);

        this.add(backPanel);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    /**
     * Handles the check box being ticked and un-ticked
     */
    private void handleCheckBox() {
        if (allCheckBox.isSelected()) {
            dropDown.setEnabled(false); // Disable drop down
            populateTableAllSessions();

        } else {
            checkBoxDeselected();
        }
    }

    /**
     * Updates table to show the selected module's sessions
     */
    private void handleDropDown() {
        // Only want this running when this page is active
        if (getName().equals(manager.getCurrentCard())) {
            tableModel.setRowCount(0); // Clear the table
            populateTable();
        }
    }

    /**
     * Called when the check box is deselected
     */
    private void checkBoxDeselected() {
        // Enable drop down
        dropDown.setEnabled(true);

        // Model without the Module column
        setModel(new String[] { "Date", "Duration" });

        // Populate using drop down
        populateTable();
    }

    /**
     * Used to set the tables model
     *
     * @param columns A String[] containing the names of the desired columns
     */
    private void setModel(String[] columns) {
        table.setModel( tableModel = new DefaultTableModel(columns, 0) {
        private static final long serialVersionUID = 5689863494172287549L;

            @Override
            public boolean isCellEditable(int row, int column) {
                // Setting all cells to be uneditable
                return false;
            }
        });
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

        if (allCheckBox.isSelected()) {

            Object moduleName = tableModel.getValueAt(selectedRow, 0);

            for (Module m : state.getModules()) {
                if (m.getName().equals(moduleName.toString())) {
                    m.removeSession(sessions.get(selectedRow));

                    sessions.remove(sessions.get(selectedRow));
                    tableModel.removeRow(selectedRow);
                }
            }
        } else {
            getModule().removeSession(sessions.get(selectedRow));

            sessions = new ArrayList<>(Arrays.asList(getModule().getStudySessions()));
            tableModel.removeRow(selectedRow);
        }



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
        sessions = new ArrayList<>(Arrays.asList(getModule().getStudySessions()));

        // Populating the table with the modules sessions
        for (StudySession session : sessions) {
            String date = session.date.toString();
            String duration = session.duration.toMinutes() + " minutes";

            tableModel.addRow(new String[] { date, duration });
        }
    }

    /**
     * Populates the table model wth every session - adding a module column
     */
    private void populateTableAllSessions() {
        setModel(new String[] { "Module", "Date", "Duration" });

        sessions = new ArrayList<>();

        for (Module m : state.getModules()) {
            for (StudySession s : m.getStudySessions()) {
                sessions.add(s);

                String date = s.date.toString();
                String duration = s.duration.toMinutes() + " minutes";

                tableModel.addRow(new String[]{m.getName(), date, duration});
            }
        }
    }

    /**
     * Creates the JTable
     *
     * @return JScrollPane - Containing the created JTable
     */
    private JScrollPane createTable() {
        table = new JTable();

        setModel(new String[] { "Date", "Duration" });

        // Stopping population if there aren't any modules
        if (dropDown.getItemCount() != 0) {
            populateTable();
        }

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

        if (allCheckBox.isSelected()) {
            populateTableAllSessions();
        } else {
            populateTable();
        }
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JCheckBox getCheckBox() {
        return allCheckBox;
    }

    public int getRowCount() {
        return table.getRowCount();
    }

    public void selectRow(int row) {
        table.setRowSelectionInterval(row, row);
    }

    public void setDropDown(String moduleName) {
        dropDown.setSelectedItem(moduleName);
    }
}
