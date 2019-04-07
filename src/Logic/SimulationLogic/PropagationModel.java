package Logic.SimulationLogic;

import Logic.Airport;
import Logic.SimulationLogic.PropagationModelUtils.ReportBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PropagationModel extends Simulation {

    private Float threshold;
    private Integer initialAmountOfAffectedNodes;
	public PropagationModel(List<Object> simData) {
	    super((Integer) simData.get(0), (HashMap) simData.get(3));
        this.threshold = (Float) simData.get(1);
        this.initialAmountOfAffectedNodes = (Integer) simData.get(2);
	}
	
	public void executeSimulation(){
        Random random = new Random();
        int iteration = 0;
        ReportBuilder reportBuilder = null;
        while (iteration++ < simIterations) {
            reportBuilder = new ReportBuilder();
            int tries = 0;
            super.initSimulationList();

            //At time 0 fails a small number of nodes picked randomly
            for (int i = 0; i < initialAmountOfAffectedNodes; ++i) {
                int randomAirport = random.nextInt(airports.size());

                String iata = availableAirports.get(randomAirport);
                airports.get(iata).setLock(true);

                availableAirports.remove(iata);
                downedAirports.add(iata);
            }


            Airport a;
            //if the status of the net doesn't change in the 3 next steps then the cascade was stopped
            while (downedAirports.size() < availableAirports.size() && tries <= 3) {
                String iata = availableAirports.get(random.nextInt(availableAirports.size()));
                a = airports.get(iata);
                while (a.isLocked()){
                    iata = availableAirports.get(random.nextInt(availableAirports.size()));
                    a = airports.get(iata);
                }

                if (!tryToLockNode(a))
                    ++tries;
            }

            reportBuilder.addData(availableAirports.size(),downedAirports.size());
        }

        reportBuilder.buildReport();
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
            airports.put(a.getIata(),a);
            availableAirports.remove(a.getIata());
            downedAirports.add(a.getIata());
            return true;
        }

        return false;
    }




}
