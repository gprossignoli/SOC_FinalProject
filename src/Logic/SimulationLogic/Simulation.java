package Logic.SimulationLogic;

import Logic.Airport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Simulation {
    protected Integer simIterations;
    protected Map<String,Airport> airports;
    protected List<String> availableAirports;
    protected List<String> downedAirports;

    public Simulation(Integer simIterations, Map<String,Airport> airports){
        this.simIterations = simIterations;
        this.airports = airports;
        this.availableAirports = new ArrayList<>();
        this.downedAirports = new ArrayList<>();

        initSimulationList();
    }

    protected void initSimulationList() {
        airports.forEach((k,v) -> availableAirports.add(k));
        downedAirports.clear();
    }

    public abstract boolean executeSimulation();
}
