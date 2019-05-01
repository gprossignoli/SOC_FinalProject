package Logic;

import java.util.ArrayList;
import java.util.List;

public class Airport {
	private String label;
	private String Icao;
	private String Iata;
	private String country;
	private boolean locked;
	private List<String> neighbors;
	private Double load;

    public Airport(){
        neighbors = new ArrayList<String>();
        locked = false;
    }

	public Airport(String label, String Icao, String Iata,
                   String country, boolean locked, List<String> neighbors, Double load){
	    this.label = label;
	    this.Icao = Icao;
	    this.Iata = Iata;
	    this.country = country;
	    this.locked = locked;
	    this.neighbors = neighbors;
	    this.load = load;
    }

	public void setLabel(String a){
		this.label = a;
	}
	
	public void setIcao(String a){
		this.Icao = a;
	}
	
	public void setCountry(String a){
		this.country = a;
	}
	
	public void setLock(boolean status){
		this.locked = status;
	}
	
	public void setIata(String a){
		this.Iata = a;
	}
	
	public String getIata(){
		return this.Iata;
	}
	
	public void addNeighbor(String a){
		neighbors.add(a);
	}
	
	public void setLoad(Double load){
		this.load = load;
	}
	
	public Double getLoad(){
		return this.load;
	}

    public boolean isLocked() {
        return this.locked;
    }

    public List<String> getNeighbors() {
        return neighbors;
    }
}
