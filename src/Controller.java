import Logic.Context;
import Logic.networkBuilder;
import Logic.SimulationLogic.OverloadModel;
import Logic.SimulationLogic.PropagationModel;
import Logic.SimulationLogic.Simulation;

import java.io.IOException;

public class Controller {

	private Context simData;
	private Simulation sim;

	public Controller(String nodesPath, String edgesPath, Context simData) throws IOException {
        this.simData = simData;
        buildNetwork(nodesPath,edgesPath);

        if(simData.getType().equals("Logic.SimulationLogic.PropagationModel")){
            this.sim = new PropagationModel(simData.getData());
        }

        else if(simData.getType().equals("OverLoadModel")){
           this.sim = new OverloadModel(simData.getData());
        }

	}

    public void initSimulation(){
	    sim.executeSimulation();
    }

	private void buildNetwork(String nodesPath, String edgesPath) throws IOException{
        simData.addData(networkBuilder.read("europeNodes.csv", "europeEdges.csv"));
	}

}
