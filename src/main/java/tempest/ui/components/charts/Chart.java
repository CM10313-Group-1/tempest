package tempest.ui.components.charts;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import tempest.ui.View;

/**
 * Generic chart class.
 */
public abstract class Chart extends View {
  public abstract JFreeChart getChart();

  @Override
  public JPanel getPanel() {
    return new ChartPanel(getChart());
  }
}
