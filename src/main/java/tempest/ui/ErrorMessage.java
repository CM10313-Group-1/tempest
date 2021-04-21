package tempest.ui;

import tempest.ui.pages.DeleteModulePage;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ErrorMessage {
    private static boolean messagesShown = true;

    public void showMessage(JPanel parent, Exception exception) {
        String message = exception.getMessage();

        if (messagesShown) {
            JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            System.err.println(message);
        }
    }

    public void setMessagesShown(boolean show) {
        messagesShown = show;
    }

    public int showWarningMessage(JPanel parent, String message) {
        if (messagesShown) {
            return JOptionPane.showConfirmDialog(parent, message, "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        } else {
            System.err.println(message);
        }

        return -2;
    }
}
