package SimulationLogic;

import SimulationLogic.Simulation;

import java.util.List;

public class PropagationModel extends Simulation {

    private Float threshold;

	public PropagationModel(List<Object> simData) {
	    super((Integer) simData.get(0));
        this.threshold = (Float) simData.get(1);
	}
	
	public void simular(){

	}

}
