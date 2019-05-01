package Logic;

import java.util.List;

public class Context {

    /*
    Data format:
    [0] -> simulation model to use : "PropagationModel" or "OverLoadModel"
    [last value] -> always be the map with the nodes objects;
    --For propagation model:
        [1] -> iterations per each threshold
        [2] -> threshold, the program will run simulations with the interval [threshold,1];
           0 <= threshold <= 1 and will be a Double type value
        [3] -> initial amount of nodes affected, afecta directamente a la probabilidad de que se produzca una cascada;
            0 <= initialAmountOfNodesAffected <= number of nodes in the net
    --For overload model:
        [1] -> iterations to be executed
        [2] -> Lmin
        [3] -> Lmax  --> indicates the interval, both included,
                         with which the initial load of each node will be calculated
        [4] -> Lfail --> the max load that a node can support before failing
        [5] -> D --> the load that it's going to be applied for the network in each simulation step
        [6] -> P --> the overload that will be distributed on the rest of the network when a node fails
                    P > 0
    */
    private List<Object> data;
    public Context(List<Object> data){
        this.data = data;
    }

    public String getType() {
        return data.get(0).toString();
    }

    public List<Object> getData() {
        return data.subList(1,data.size());
    }

    public void addData(Object obj) {
        data.add(obj);
    }
}

