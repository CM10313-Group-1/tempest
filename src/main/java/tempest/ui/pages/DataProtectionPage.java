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
        text = "<ul><li>All data collected is stored in a CSV file called store.csv\n" +
                "<li> Any data inputted by the user from within the program will be stored within this file\n" +
                "<li>The data is local to each user therefore can only be visible/accessed by the user\n" +
                "<li>Altering the contents of this file may affect the program and cause errors <ul>";
        text = "<html><font size='2'>" + text + "</font></html>";
        JLabel textLabel = new JLabel();
        textLabel.setText(text);
        backButton = new BackButton(manager);
        backPanel.add(backButton);
        textPanel.add(textLabel);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(textPanel);
        this.add(backPanel);
    }
    public BackButton getBackButton() {
        return backButton;
    }
}
