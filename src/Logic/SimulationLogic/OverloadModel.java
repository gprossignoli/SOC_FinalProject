package Logic.SimulationLogic;

import Logic.Airport;
import Logic.SimulationLogic.OverloadModelUtils.Report;
import Logic.SimulationLogic.OverloadModelUtils.Chart_Report;
import javafx.util.Pair;

import java.util.*;

//We want to compare the results for the execution in two ways,
// first distributing the overload between the entire network,
// second distributing the overload only between the neighbors of the affected node
//ModeA = entire network, ModeB = only neighbors
public class OverloadModel extends Simulation {
    private Pair<Double,Double> initalLoadInterval;
    private Double Lfail;
    private Double D; //the load that it's going to be applied for the network in each simulation step
    private Double P; //the overload that will be distributed on the rest of the network when a node fails
    private Map<String,Double> availableAirportsModeA;
    private Map<String,Double> availableAirportsModeB;
    private List<String> downedAirportsModeA;
    private List<String> downedAirportsModeB;

    public OverloadModel(List<Object> data) {
        super(1, (Map<String, Airport>) data.get(5));
        this.initalLoadInterval = new Pair<>(Double.parseDouble((String) data.get(0)),
                Double.parseDouble((String) data.get(1)));
        this.Lfail = Double.parseDouble((String) data.get(2));
        this.D = Double.parseDouble((String) data.get(3));
        this.P = Double.parseDouble((String) data.get(4));
        this.availableAirportsModeA = new HashMap<>();
        this.downedAirportsModeA = new ArrayList<>();
        this.availableAirportsModeB = new HashMap<>();
        this.downedAirportsModeB = new ArrayList<>();
    }

    @Override
    public boolean executeSimulation() {
            initSimulationList();

            setInitialLoad();
            applyOverload();

            int tries = 0;
            Report reportA = new Report();
            Report reportB = new Report();
            Chart_Report XYReport = new Chart_Report();


            //Mode A
            // if the network status doesn't change in the 3 next steps it would assume that the cascade it's stopped
            while (availableAirportsModeA.size() > 0 && tries < 3) {
                int initialDownedAirportsSize = downedAirportsModeA.size();
                availableAirportsModeA.forEach((k,v) -> {
                        if(v > Lfail && !airports.get(k).isLocked()) {
                         downedAirportsModeA.add(k);
                         airports.get(k).setLock(true);
                         redistributeLoad();
                        }
                    }
                );
                if(initialDownedAirportsSize == downedAirportsModeA.size())
                    tries++;
                else {
                    reportA.addData(downedAirportsModeA.size() - initialDownedAirportsSize);
                    XYReport.addData(downedAirportsModeA.size() - initialDownedAirportsSize,"A");
                }
            }

            //Mode B
            // if the network status doesn't change in the 3 next steps it would assume that the cascade it's stopped
            tries = 0;
            airports.forEach((k,v) -> v.setLock(false));
            while (availableAirportsModeB.size() > 0 && tries < 3) {
                int initialDownedAirportsSize = downedAirportsModeB.size();
                availableAirportsModeB.forEach((k,v) -> {
                            if(v > Lfail && !airports.get(k).isLocked()) {
                                downedAirportsModeB.add(k);
                              Airport a = airports.get(k);
                              a.setLock(true);
                              a.getNeighbors().forEach((n)->redistributeLoad(n));
                            }
                        }
                );
                if(initialDownedAirportsSize == downedAirportsModeB.size())
                    tries++;
                else {
                    reportB.addData(downedAirportsModeB.size() - initialDownedAirportsSize);
                    XYReport.addData(downedAirportsModeB.size() - initialDownedAirportsSize,"B");
                }
            }

        return reportA.writeReport("ModelA") && reportB.writeReport("ModelB")
                && XYReport.buildChart();
    }

    private void initSimulationList() {
        airports.forEach((k,v) -> {
            availableAirportsModeA.put(k,0.0);
            availableAirportsModeB.put(k,0.0);
        });
        downedAirportsModeA.clear();
        downedAirportsModeB.clear();
    }

    private void setInitialLoad() {
        Random Linit = new Random();
        availableAirportsModeA.forEach((k,v)->
            availableAirportsModeA.replace(k,
                    initalLoadInterval.getValue()
                            + (initalLoadInterval.getKey() - initalLoadInterval.getValue())
                            * Linit.nextDouble()));
        availableAirportsModeB.forEach((k,v)->
            availableAirportsModeB.replace(k,
                    initalLoadInterval.getValue()
                            + (initalLoadInterval.getKey() - initalLoadInterval.getValue())
                            * Linit.nextDouble()));
    }

    private void applyOverload() {
        availableAirportsModeA.forEach((k,v)->availableAirportsModeA.replace(k, v + D));
        availableAirportsModeB.forEach((k,v)->availableAirportsModeB.replace(k, v + D));
    }

    private void redistributeLoad(){
        availableAirportsModeA.forEach((k,v)->{
            if(!airports.get(k).isLocked())
                availableAirportsModeA.replace(k, v + P);
        });
    }

    private void redistributeLoad(String downedNodeKey){
        if (!airports.get(downedNodeKey).isLocked())
            availableAirportsModeB.replace(downedNodeKey,
                    availableAirportsModeB.get(downedNodeKey) + P);

    }
}
