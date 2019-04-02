import SimulationLogic.OverloadModel;
import SimulationLogic.PropagationModel;
import SimulationLogic.Simulation;

import java.io.IOException;

public abstract class Controller {

	private Context simData;
	private Simulation sim;

	public Controller(String pathNodos, String pathEnlaces, Context simData) throws IOException {
        this.simData = simData;
        buildNetwork();

        if(simData.getType().equals("SimulationLogic.PropagationModel")){
            this.sim = new PropagationModel(simData.getData());
        }

        else if(simData.getType().equals("OverLoadModel")){
           this.sim = new OverloadModel(simData.getData());
        }

	}

	private void buildNetwork() throws IOException{
        simData.addData(networkBuilder.read("europeNodes.csv", "europeEdges.csv"));
	}

	public void initSimulation(){}

}
