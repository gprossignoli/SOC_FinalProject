package Logic.SimulationLogic.PropagationModelUtils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Histogram {
    private double[] data;
    private int indexOfData;

    public Histogram(int size){
        data = new double[size];
        indexOfData = 0;
    }

    public void addData(Double r){
        if(indexOfData > data.length)
            throw new ArrayIndexOutOfBoundsException("Error en reportBuilder, demasiados datos?");
        data[indexOfData] = r;
        indexOfData++;
    };

    public boolean buildHistogram(){
        HistogramDataset dataset = new HistogramDataset();
        dataset.setType(HistogramType.FREQUENCY);
        dataset.addSeries("Cascade Distribution",data,data.length);
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
