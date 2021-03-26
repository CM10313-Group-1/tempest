package tempest.ui.components.charts;

import org.jfree.chart.JFreeChart;

import tempest.ui.View;

/**
 * Generic chart class.
 */
public abstract class Chart extends View {
  public abstract JFreeChart getChart();
}
