package Logic.SimulationLogic.PropagationModelUtils;

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
    private Map<Double,List<Double>> data;

    public Chart_Report(){
        data = new TreeMap<>();
    }


    public void addData(Double threshold, List<Double> downedNodesFrequency) {
        data.put(threshold,downedNodesFrequency);
    }

    public boolean buildBoxPlot(){
        BoxAndWhiskerCategoryDataset dataset = createDataset();

        JFreeChart chart = ChartFactory.createBoxAndWhiskerChart(
                "cascades distribution",
                "thresholds",
                "downed nodes frecuencies",
                dataset,
                true);

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
            relativePath += fileSeparator + "propagation_model.PNG";
            file = new File(relativePath);
            //creates the file
            ChartUtils.saveChartAsPNG(file,chart,800,300);
        }
        catch (IOException e){
            return false;
        }

        return true;
    }

    private BoxAndWhiskerCategoryDataset createDataset() {
        BoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();

        DecimalFormat thresholdFormat = new DecimalFormat("#0.00");
        data.forEach((k,v) -> ((DefaultBoxAndWhiskerCategoryDataset) dataset)
                .add(v,thresholdFormat.format(k),"threshold"));

        return dataset;
    }
}
