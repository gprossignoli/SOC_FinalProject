package SimulationLogic;

import java.io.IOException;
import java.util.List;

public class OverloadModel extends Simulation {
	
	public OverloadModel(List<Object> simData) throws IOException {
        super((Integer) simData.get(0));
	}
	
	public void simular(float Lmin, float Lmax, float Lfail, float cargaFallo){
	}

}
