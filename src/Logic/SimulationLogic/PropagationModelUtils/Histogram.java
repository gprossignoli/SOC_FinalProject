package Logic.SimulationLogic.PropagationModelUtils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.xy.XYBarDataset;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Histogram {
    private List<Double> downedNodesFrequency;
    private List<Double> thresholds;

    public Histogram(){
        downedNodesFrequency = new ArrayList<>();
        thresholds = new ArrayList<>();
    }

    public void addData(Double frequency, Double threshold){
        downedNodesFrequency.add(frequency);
        thresholds.add(threshold);
    };

    private double[] prepareAxis(List<Double> list){
        int lenght = list.size();
        double[] ret = new double[lenght];
        for(int i = 0; i < lenght; i++){
            ret[i] = list.get(i);
        }

        return ret;
    }


    public boolean buildHistogram(){
        XYBarDataset dataset = new XYBarDataset();
        dataset.s
        double[] frequencies = prepareAxis(downedNodesFrequency);
        double[] classes = prepareAxis(thresholds);
        dataset.addSeries("Cascade Distribution",frequencies,classes);
        dataset.
        String plotTitle = "Cascade Distribution";
        String xAxis = "failure threshold";
        String yAxis = "Downed nodes frequency";
        PlotOrientation orientation = PlotOrientation.VERTICAL;
        boolean show = false;
        boolean toolTips = false;
        boolean urls = false;

        JFreeChart chart = ChartFactory.createHistogram(plotTitle,xAxis,yAxis,dataset,orientation,
                show,toolTips,urls);
        int width = 500;
        int height = 300;
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
            relativePath += fileSeparator + "propagation_model_Histogram.JPEG";
            file = new File(relativePath);
            //creates the file
            ChartUtils.saveChartAsJPEG(file,chart,width,height);
        }
        catch (IOException e){
            return false;
        }

        return true;
    }
}
