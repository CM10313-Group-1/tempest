package tempest.ui.pages;

import tempest.ui.GUIManager;

import javax.swing.*;

public class DataProtectionPage extends Page {
    static String text;

    public DataProtectionPage(GUIManager manager) {
        super(manager);
        setupUI();
    }

    @Override
    public String getName() {
        return PageNames.DATA_PROTECTION;
    }

    private void setupUI(){
        JPanel textPanel = new JPanel();

        text = "<ul><h3><center>Data Protection Information</center></h3>" +
                "<li>All data collected (not including goals) is stored in a CSV file called store.csv</li>"+
                "<li> Any data inputted by the user from within the program will be stored within this file</li>" +
                "<li>The data is local to each user therefore can only be visible/accessed by the user</li>" +
                "<li>Altering the contents of the csv file may affect the program and cause errors</li>" +
                "<li>Deleting the csv file or changing the location of the csv file may also affect the program</li>" +
                "<li> Goals will be stored using serialisation, meaning they will be stored within the program itself</li>" +
                "<li> This means that goals can not be accessed or be visible outside of the program</li></ul>" ;

        text = "<html><font size='2'>" + text + "</font></html>";

        JLabel textLabel = new JLabel();
        textLabel.setText(text);
        textPanel.add(textLabel);

        this.add(textPanel);
        this.add(backPanel);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
}
