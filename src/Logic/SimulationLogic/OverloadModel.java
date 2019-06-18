package Logic.SimulationLogic;

import Logic.Airport;
import Logic.SimulationLogic.OverloadModelUtils.Report;
import Logic.SimulationLogic.OverloadModelUtils.ChartReport;
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
    private List<String> downedNodesOnLastTurn;

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
        this.downedNodesOnLastTurn = new ArrayList<>();
    }

    @Override
    public boolean executeSimulation() {
        int tries = 0;
        Report reportA = new Report();
        Report reportB = new Report();
        ChartReport XYReport = new ChartReport();

        initSimulationList();

        setInitialLoad();


        //Mode A

        //apply overload
        availableAirportsModeA.forEach((k,v) -> {
            availableAirportsModeA.replace(k,v+D);
        });

        // if the network status doesn't change in the 3 next steps it would assume that the cascade it's stopped
        while(availableAirportsModeA.size()>0 && tries < 3) {
            int initialDownedAirportsSize = downedAirportsModeA.size();

            //redistributes the load for each downed node on the last turn
            downedNodesOnLastTurn.forEach((node) -> redistributeLoad());
            downedNodesOnLastTurn.clear();
            airports.forEach((k, v) -> {
                if(!v.isLocked()){
                    if (availableAirportsModeA.get(k) > Lfail) {
                        v.setLock(true);
                        downedAirportsModeA.add(k);
                        availableAirportsModeA.remove(k);
                        //adds it for redistribute the load on the next turn
                        downedNodesOnLastTurn.add(k);
                    }
                }
            });
            //no downed nodes in this turn
            if(downedAirportsModeA.size() == initialDownedAirportsSize)
                tries++;

            reportA.addData(downedAirportsModeA.size() - initialDownedAirportsSize);
            XYReport.addData(downedAirportsModeA.size() - initialDownedAirportsSize,"A");
        }

        //unlock all the nodes for mode B
        airports.forEach((k,v) -> v.setLock(false));
        downedNodesOnLastTurn.clear();

        //Mode B

        //apply overload
        availableAirportsModeB.forEach((k,v) -> {
            availableAirportsModeB.replace(k,v+D);
        });

        tries = 0;
        // if the network status doesn't change in the 3 next steps it would assume that the cascade it's stopped
        while(availableAirportsModeB.size()>0 && tries < 3) {
            int initialDownedAirportsSize = downedAirportsModeB.size();

            //redistributes the load for each downed node on the last turn
            downedNodesOnLastTurn.forEach((node) -> redistributeLoad(node));
            downedNodesOnLastTurn.clear();

            airports.forEach((k, v) -> {
                if(!v.isLocked()){
                    if (availableAirportsModeB.get(k) > Lfail) {
                        v.setLock(true);
                        downedAirportsModeB.add(k);
                        availableAirportsModeB.remove(k);
                        downedNodesOnLastTurn.add(k);
                    }
                }
            });
            //no downed nodes in this turn
            if(downedAirportsModeB.size() == initialDownedAirportsSize)
                tries++;

            reportB.addData(downedAirportsModeB.size() - initialDownedAirportsSize);
            XYReport.addData(downedAirportsModeB.size() - initialDownedAirportsSize,"B");
        }


        return reportA.writeReport("ModelA") && reportB.writeReport("ModelB")
                && XYReport.buildChart();
    }

    private void initSimulationList() {
        airports.forEach((k,v) -> {
            availableAirportsModeA.put(k,0.0);
            availableAirportsModeB.put(k,0.0);
            v.setLock(false);
        });
        downedAirportsModeA.clear();
        downedAirportsModeB.clear();
    }

    private void setInitialLoad() {
        Random Linit = new Random();
        List<Double> initialLoads = new ArrayList<>();
        //generates random values between Lmin and Lmax
        for (int i = 0; i < availableAirportsModeA.size(); ++i) {
            initialLoads.add(initalLoadInterval.getValue()
                    + (initalLoadInterval.getKey() - initalLoadInterval.getValue())
                    * Linit.nextDouble());

        }

        Iterator<Double> it = initialLoads.iterator();
        availableAirportsModeA.forEach((k, v) -> {
            availableAirportsModeA.replace(k,
                    it.next());
        });

        Iterator<Double> it1 = initialLoads.iterator();
        availableAirportsModeB.forEach((k, v) -> {
            availableAirportsModeB.replace(k,
               it1.next());
        });
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
