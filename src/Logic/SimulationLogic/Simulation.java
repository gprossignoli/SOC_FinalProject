package Logic.SimulationLogic;

import Logic.Airport;

import java.util.Map;

public abstract class Simulation {
    protected Integer simIterations;
    protected Map<String,Airport> airports;


    public Simulation(Integer simIterations, Map<String,Airport> airports){
        this.simIterations = simIterations;
        this.airports = airports;

    }



    public abstract boolean executeSimulation();
}
