package Logic.SimulationLogic;

import Logic.Airport;

import java.util.List;

public abstract class Simulation {
    protected Integer steps;
    protected List<Airport> airports;
    protected List<String> availableAirports;
    protected List<String> downedAirports;

    public Simulation(Integer steps, List<Airport> airports){
        this.steps = steps;
        this.airports = airports;

        initSimulationList();
    }

    private void initSimulationList() {
        for(Airport a : airports){
            availableAirports.add(a.getIata());
        }
    }

    public void executeSimulation(){}
}
