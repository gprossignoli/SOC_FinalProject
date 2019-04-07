package Logic.SimulationLogic.PropagationModelUtils;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class ReportBuilder {

    private List<Integer> availableNodesFrequency;
    private List<Integer> downedNodesFrequency;
    private Double availableNodesAverage;
    private Double downedNodesAverage;

    public ReportBuilder(){
        availableNodesFrequency = new ArrayList<>();
        downedNodesFrequency = new ArrayList<>();
        availableNodesAverage = 0.0;
        downedNodesAverage = 0.0;
    }

    public void addData(Integer availableNodes, Integer downedNodes){
        availableNodesFrequency.add(availableNodes);
        downedNodesFrequency.add(downedNodes);
    }

    public void buildReport(){
        Pair<Double,Double> averages = calculateAverages();
        Pair<Double,Double> variances = calculateVariance(averages.getKey(),averages.getValue());
        Pair<Double,Double> typicalDeviations = calculateTypicalDeviation(variances.getKey(),variances.getValue());
        Report report = new Report(averages, variances, typicalDeviations);
        report.writeReport();
    }

    private Pair<Double,Double> calculateAverages(){
        return new Pair<>(
                availableNodesFrequency.stream()
                        .mapToDouble(Integer::intValue)
                        .average()
                        .getAsDouble(),

                downedNodesFrequency.stream()
                        .mapToDouble(Integer::intValue)
                        .average()
                        .getAsDouble() );
    }

    private Pair<Double,Double> calculateVariance(Double availableAverage, Double downedAverage) {
        return new Pair<>(
                (availableNodesFrequency.stream()
                        .mapToDouble(v -> Math.pow(v,2.0)).sum()) - Math.pow(availableAverage,2.0),
                (downedNodesFrequency.stream()
                        .mapToDouble(v -> Math.pow(v,2.0)).sum()) - Math.pow(downedAverage,2.0)
        );
    }

    private Pair<Double,Double> calculateTypicalDeviation(Double availableVariance, Double downedVariance){
        return new Pair<>(
                Math.sqrt(availableVariance),
                Math.sqrt(downedVariance)
        );
    }
}

