import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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
	Map<Node, Integer> indexOfBestParallelFlight = new HashMap<>();
	
	private Duration duration;
	
  
    private List<Node> shortestPath = new LinkedList<>();
     
     
    Map<Node, List<Double>> adjacentNodesCost = new HashMap<>();
    
    // LocalDateTime [] stores an edge's departure and arrival time in that order
    Map<Node, List<LocalDateTime []>> adjacentNodesDuration = new HashMap<>();
    
    // constructor
    public Node(int airportID) {

    	this.duration = Duration.ofDays(60);
    	this.cost = Double.MAX_VALUE;
    	this.airportID = airportID;
    	
    }
 
    // adds an edge between nodes.
    public void addDestination(Node destination, double cost, LocalDateTime arrival, LocalDateTime departure) {
        // if there is already an entry for this neighbour, add cost onto end of costs array. Otherwise make a new entry
    	if (adjacentNodesCost.containsKey(destination)) {
        	// add another cost
    		List <Double> costs = adjacentNodesCost.get(destination);
        	costs.add(cost);
        	adjacentNodesCost.replace(destination, costs);
        	
        	// add another arrival/departure time
        	List <LocalDateTime []> flighttimes = adjacentNodesDuration.get(destination);
        	LocalDateTime [] arrdepttimes = new LocalDateTime [] {departure, arrival};
        	flighttimes.add(arrdepttimes);
        	adjacentNodesDuration.replace(destination, flighttimes);
        	
        } else {
        	// add new cost
        	List<Double> costs = new ArrayList<>();
        	costs.add(cost);
        	adjacentNodesCost.put(destination, costs);
        	
        	// add new arrival/departure time
        	List<LocalDateTime []> flighttimes = new ArrayList<>();
        	LocalDateTime [] arrdepttimes = new LocalDateTime [] {departure, arrival};
        	flighttimes.add(arrdepttimes);
        	adjacentNodesDuration.put(destination, flighttimes);
        }
        
    }
    
    
    // TODO adjust for parallel flights
    // returns cost only if flight is valid. Otherwise returns -infinity and sets edge cost to infinity
    public double getValidCost(Node source, int index) {
    	
    	// get source's settled arrival time and edge's arrival and departure time
    	LocalDateTime srcArrivalTime = source.getArrivalTime();
    	List<LocalDateTime []> flightDepartureTime = source.getAdjacentNodesDuration().get(this);
    	
    	// if the flight leaves after the source node's arrival time, return cost. Else return infinite cost
    	if (!Duration.between(srcArrivalTime, flightDepartureTime.get(index)[0]).isNegative()) {
    		System.out.println("Valid Edge for index " + index);
    		return this.cost;
    	} else {
    		
    		// set edge at index to infinity
    		List<Double> infinityCosts = source.getAdjacentNodesCost().get(this);
    		infinityCosts.set(index, Double.MAX_VALUE);
    		source.getAdjacentNodesCost().replace(this, infinityCosts);
    		System.out.println("Invalid Edge for index " + index);
    		return -Double.MAX_VALUE;
    	}
    	
    }
    
    public Map<Node, List<Double>> getBestValue() {
    	// sort List by arrival times increasing
    	List<LocalDateTime []> flighttimes = this.adjacentNodesDuration.get(this);
//    	Collections.sort(flighttimes);
    	
    	// loop through List and return first valid entry
    	
    	// set valid entry to be at index 0 in adjacentNodesCost AND adjacentNodesDuration
    	
    	return this.adjacentNodesCost;
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
    
    public Map<Node, List<Double>> getAdjacentNodesCost() {
    	return this.adjacentNodesCost;
    }
    
    public void setAdjacentNodesCost(Map<Node, List<Double>> adjacentNodesCost) {
    	this.adjacentNodesCost = adjacentNodesCost;
    }
    
    public Map<Node, List<LocalDateTime []>> getAdjacentNodesDuration() {
    	return this.adjacentNodesDuration;
    }
    
    public void setAdjacentNodesDuration(Map<Node, List<LocalDateTime []>> adjacentNodesDuration) {
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

	public Integer getIndexOfBestParallelFlight(Node neighbour) {
		return indexOfBestParallelFlight.get(neighbour);
	}

	public void setIndexOfBestParallelFlight(Node neighbour, Integer indexOfBestParallelFlight) {
		this.indexOfBestParallelFlight.put(neighbour, indexOfBestParallelFlight);
	}

	
}

