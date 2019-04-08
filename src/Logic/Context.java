package Logic;

import java.util.List;

public class Context {

    /*
    Data format:
    [0] -> simulation model to use
    [last value] -> always be the map with the nodes objects;
    --For propagation model:
        [1] -> iterations per each threshold
        [2] -> threshold, the program will run simulations with the interval [threshold,1];
            threshold <= 1
        [3] -> initial amount of nodes affected, afecta directamente a la probabilidad de que se produzca una cascada;
            0 <= initialAmountOfNodesAffected <= number of nodes in the net

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

