package tempest.ui.pages;

import tempest.State;
import tempest.ui.GUIManager;
import tempest.ui.components.ClearButton;
import tempest.ui.components.EnterButton;

import java.awt.event.ActionListener;

/**
 * Pages should extend this abstract class if they want to use a
 * clear button and have an enter button
 */
public abstract class InputPage extends Page implements ActionListener {

    protected State state;
    protected EnterButton enterButton;
    protected ClearButton clearButton;

    public InputPage(GUIManager manager, State state) {
        super(manager);
        this.state = state;

        enterButton = new EnterButton(this);
        clearButton = new ClearButton(this);
    }

    /**
     * Used to clear input fields when the clear button is pressed
     */
    public abstract void clearInput();

    public EnterButton getEnterButton() {
        return enterButton;
    }
}
