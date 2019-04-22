package Logic.SimulationLogic;

import Logic.Airport;
import Logic.SimulationLogic.PropagationModelUtils.ReportBuilder;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PropagationModel extends Simulation {
    private Double threshold;
    private Integer initialAmountOfAffectedNodes;
	public PropagationModel(List<Object> simData) {
	    super(Integer.parseInt((String) simData.get(0)), (HashMap) simData.get(3));
        this.threshold = Double.parseDouble((String) simData.get(1));
        this.initialAmountOfAffectedNodes = Integer.parseInt((String) simData.get(2));
	}

	@Override
	public boolean executeSimulation(){
        DecimalFormat thresholdFormat = new DecimalFormat("#0.00");
	    while(threshold <= 1) {
            ReportBuilder reportBuilder = null;
            Random random = new Random();
            int iteration = 0;

            reportBuilder = new ReportBuilder(thresholdFormat.format(threshold));

            while (iteration++ < simIterations) {
                super.initSimulationList();

                setRandomFailures(random); //At time 0 fails a small number of nodes picked randomly

                Airport a;
                int tries = 0;

                // if the network status doesn't change in the 3 next steps it would assume that the cascade it's stopped
                while (downedAirports.size() < availableAirports.size() && tries <= 3) {
                    String iata = availableAirports.get(random.nextInt(availableAirports.size()));
                    a = airports.get(iata);
                    while (a.isLocked()) {
                        iata = availableAirports.get(random.nextInt(availableAirports.size()));
                        a = airports.get(iata);
                    }

                    if (!tryToLockNode(a))
                        ++tries;
                }

                reportBuilder.addData(availableAirports.size(), downedAirports.size());
            }

            if (reportBuilder == null) {
                System.out.println("It was problems with the simulation with id: " + threshold);
                return false;
            }

            if(!reportBuilder.buildReport())
                return false;

            threshold += 0.05;
        }

        return true;
    }

    private void setRandomFailures(Random random) {
        for (int i = 0; i < initialAmountOfAffectedNodes; ++i) {
            int randomAirport = random.nextInt(airports.size());

            String iata = availableAirports.get(randomAirport);
            airports.get(iata).setLock(true);

            availableAirports.remove(iata);
            downedAirports.add(iata);
        }
    }

    private void executeSimulationStep(Random random) {

    }

    private boolean tryToLockNode(Airport a) {
        //if the number of neighbor nodes is greater than the threshold then the current node would fail
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
