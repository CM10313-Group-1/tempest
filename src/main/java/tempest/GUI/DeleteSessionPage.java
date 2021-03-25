package tempest.GUI;

import tempest.GUI.components.BackButton;
import tempest.GUI.components.ModuleDropDown;
import tempest.Module;
import tempest.State;
import tempest.StudySession;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Objects;

public class DeleteSessionPage extends Page {

    //TODO:
    // - Fix module drop down problem when adding a session & deleting a module
    // - Can the table be updated when first loaded???
    // - Grey out button if no sessions in any modules???
    // - Convert date to local date - or do we need to store all of date to be able to see time session entered not just day
    // - Scrap all option in drop down???
    // - Add Module Page -> add another panel to allow for resizing

    private final State state;
    private final GUIManager manager;

    private final ErrorMessage errorMessage = new ErrorMessage();
    private final ModuleDropDown moduleDropDown = new ModuleDropDown();

    private JPanel pagePanel;

    private BackButton backButton;
    private JComboBox<Object> dropDown;

    private StudySession[] sessions;

    private JTable table;
    private DefaultTableModel tableModel;

    public DeleteSessionPage(State state, GUIManager manager) {
        this.state = state;
        this.manager = manager;
    }

    @Override
    public String getName() {
        return "deleteSessionPage";
    }

    @Override
    public JPanel getPanel() {
        pagePanel = new JPanel();
        JPanel optionsPanel = new JPanel();

        // Drop down -> w/ "all" options as well???
        dropDown = moduleDropDown.getModuleDropDown();
        pagePanel.add(dropDown);

        // Changing the sessions shown in the table when a module is selected
        dropDown.addActionListener(e -> {
            tableModel.setRowCount(0); // Clearing the table
            populateTable();
        });

        // Creating the table
        JScrollPane scroll = createTable(); // scroll contains the JTable

        pagePanel.add(scroll);

        // Back Button
        backButton = new BackButton(manager);
        optionsPanel.add(backButton);

        // Delete Button
        JButton deleteButton = new JButton("Delete Session");
        deleteButton.setFocusable(false);
        optionsPanel.add(deleteButton);

        // Deleting the selected session
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();

            if (selectedRow < 0) {
                errorMessage.showMessage(pagePanel, new Exception("Please select a row to delete"));

            } else {
                getModule().removeSesison(sessions[selectedRow]);

                tableModel.removeRow(selectedRow);

                // Need to update session???
            }
        });

        pagePanel.add(optionsPanel);

        pagePanel.setLayout(new BoxLayout(pagePanel, BoxLayout.Y_AXIS));

        return pagePanel;
    }

    /**
     * @return Module - Instance of dropDown's selected module
     */
    private Module getModule() {
        Module module = null;

        String moduleName = (String)dropDown.getSelectedItem();

        System.out.println("Getting Module"); //TODO: Delete
        System.out.println("Name: " + moduleName);

        if (moduleName == null) {
            //TODO: Needs a check if drop down empty (no modules)

            System.out.println(state.getModules().length);

            moduleName = dropDown.getItemAt(0).toString();;
        }

        for (Module m : state.getModules()) {
            if (moduleName.equals(m.getName())) {
                module = m;
                break;
            }
        }
        if (module == null) {
            System.err.println("Couldn't find a module with the name: " + moduleName);
        }

        return module;
    }

    /**
     * Populates a table will all the sessions from the currently selected module
     */
    private void populateTable() {
        sessions = getModule().getStudySessions();

        // Populating the table with the modules sessions
        for (StudySession session : sessions) {
            String date = session.date.toString();
            String duration = session.duration.toMinutes() + " minutes";

            tableModel.addRow(new String[]{date, duration});
        }
    }

    private JScrollPane createTable() {
        String[] columns = new String[]{"Date", "Duration"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { // Prevents cells from being editable
                return false;
            }
        };

        populateTable();

        table = new JTable(tableModel);

        table.setRowHeight(30);
        table.setFillsViewportHeight(true); // Table uses entire height of the scroll pane
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Prevents user from selecting > 1 row

        return new JScrollPane(table);
    }

    public BackButton getBackButton() {
        return backButton;
    }
}
