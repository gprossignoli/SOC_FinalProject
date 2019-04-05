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
        int tries = 0;


        //At time 0 fails a small number of nodes picked randomly
        for(int i = 0; i < initialAmountOfAffectedNodes; ++i){
            int randomAirport = random.nextInt(airports.size());

           Airport a = airports.get(randomAirport);
            a.setLock(true);

            availableAirports.remove(a.getIata());
            downedAirports.add(a.getIata());
        }

        ++time;
        Airport a;
        //if the status of the net doesn't change in the 3 next steps then the cascade was stopped
        while(time <= steps && downedAirports.size() < availableAirports.size() && tries <= 3){
            a = airports.get(random.nextInt(airports.size()));
            while(!a.isLocked())
                a = airports.get(random.nextInt(airports.size()));

            if(!tryToLockNode(a))
                ++tries;
        }
	}

    private boolean tryToLockNode(Airport a) {
	    List<String> neighbors = a.getNeighbors();
	    int neighborsDowned = 0;
	    for(String s : neighbors){
	        if(downedAirports.contains(s)){
	            neighborsDowned++;
            }
        }

        if((neighborsDowned/neighbors.size()) > threshold){
            a.setLock(true);
            availableAirports.remove(a.getIata());
            downedAirports.add(a.getIata());
            return true;
        }

        return false;
    }

}
