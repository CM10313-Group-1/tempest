package tempest.ui.pages;

import org.junit.Before;
import tempest.ui.ErrorMessage;

public class DeleteSessionPageTest {

    // Test deleting a session

    // Test deleting last session moves user back a page

    // Test changing the drop fills table with correct sessions???

    // A newly added sessions appears in the table???

    @Before
    public void turnOffErrorMessages() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessagesShown(false);
    }



}