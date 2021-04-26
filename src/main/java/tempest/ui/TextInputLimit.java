package tempest.ui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

//https://www.tutorialspoint.com/how-can-we-limit-the-number-of-characters-inside-a-jtextfield-in-java
public class TextInputLimit extends PlainDocument {
    private final int limit;

    public TextInputLimit (int limit) {
        super();
        this.limit = limit;
    }

    /**
     * Prevents the user from entering a string longer than limit
     */
    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null) {
            return;
        }

        if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str, attr);
        }
    }
}