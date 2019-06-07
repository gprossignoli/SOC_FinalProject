package Logic.SimulationLogic.OverloadModelUtils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XYChart_Report {
    private List<Integer> downedNodesPerIteration;

    public XYChart_Report(){downedNodesPerIteration = new ArrayList<>();}


    public void addData(Integer downedNodes){this.downedNodesPerIteration.add(downedNodes);}

    public boolean buildChart(String model){
        XYSeries series = new XYSeries("Cascade distribution");
        for(int i = 0; i < downedNodesPerIteration.size(); i++){
            series.add(i,downedNodesPerIteration.get(i));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        JFreeChart xyChart = ChartFactory.createXYLineChart(
                "Cascade Distribution",
                "simulation step",
                "downed nodes frequency",
                dataset,
                PlotOrientation.VERTICAL,
                false,false,false
                );
        try{
            String fileSeparator = System.getProperty("file.separator");
            String relativePath = "results";
            File file = new File(relativePath);
            if(!file.exists())
                if(!file.mkdir())
                    return false;
            relativePath += fileSeparator + "overloadModel";
            file = new File(relativePath);
            if(!file.exists())
                if(!file.mkdir())
                    return false;
            relativePath += fileSeparator + "overload_model_XYChart_"+model+".PNG";
            file = new File(relativePath);
            //creates the file
            ChartUtils.saveChartAsPNG(file,xyChart,640,480);
        }
        catch (IOException e){
            return false;
        }

        return true;
    }
}
