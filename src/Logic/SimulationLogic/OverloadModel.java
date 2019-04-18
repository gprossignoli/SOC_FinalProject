package Logic.SimulationLogic;

import Logic.Airport;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class OverloadModel extends Simulation {
    public OverloadModel(Integer simIterations, Map<String, Airport> airports) {
        super(simIterations, airports);
    }

    @Override
    public boolean executeSimulation() {
        return false;
    }
	
	/*public OverloadModel(List<Object> simData) throws IOException {
        super((Integer) simData.get(0));
	}
	
	public void simular(float Lmin, float Lmax, float Lfail, float cargaFallo){
	}
*/
}
