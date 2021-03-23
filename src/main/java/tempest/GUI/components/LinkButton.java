package tempest.GUI.components;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class LinkButton extends JButton {
  private static final long serialVersionUID = 7864238L;
  private String destination;

  public LinkButton(String text, String destination, ActionListener l) {
    super(text);
    this.setDestination(destination);

    this.setFocusable(false);
    this.addActionListener(l);
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }
}
