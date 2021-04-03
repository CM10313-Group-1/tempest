package tempest.ui.components;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import tempest.Module;

public class ModuleControls extends JPanel implements ActionListener {
  private final ChartControls parent;
  private static final long serialVersionUID = 8860049310284130378L;
  public Color color;
  private String moduleName;
  private JButton colorPicker;
  private JToggleButton toggle;

  public ModuleControls(ChartControls chartControls, Module module) {
    this.parent = chartControls;
    this.moduleName = module.getName();
    Random rand = new Random(module.hash());
    this.color = generateRandomColor(rand);

    setupUI();
  }

  private void setupUI() {
    JLabel label = new JLabel(moduleName);
    toggle = new JCheckBox("Included");
    colorPicker = new JButton("C");
    colorPicker.addActionListener(this);
    colorPicker.setBackground(color);

    add(label);
    add(toggle);
    add(colorPicker);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource().equals(colorPicker))
      updateColor();
  }

  private void updateColor() {
    Color newColor = JColorChooser.showDialog(this.parent, moduleName, color);
    if (newColor != null) {
      color = newColor;
      colorPicker.setBackground(color);
    }
  }

  private Color generateRandomColor(Random rand) {
    float r = rand.nextFloat();
    float g = rand.nextFloat();
    float b = rand.nextFloat();
    return new Color(r, g, b);
  }

  public boolean isEnabled() {
    return toggle.isSelected();
  }
}