package tempest.ui.components.charts;

import javax.swing.JLabel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import tempest.State;
import tempest.ui.ViewManager;

public class LineChart extends Chart {
  private static final long serialVersionUID = -1275171253819439097L;

  public LineChart(State state, ViewManager<Chart> manager) {
    super(state, manager);
    this.add(new JLabel(getName()));
    this.add(new ChartPanel(getChart()));
  }

  @Override
  public JFreeChart getChart() {

    JFreeChart chart = ChartFactory.createLineChart("Line Chart", "Modules", "Cumulative Time / mins",
        generateDataset(this.state), PlotOrientation.HORIZONTAL, true, false, false);
    return chart;
  }

  @Override
  public CategoryDataset generateDataset(State state) {
    final String fiat = "FIAT";
    final String audi = "AUDI";
    final String ford = "FORD";
    final String speed = "Speed";
    final String mileage = "Mileage";
    final String userRating = "User Rating";
    final String safety = "safety";
    final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    dataset.addValue(1.0, fiat, speed);
    dataset.addValue(3.0, fiat, userRating);
    dataset.addValue(5.0, fiat, mileage);
    dataset.addValue(5.0, fiat, safety);

    dataset.addValue(5.0, audi, speed);
    dataset.addValue(6.0, audi, userRating);
    dataset.addValue(10.0, audi, mileage);
    dataset.addValue(4.0, audi, safety);

    dataset.addValue(4.0, ford, speed);
    dataset.addValue(2.0, ford, userRating);
    dataset.addValue(3.0, ford, mileage);
    dataset.addValue(6.0, ford, safety);

    return dataset;
  }

  @Override
  public String getName() {
    return ChartTypes.LINE;
  }

}
