package tempest.ui.components;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;

import tempest.Module;

public class ModuleControl implements ActionListener {
  private final ChartControls parent;
  private Module module;
  public JLabel label;
  public JButton colorPicker;

  public ModuleControl(ChartControls chartControls, Module module) {
    this.parent = chartControls;
    this.module = module;
    this.label = new JLabel(module.getName());
    this.colorPicker = new JButton();
    colorPicker.addActionListener(this);
    colorPicker.setBackground(module.getColor());
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource().equals(colorPicker))
      openPicker();
  }

  private void openPicker() {
    Color newColor = JColorChooser.showDialog(this.parent, module.getName(), module.getColor());
    setColor(newColor);
  }

  public void resetToDefaultColor() {
    module.resetToDefaultColor();
    colorPicker.setBackground(module.getDefaultColor());
  }

  private void setColor(Color newColor) {
    if (newColor != null) {
      module.setColor(newColor);
      colorPicker.setBackground(newColor);
    }
  }
}