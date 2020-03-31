import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Node {

	// attributes
	private Integer airportID;
	private LocalDateTime arrivalTime;
	private LocalDateTime departureTime;
	private Double cost;
	private Duration duration;
	
  
    private List<Node> shortestPath = new LinkedList<>();
     
     
    Map<Node, Double> adjacentNodesCost = new HashMap<>();
    
    // LocalDateTime [] stores an edge's departure and arrival time in that order
    Map<Node, LocalDateTime []> adjacentNodesDuration = new HashMap<>();
    
    // constructor
    public Node(int airportID) {

    	this.duration = Duration.ofDays(60);
    	this.cost = Double.MAX_VALUE;
    	this.airportID = airportID;
    	
    }
 
    // adds an edge between nodes.
    public void addDestination(Node destination, double cost, LocalDateTime arrival, LocalDateTime departure) {
        adjacentNodesCost.put(destination, cost);
        LocalDateTime [] arrdepttimes = new LocalDateTime [] {departure, arrival};
        adjacentNodesDuration.put(destination, arrdepttimes);
    }
    
    // TODO: method to calculate best flight between this node and an adjacent node
    // probably use adjacentNodesDuration. Return single edge
  
 
     
    // getters and setters
    public double getValidCost(Node source) {
    	
    	// get source's settled arrival time and edge's arrival and departure time
    	LocalDateTime srcArrivalTime = source.getArrivalTime();
    	LocalDateTime flightDepartureTime = source.getAdjacentNodesDuration().get(this)[0];
    	
    	System.out.println(srcArrivalTime + ": src ID is " + source.getAirportID());
    	System.out.println(flightDepartureTime + ": this ID is " + this.getAirportID());
    	
    	// if the flight leaves after the source node's arrival time, return cost. Else return infinite cost
    	if (!Duration.between(srcArrivalTime, flightDepartureTime).isNegative()) {
    		return this.cost;
    	} else return Double.MAX_VALUE;
    	
    }
    
    public double getCost() {
    	return this.cost;
    }
    
    public void setCost(double cost) {
    	this.cost = cost;
    }
    
    public int getAirportID() {
    	return this.airportID;
    }
    
    public List<Node> getShortestPath() {
    	return this.shortestPath;
    }
    public void setShortestPath(List<Node> shortestPath) {
    	this.shortestPath = shortestPath;
    }
    
    public Map<Node, Double> getAdjacentNodesCost() {
    	return this.adjacentNodesCost;
    }
    
    public void setAdjacentNodesCost(Map<Node, Double> adjacentNodesCost) {
    	this.adjacentNodesCost = adjacentNodesCost;
    }
    
    public Map<Node, LocalDateTime []> getAdjacentNodesDuration() {
    	return this.adjacentNodesDuration;
    }
    
    public void setAdjacentNodesDuration(Map<Node, LocalDateTime []> adjacentNodesDuration) {
    	this.adjacentNodesDuration = adjacentNodesDuration;
    }

	public LocalDateTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(LocalDateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public LocalDateTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalDateTime departureTime) {
		this.departureTime = departureTime;
	}
}

