import Logic.Airport;
import Logic.Context;
import Logic.networkBuilder;
import Logic.SimulationLogic.OverloadModel;
import Logic.SimulationLogic.PropagationModel;
import Logic.SimulationLogic.Simulation;

import java.io.IOException;
import java.util.Map;

public class Controller {

	private Context simData;
	private Simulation sim;

	public Controller(String nodesPath, String edgesPath, Context simData) throws IOException {
        this.simData = simData;
        buildNetwork(nodesPath,edgesPath);

        if(simData.getType().equals("PropagationModel")){
            this.sim = new PropagationModel(simData.getData());
        }

        else if(simData.getType().equals("OverLoadModel")){
            this.sim = new OverloadModel(simData.getData());
        }

	}

    public void initSimulation(){
	    if(!sim.executeSimulation()){
	        if(!sim.executeSimulation())
	            System.out.println("The simulation with id: " + simData.getData().get(1) + "was impossible to execute");
	        else
                System.out.println("The simulation with id: " + simData.getData().get(1) + "was executed with success");
        }

        else{
            System.out.println("The simulation with id: " + simData.getData().get(1) + "was executed with success");

        }
    }

	private boolean buildNetwork(String nodesPath, String edgesPath) {
        try {
            simData.addData(networkBuilder.read(nodesPath, edgesPath));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
