package Logic.SimulationLogic;

import Logic.Airport;
import javafx.util.Pair;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;

public class OverloadModel extends Simulation {
    private Pair<Double,Double> initalLoadInterval;
    private Double Lfail;
    private Double D; //the load that it's going to be applied for the network in each simulation step
    private Double P; //the overload that will be distributed on the rest of the network when a node fails
    private Map<String,Double> availableAirports;
    private List<String> downedAirports;

    public OverloadModel(List<Object> data) {
        super((Integer)data.get(0), (Map<String, Airport>) data.get(6));
        this.initalLoadInterval = new Pair<>(Double.parseDouble((String) data.get(1)),
                Double.parseDouble((String) data.get(2)));
        this.Lfail = (Double) data.get(3);
        this.D = (Double) data.get(4);
        this.P = (Double) data.get(5);
        this.availableAirports = new HashMap<>();
        this.downedAirports = new ArrayList<>();
    }

    @Override
    public boolean executeSimulation() {
        int iteration = 0;
        while(iteration++ < this.simIterations){
            initSimulationList();

            setInitialLoad();
            applyOverload();

            int tries = 0;
            // if the network status doesn't change in the 3 next steps it would assume that the cascade it's stopped
            while (downedAirports.size() < availableAirports.size() && tries < 3) {
                int initialDownedAirportsSize = downedAirports.size();
                availableAirports.forEach((k,v) -> {
                        if(v > Lfail) {
                         downedAirports.add(k);
                         availableAirports.remove(k);
                         redistributeLoad();
                        }
                    }
                );
                if(initialDownedAirportsSize == downedAirports.size())
                    tries++;
            }
        }

        return true;
    }

    private void initSimulationList() {
        airports.forEach((k,v) -> availableAirports.put(k,0.0));
        downedAirports.clear();
    }

    private void setInitialLoad() {
        Random Linit = new Random();

        availableAirports.forEach((k,v)-> availableAirports.replace(k,
                Linit.nextDouble()*
                (initalLoadInterval.getValue() - initalLoadInterval.getKey())
                + initalLoadInterval.getKey()));
    }

    private void applyOverload() {
        availableAirports.forEach((k,v)->availableAirports.replace(k, v + D));
    }

    private void redistributeLoad(){
        availableAirports.forEach((k,v)->availableAirports.replace(k, v + P));
    }
}
