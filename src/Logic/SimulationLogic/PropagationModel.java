package Logic.SimulationLogic;

import Logic.Airport;
import Logic.SimulationLogic.PropagationModelUtils.Chart_Report;
import Logic.SimulationLogic.PropagationModelUtils.ReportBuilder;

import java.util.*;

public class PropagationModel extends Simulation {
    private Double threshold;
    private Integer initialAmountOfAffectedNodes;
    private List<String> availableAirports;
    private List<String> downedAirports;
    private Integer totalNodesInNetwork;
    public PropagationModel(List<Object> simData) {
	    super(Integer.parseInt((String) simData.get(0)), (HashMap) simData.get(3));
        this.threshold = Double.parseDouble((String) simData.get(1));
        this.initialAmountOfAffectedNodes = Integer.parseInt((String) simData.get(2));
        this.availableAirports = new ArrayList<>();
        this.downedAirports = new ArrayList<>();
        this.totalNodesInNetwork = airports.size();
	}

	@Override
	public boolean executeSimulation(){

        ReportBuilder reportBuilder = null;
        Chart_Report chart = new Chart_Report(totalNodesInNetwork);
	    while(threshold <= 1) {
            Random random = new Random();
            int iteration = 0;

            reportBuilder = new ReportBuilder(threshold);

            while (iteration < simIterations) {
                initSimulationList();

                setRandomFailures(random); //At time 0 fails a small number of nodes picked randomly

                Airport a;
                int tries = 0;

                // if the network status doesn't change in the 3 next steps it would assume that the cascade it's stopped
                while (availableAirports.size() > 0 && tries < 3) {
                    String iata = availableAirports.get(random.nextInt(availableAirports.size()));
                    a = airports.get(iata);

                    if (!tryToLockNode(a))
                        ++tries;
                }

                reportBuilder.addData(availableAirports.size(),downedAirports.size());

             iteration++;
            }

            if(!reportBuilder.buildReport(chart))
            return false;

            threshold += 0.05;

        }

        if(!chart.buildBoxPlot() || !chart.buildBarChart())
            return false;

        return true;
    }
    private void initSimulationList() {
        downedAirports.clear();
        availableAirports.clear();
        airports.forEach((k,v) -> {
            airports.get(k).setLock(false);
            availableAirports.add(k);
        });
    }

    private void setRandomFailures(Random random) {
        for (int i = 0; i < initialAmountOfAffectedNodes; ++i) {
            int randomAirport = random.nextInt(availableAirports.size());

            String iata = availableAirports.get(randomAirport);
            airports.get(iata).setLock(true);

            availableAirports.remove(iata);
            downedAirports.add(iata);
        }
    }

    private boolean tryToLockNode(Airport a) {
        //if the number of neighbor nodes is greater than the threshold then the current node would fail
	    List<String> neighbors = a.getNeighbors();
	    Double neighborsDowned = 0.0;

	    for(String s : neighbors){
	        if(downedAirports.contains(s)){
	            neighborsDowned++;
            }
        }

        if((neighborsDowned/neighbors.size()) >= threshold){
            a.setLock(true);
            availableAirports.remove(a.getIata());
            downedAirports.add(a.getIata());
            return true;
        }

        return false;
    }




}
