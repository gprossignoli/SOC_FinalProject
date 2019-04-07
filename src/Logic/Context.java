package Logic;

import java.util.List;

public class Context {

    /*
    Data format:
    [0] -> simulation model to use
    [1] -> steps
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

