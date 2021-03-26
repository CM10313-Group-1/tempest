package tempest.ui.components;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class LinkButton extends JButton {
  private static final long serialVersionUID = 7864238L;
  private String destination;

  /**
   * Creates a button to allow linking between pages.
   * 
   * @param text        The text displayed to the user.
   * @param destination The destination page the link goes to.
   * @param l           Listener to detect when the link is used.
   */
  public LinkButton(String text, String destination, ActionListener l) {
    super(text);
    this.setDestination(destination);
    this.setFocusable(false);
    this.addActionListener(l);
  }

  /**
   * Gets the destination of the link.
   */
  public String getDestination() {
    return destination;
  }

  /**
   * Sets the destination of the link.
   */
  private void setDestination(String destination) {
    this.destination = destination;
  }
}
