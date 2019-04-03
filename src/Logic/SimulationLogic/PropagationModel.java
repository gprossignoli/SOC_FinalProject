package Logic.SimulationLogic;

import Logic.Airport;

import java.util.List;
import java.util.Random;

public class PropagationModel extends Simulation {

    private Float threshold;
    private Integer initialAmountOfAffectedNodes;
	public PropagationModel(List<Object> simData) {
	    super((Integer) simData.get(0),(List) simData.get(1));
        this.threshold = (Float) simData.get(2);
        this.initialAmountOfAffectedNodes = (Integer) simData.get(3);
	}
	
	public void executeSimulation(){
        Random random = new Random();
        int time = 0;
        int nodesDowned = 0;

        //At time 0 fails a small number of nodes picked randomly
        for(int i = 0; i < initialAmountOfAffectedNodes; ++i){
            airports.get(random.nextInt(airports.size())).setLock(true);
        }

        nodesDowned = initialAmountOfAffectedNodes;
        ++time;
        Airport a;
        while(time <= steps && nodesDowned < airports.size()){
            a = airports.get(random.nextInt(airports.size()));
            while(a.isLocked()){a = airports.get(random.nextInt(airports.size()));}

            checkStatus(a);
        }
	}

    private boolean checkStatus(Airport a) {
	    List<String> neighbors = a.getNeighbors();
	    int neighborsDowned = 0;
	    for(String s : neighbors){
	        if()
        }
    }

}
