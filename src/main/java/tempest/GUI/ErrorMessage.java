package tempest.GUI;

import javax.swing.*;

public class ErrorMessage{
    private static boolean messagesShown = true;

    public ErrorMessage(){}

    public void showMessage(JPanel parent, String message){
        if(messagesShown){
            JOptionPane.showMessageDialog(parent, message);
        }
    }

    public void setMessagesShown(boolean show){
        messagesShown = show;
    }
}
