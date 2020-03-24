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
	private Double cost;
	private Duration duration;
	
  
    private List<Node> shortestPath = new LinkedList<>();
     
     
    Map<Node, Double> adjacentNodesCost = new HashMap<>();
    Map<Node, Double> adjacentNodesDuration = new HashMap<>();
    
    // constructor
    public Node(int airportID) {

    	// TODO: make duration MAX VALUE
    	this.cost = Double.MAX_VALUE;
    	this.airportID = airportID;
    	
    }
 
    // methods
    public void addDestination(Node destination, double cost, double duration) {
        adjacentNodesCost.put(destination, cost);
        adjacentNodesDuration.put(destination, duration);
    }
 
     
    // getters and setters
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
    
    public Map<Node, Double> getAdjacentNodesDuration() {
    	return this.adjacentNodesDuration;
    }
    
    public void setAdjacentNodesDuration(Map<Node, Double> adjacentNodesDuration) {
    	this.adjacentNodesDuration = adjacentNodesDuration;
    }
}

