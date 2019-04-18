import Logic.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) throws IOException{
        List<Object> contextData = new ArrayList<>();
	    for(int i = 2; i < args.length; i++){
	        contextData.add(args[i]);
        }
	    Controller controller = new Controller(args[0],args[1],new Context(contextData));
	    controller.initSimulation();
	}
	
	
}



