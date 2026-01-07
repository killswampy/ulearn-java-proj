import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import java.util.Map;

public class Visualisation {

    public static void barChartByMap(Map<String, Integer> data, String title, String categoryLabel, String valueLabel) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            dataset.addValue(entry.getValue(), valueLabel, entry.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                title,
                categoryLabel,
                valueLabel,
                dataset,
                PlotOrientation.VERTICAL,
                false,
                false,
                false
        );

        ChartFrame frame = new ChartFrame(title, chart);
        frame.setSize(1000, 600);
        frame.setVisible(true);
    }
}
