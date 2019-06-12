package Logic.SimulationLogic.PropagationModelUtils;

import javafx.util.Pair;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

public class ReportBuilder {

    private List<Double> availableNodesFrequency;
    private List<Double> downedNodesFrequency;
    private String reportId;
    private Double threshold;

    public ReportBuilder(Double threshold){
        DecimalFormat thresholdFormat = new DecimalFormat("#0.00");
        availableNodesFrequency = new ArrayList<>();
        downedNodesFrequency = new ArrayList<>();
        this.reportId = thresholdFormat.format(threshold);
        this.threshold = threshold;
    }

    public void addData(Double availableNodes, Double downedNodes){
        availableNodesFrequency.add(availableNodes);
        downedNodesFrequency.add(downedNodes);
    }

    public boolean buildReport(Chart_Report chart){
        //key = availableNodes, value = downedNodes
        Pair<Double,Double> averages = calculateAverages();
        Pair<Double,Double> variances = calculateVariance(averages.getKey(),averages.getValue());
        Pair<Double,Double> typicalDeviations = calculateTypicalDeviation(variances.getKey(),variances.getValue());
        Report report = new Report(averages, variances, typicalDeviations,reportId);
        chart.addData(threshold,downedNodesFrequency);
        return report.writeReport();
    }


    private Pair<Double,Double> calculateAverages(){
        return new Pair<>(
                availableNodesFrequency.stream()
                        .mapToDouble(Double::doubleValue)
                        .average()
                        .getAsDouble(),

                downedNodesFrequency.stream()
                        .mapToDouble(Double::doubleValue)
                        .average()
                        .getAsDouble() );
    }
/*
    private Pair<Double,Double> calculateMedians(DoubleStream sortedAvailableNodes, DoubleStream sortedDownedNodes) {

        double availableNodesMedian;
        if(availableNodesFrequency.size()%2 == 0)
                availableNodesMedian = sortedAvailableNodes.skip(availableNodesFrequency.size()/2-1)
                        .limit(2).average().getAsDouble();
        else
            availableNodesMedian = sortedDownedNodes.skip(availableNodesFrequency.size()/2).findFirst().getAsDouble();

        double downedNodesMedian;
        if(downedNodesFrequency.size()%2 == 0)
                downedNodesMedian = sortedAvailableNodes.skip(downedNodesFrequency.size()/2-1)
                        .limit(2).average().getAsDouble();
        else
            downedNodesMedian = sortedDownedNodes.skip(downedNodesFrequency.size()/2).findFirst().getAsDouble();

        return new Pair<>(availableNodesMedian,downedNodesMedian);
    }
*/
    private Pair<Double,Double> calculateVariance(Double availableAverage, Double downedAverage) {
        return new Pair<>(
                (availableNodesFrequency.stream()
                        .mapToDouble(v -> Math.pow(v,2.0)).sum() / availableNodesFrequency.size())
                        - Math.pow(availableAverage,2.0),
                (downedNodesFrequency.stream()
                        .mapToDouble(v -> Math.pow(v,2.0)).sum() / downedNodesFrequency.size())
                        - Math.pow(downedAverage,2.0)
        );
    }

    private Pair<Double,Double> calculateTypicalDeviation(Double availableVariance, Double downedVariance){
        return new Pair<>(
                Math.sqrt(availableVariance),
                Math.sqrt(downedVariance)
        );
    }

   /* private List<Pair<Double,Double>> calculateDataForBoxWhiskerChart(){
        List<Pair<Double,Double>> data = new ArrayList<>();
        DoubleStream sortedAvailableNodes = availableNodesFrequency.stream().mapToDouble(Double::doubleValue).sorted();
        DoubleStream sortedDownedNodes = downedNodesFrequency.stream().mapToDouble(Double::doubleValue).sorted();
        data.add(calculateMedians(sortedAvailableNodes,sortedDownedNodes));
        data.add(calculate)
    }*/
}

