package Logic.SimulationLogic.PropagationModelUtils;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Report {
    private String id;
    private Double availableAverage;
    private Double availableVariance;
    private Double availableTypicalDeviation;
    private Double downedAverage;
    private Double downedVariance;
    private Double downedTypicalDeviation;

    public Report(Pair<Double,Double> averages,
                  Pair<Double,Double> variances,
                  Pair<Double,Double> typicalDeviations,
                  String id){
        this.id = id;
        availableAverage = averages.getKey();
        downedAverage = averages.getValue();
        availableVariance = variances.getKey();
        downedVariance = variances.getValue();
        availableTypicalDeviation = typicalDeviations.getKey();
        downedTypicalDeviation = typicalDeviations.getValue();
    }

    public boolean writeReport(){
            String fileSeparator = System.getProperty("file.separator");
            String relativePath = "results" + fileSeparator + "propagation_model"+
                    fileSeparator+id+".txt";
            File file = new File(relativePath);
        try {
            if(!file.createNewFile()){
                throw new IOException("The results file " + id + ".txt " + "wasn't created");
            }
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
