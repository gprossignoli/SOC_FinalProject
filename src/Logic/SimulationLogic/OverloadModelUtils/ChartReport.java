package Logic.SimulationLogic.OverloadModelUtils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChartReport {
    private List<Integer> downedNodesPerIterationModeA;
    private List<Integer> downedNodesPerIterationModeB;

    public ChartReport(){
        downedNodesPerIterationModeA = new ArrayList<>();
        downedNodesPerIterationModeB = new ArrayList<>();
    }


    public boolean addData(Integer downedNodes,String mode){
        if(mode.equals("A"))
            this.downedNodesPerIterationModeA.add(downedNodes);
        else if(mode.equals("B"))
            this.downedNodesPerIterationModeB.add(downedNodes);
        else
            return false;

        return true;
    }

    public boolean buildChart(){
        XYSeries serieA = new XYSeries("Mode A");
        XYSeries serieB = new XYSeries("Mode B");
        for (int i = 0; i < downedNodesPerIterationModeA.size();i++){
            serieA.add(i,downedNodesPerIterationModeA.get(i));
        }
        for (int j = 0; j < downedNodesPerIterationModeB.size();j++){
            serieB.add(j,downedNodesPerIterationModeB.get(j));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(serieA);
        dataset.addSeries(serieB);
        NumberAxis xAxis =  new NumberAxis("iteration");
        NumberAxis yAxis = new NumberAxis("downed nodes");
        XYSplineRenderer r = new XYSplineRenderer(3);
        XYPlot xyPlot = new XYPlot(dataset,xAxis,yAxis,r);
        JFreeChart chart = new JFreeChart(xyPlot);

        try{
            String fileSeparator = System.getProperty("file.separator");
            String relativePath = "./results";
            File file = new File(relativePath);
            if(!file.exists())
                if(!file.mkdir())
                    return false;
            relativePath += fileSeparator + "overloadModel";
            file = new File(relativePath);
            if(!file.exists())
                if(!file.mkdir())
                    return false;
            relativePath += fileSeparator + "overload_model_Chart.PNG";
            file = new File(relativePath);
            //creates the file
            ChartUtils.saveChartAsPNG(file,chart,400,400);
        }
        catch (IOException e){
            return false;
        }

        return true;
    }
}
