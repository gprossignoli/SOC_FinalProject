package Logic.SimulationLogic.PropagationModelUtils;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;

public class Report {
    private String id;
    private String availableAverage;
    private String availableVariance;
    private String availableTypicalDeviation;
    private String downedAverage;
    private String downedVariance;
    private String downedTypicalDeviation;

    public Report(Pair<Double,Double> averages,
                  Pair<Double,Double> variances,
                  Pair<Double,Double> typicalDeviations,
                  String id){
        this.id = id;
        DecimalFormat Format = new DecimalFormat("#0.00");
        availableAverage = Format.format(averages.getKey());
        downedAverage = Format.format(averages.getValue());
        availableVariance = Format.format(variances.getKey());
        downedVariance = Format.format(variances.getValue());
        availableTypicalDeviation = Format.format(typicalDeviations.getKey());
        downedTypicalDeviation = Format.format(typicalDeviations.getValue());
    }

    public boolean writeReport(){
            String fileSeparator = System.getProperty("file.separator");
            String relativePath = "results" + fileSeparator + "propagation_model-"+ id +".txt";
            File file = new File(relativePath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            return false;
        }
        Path path = Paths.get(relativePath);
        try(BufferedWriter bw = Files.newBufferedWriter(path)) {
            bw.write(this.toString());
        } catch (IOException e) {
            System.out.println("It was problems trying to write in " + id + ".txt");
            return false;
        }

        return true;
    }

    public String toString(){
        return "The simulation has the following results: \n" +
                "--Available nodes: \n" +
                "   Average: " + availableAverage + "\n" +
                "   Variance: " + availableVariance + "\n" +
                "   Typical Deviation: " + availableTypicalDeviation +"\n"+
                "--Downed nodes: \n" +
                "   Average: " + downedAverage + "\n" +
                "   Variance: " + downedVariance + "\n" +
                "   Typical Deviation: " + downedTypicalDeviation;
    }

}
