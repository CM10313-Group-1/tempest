package tempest.ui.pages;

import tempest.State;
import tempest.ui.GUIManager;
import tempest.ui.components.BackButton;

import javax.swing.*;

public class DataProtectionPage extends Page {
    private BackButton backButton;
    static String text;
    public DataProtectionPage(State state, GUIManager manager) {
        super(manager);
        setupUI();
    }

    @Override
    public String getName() {
        return PageNames.DATA_PROTECTION;
    }
    private void setupUI(){
        JPanel backPanel = new JPanel();
        JPanel textPanel = new JPanel();
        text = "- All data collected is stored in a CSV file called store.csv\n" +
                "- Any data inputted by the user from within the program will be stored within this file\n" +
                "- The data is local to each user therefore can only be visible/accessed by the user\n" +
                "- Altering the contents of this file may affect the program and cause errors ";
        String formatted = text.replace("\n", "<br>");
        formatted = "<html><font size='3'>" + formatted + "</font></html>";
        JLabel textLabel = new JLabel();
        textLabel.setText(formatted);
        backButton = new BackButton(manager);
        backPanel.add(backButton);
        textPanel.add(textLabel);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(textPanel);
        this.add(backPanel);
    }
}
