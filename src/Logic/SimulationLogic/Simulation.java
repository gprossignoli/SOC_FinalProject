package Logic.SimulationLogic;

import Logic.Airport;

import java.util.List;

public abstract class Simulation {
    protected Integer steps;
    protected List<Airport> airports;

    public Simulation(Integer steps, List<Airport> airports){
        this.steps = steps;
        this.airports = airports;
    }

    public void executeSimulation(){}
}
