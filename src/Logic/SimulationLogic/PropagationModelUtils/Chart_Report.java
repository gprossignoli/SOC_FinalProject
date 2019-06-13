package Logic.SimulationLogic.PropagationModelUtils;

import javafx.util.Pair;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.*;
import org.jfree.data.xy.XYBarDataset;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class Chart_Report {
    private Map<Double, Pair<List<Integer>,Double>> data;
    private Integer totalNodesInNetwork;
    public Chart_Report(Integer totalNodesInNetwork){
        data = new TreeMap<>();
        this.totalNodesInNetwork = totalNodesInNetwork;
    }

    // Pair -> key = all the downedData for each threshold, value -> average of the key values
    public void addData(Double threshold, Pair<List<Integer>,Double> downedNodes) {
        data.put(threshold,downedNodes);
    }

    public boolean buildBoxPlot(){
        BoxAndWhiskerCategoryDataset dataset =
                (BoxAndWhiskerCategoryDataset) createDataset("BoxPlot");

        JFreeChart chart = ChartFactory.createBoxAndWhiskerChart(
                "cascades distribution",
                "thresholds",
                "downed nodes frecuencies",
                dataset,
                true);

        return createFile("propagation_model_BoxPlot.PNG",chart);
    }

    public boolean buildBarChart(){
        CategoryDataset dataset = createDataset("BarChart");

        JFreeChart chart = ChartFactory.createBarChart(
                "Cascades Distribution",
                "thresholds",
                "downed nodes frequencies",
                dataset,
                PlotOrientation.VERTICAL,
                false,false,false);

        return createFile("propagation_model_BarChart.PNG",chart);
    }
    private CategoryDataset createDataset(String chartType) {
        CategoryDataset ret = null;
        if(chartType.equals("BoxPlot")) {
           BoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();

            DecimalFormat thresholdFormat = new DecimalFormat("#0.00");
            data.forEach((k, v) -> ((DefaultBoxAndWhiskerCategoryDataset) dataset)
                    .add(v.getKey(), thresholdFormat.format(k), "threshold"));

            ret = dataset;
        }

        else if(chartType.equals("BarChart")){
            CategoryDataset dataset = new DefaultCategoryDataset();

            DecimalFormat thresholdFormat = new DecimalFormat("#0.00");
            data.forEach((k,v) -> {
                //calculate the relative frequency and adds it to the chart using the average stored as value
                // in the pair of this.data
                final Double downedNodesFrequency = v.getValue()/totalNodesInNetwork.doubleValue();
                ((DefaultCategoryDataset) dataset).addValue(downedNodesFrequency,"",thresholdFormat.format(k));
            });

            ret = dataset;
        }

        return ret;
    }

    private boolean createFile(String name, JFreeChart chart){
        try{
            String fileSeparator = System.getProperty("file.separator");
            String relativePath = "results";
            File file = new File(relativePath);
            if(!file.exists())
                if(!file.mkdir())
                    return false;
            relativePath += fileSeparator + "propagationModel";
            file = new File(relativePath);
            if(!file.exists())
                if(!file.mkdir())
                    return false;
            relativePath += fileSeparator + name;
            file = new File(relativePath);
            //creates the file
            ChartUtils.saveChartAsPNG(file,chart,800,300);
        }
        catch (IOException e){
            return false;
        }

        return true;
    }

}
