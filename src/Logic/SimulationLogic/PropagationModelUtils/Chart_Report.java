package Logic.SimulationLogic.PropagationModelUtils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.xy.XYBarDataset;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Chart_Report {
    private List<Integer> downedNodesFrequency;
    private List<Double> thresholds;
    private int initialAmountAffectedNodes;
    public Chart_Report(int initialAmountAffectedNodes){
        downedNodesFrequency = new ArrayList<>();
        thresholds = new ArrayList<>();
        this.initialAmountAffectedNodes = initialAmountAffectedNodes;
    }

    public void addFrequency(Integer frequency){
        downedNodesFrequency.add(frequency - initialAmountAffectedNodes);
    }
    public void addThreshold(Double threshold){
        thresholds.add(threshold);
    }

    private double[] prepareAxis(List<Double> list){
        int lenght = list.size();
        double[] ret = new double[lenght];
        for(int i = 0; i < lenght; i++){
            ret[i] = list.get(i);
        }

        return ret;
    }


    public boolean buildHistogram(){
        CategoryDataset dataset = createDataset();

        JFreeChart chart = ChartFactory.createBarChart(
                "Cascades Distribution",
                "threshold",
                "downed nodes frequency",
                dataset,
                PlotOrientation.VERTICAL,
                false,false,false);

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
            relativePath += fileSeparator + "propagation_model_Histogram.PNG";
            file = new File(relativePath);
            //creates the file
            ChartUtils.saveChartAsPNG(file,chart,800,300);
        }
        catch (IOException e){
            return false;
        }

        return true;
    }

    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        DecimalFormat thresholdFormat = new DecimalFormat("#0.00");
        for(int i = 0; i < thresholds.size(); i++){
            dataset.addValue(downedNodesFrequency.get(i),"",thresholdFormat.format(thresholds.get(i)));
        }

        return dataset;
    }
}
