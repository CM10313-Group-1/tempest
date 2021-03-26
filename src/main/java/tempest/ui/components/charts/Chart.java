package tempest.ui.components.charts;

import org.jfree.chart.JFreeChart;

import tempest.ui.View;

/**
 * Generic chart class.
 */
public abstract class Chart extends View {
  private static final long serialVersionUID = -2648755806373669402L;

  public abstract JFreeChart getChart();
}
