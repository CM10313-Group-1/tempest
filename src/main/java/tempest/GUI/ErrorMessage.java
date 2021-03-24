package tempest.GUI;

import javax.swing.*;

public class ErrorMessage{
    private static boolean messagesShown = true;

    public void showMessage(JPanel parent, Exception message){

        String messageStr = message.getMessage();

        if (messagesShown) {
            JOptionPane.showMessageDialog(parent, messageStr, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            System.err.println(messageStr);
        }
    }

    public void setMessagesShown(boolean show){
        messagesShown = show;
    }
}
