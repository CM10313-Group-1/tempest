package tempest.GUI;

import tempest.GUI.components.ModuleDropDown;
import tempest.Module;
import tempest.State;
import tempest.StudySession;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class DeleteSessionsPage extends Page {

    private final State state;
    private final GUIManager manager;

    private final ModuleDropDown moduleDropDown = new ModuleDropDown();

    private JPanel deletePanel;
    private JComboBox<Object> dropDown;

    public DeleteSessionsPage(State state, GUIManager manager) {
        this.state = state;
        this.manager = manager;
    }

    @Override
    public String getName() {
        return "deleteSessionsPage";
    }

    @Override
    public JPanel getPanel() {
        deletePanel = new JPanel();

        // Drop down -> w/ "all" options as well???
        dropDown = moduleDropDown.getModuleDropDown();
        deletePanel.add(dropDown);

        // Relevant sessions shown in a JTable
        String moduleName = Objects.requireNonNull(dropDown.getSelectedItem()).toString();

        Module module = new Module("module");

        for (Module m : state.getModules()) {
            if (moduleName.equals(m.getName())) {
                module = m;
                createTable(deletePanel, m);
                break;
            }
        }

        return deletePanel;
    }

    private void createTable(JPanel deletePanel, Module m) {
        ArrayList<String> date = new ArrayList<>();
        ArrayList<String> duration = new ArrayList<>();

        StudySession[] sessions = m.getStudySessions();

        String[][] data = new String[sessions.length][1];

        for (int i = 0; i < sessions.length; i++) {
            data[i][0] = sessions[i].date.toString();
            data[i][1] = sessions[i].duration.toString();
        }

        String[] columns = new String[]{"Date", "Duration"};

        JTable table = new JTable(data, columns);
        deletePanel.add(table);

        // Delete button below the table
        JButton deleteButton = new JButton("Delete Session");
        deletePanel.add(deleteButton);

        // Once pressed the session in the selected row is deleted - by calling module.removeSession(session)
        deleteButton.addActionListener(e -> m.removeSesison(sessions[table.getSelectedRow()]));
    }
}
