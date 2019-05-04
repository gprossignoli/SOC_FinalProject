package Logic.SimulationLogic.OverloadModelUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Report {
    private List<Integer> downedNodesPerIteration;

    public Report(){
        this.downedNodesPerIteration = new ArrayList<>();
    }

    public void addData(Integer downedNodes){this.downedNodesPerIteration.add(downedNodes);}

    public String toString(){
        String out = "";
        for(int i = 0; i < downedNodesPerIteration.size();i++){
            out += "Simulation step: " + i + " downed nodes: " + downedNodesPerIteration.get(i) + "\n";
        }
        return out;
    }

    public boolean writeReport(){


        String fileSeparator = System.getProperty("file.separator");
        String relativePath = "results" + fileSeparator + "overload_model.txt";
        File file = new File(relativePath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            return false;
        }
        Path path = Paths.get(relativePath);
        try(BufferedWriter bw = Files.newBufferedWriter(path)) {
            if(downedNodesPerIteration.isEmpty())
                bw.write("No downed nodes, try with different input parameters");
            else
                bw.write(this.toString());
        } catch (IOException e) {
            System.out.println("It was problems trying to write in overload_model.txt");
            return false;
        }

        return true;
    }


}
