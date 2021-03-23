package tempest.ui;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ErrorMessage{
    private static boolean messagesShown = true;

    public ErrorMessage(){}

    public void showMessage(JPanel parent, String message){
        if(messagesShown){
            JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setMessagesShown(boolean show){
        messagesShown = show;
    }
}
