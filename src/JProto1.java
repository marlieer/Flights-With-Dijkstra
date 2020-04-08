import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.time.Duration;
import java.time.LocalDateTime;

public class JProto1 {
	
	/************
	 * TODO:
	 * 1. Integrate ImportData into this algorithm. How to access attributes are described line 23-46
	 * 
	 */

	public static void main(String[] args) {
		
		// import data from excel
		//ImportData data = new ImportData("flight_data.csv");
		// Can also use:
		// ImportData data = new ImportData("flight_data.csv",25); // only reads in 25 data points
		/*
		 * How to access attributes in data for the first flight:
		 * 
		int originAirportID_0 = data.getOriginAirportIDs().get(0);
		int destAirportID_0 = data.getDestAirportIDs().get(0);
		LocalDateTime departureTime_0 = data.getDepartureTimes().get(0);
		LocalDateTime arrivalTime_0 = data.getArrivalTimes().get(0);
		double costFlight_0 = data.getCosts().get(0);
		Duration durationFlight_0 = data.getDurations().get(0);
		String originAirportCity_0 = data.getOriginAirportCity().get(0);
		String destAirportCity_0 = data.getDestAirportCity().get(0);
		
		 * How to compare two LocalDateTimes:
		 * 
		 * If LocalDateTime dt2 is after LocalDateTime dt1, then
		 * dt2.compareTo(dt1) returns +1
		 * dt1.compareTo(dt2) returns -1
		 * 
		 * You can also use: 
		 * Duration.between(dt1,dt2) -> returns positive Duration
		 * Duration.between(dt2,dt1) -> returns negative Duration
		 * 
		 * Duration will have some output like PT10H10M10S. That means there is 10 hours, 10 minutes, and 10 
		 * seconds between the two LocalDateTime values. You can extract individual values with the methods
		 * toDays(), toHours(), toMinutes()
		 */
		
		// Measuring execution time:
		long start = System.nanoTime();
		
		// Creating nodes from ImportData:
		int dataSize = 500000;
		boolean add = true;
		ImportData data = new ImportData("flight_data.csv", dataSize);
		ArrayList<Node> nodes = new ArrayList<Node>();
		for(int i = 0; i < dataSize - 1; i++) {
			int id = data.getOriginAirportIDs().get(i);
			for(int j = 0; j < nodes.size(); j++) {
				if(nodes.get(j).getAirportID() == id) {
					add = false;
				}
			}
			if(add) {
				nodes.add(new Node(id));
				//System.out.println("Node added: " + id);
			}
			add = true;
		}
		
		// Adding data to nodes:
		for(int i = 0; i < dataSize - 1; i++) {
			int id = data.getOriginAirportIDs().get(i);
			for(int j = 0; j < nodes.size(); j++) {
				if(nodes.get(j).getAirportID() == id) {
					int destID = data.getDestAirportIDs().get(i);
					for(int k = 0; k < nodes.size(); k++) {
						if(nodes.get(k).getAirportID() == destID) {
							nodes.get(j).addDestination(nodes.get(k), data.getCosts().get(i), data.getDepartureTimes().get(i), data.getArrivalTimes().get(i));
							//System.out.println("Flight added from " + id + " to " + destID);
						}
					}
				}
			}
		}
		
		// Adding nodes to graph:
		Graph graph = new Graph();
		for(int i = 0; i < nodes.size(); i++) {
			graph.addNode(nodes.get(i));
			//System.out.println("Node added to graph.");
		}
		
		// Running modified Dijkstra's algorithm from 'src' to 'dest':
		Node src = nodes.get(0);
		Node dest = nodes.get(5);
		List<Node> shortestPath = calculateShortestPathFromSource(graph, src, dest);
		
		if (shortestPath != null) {
			
			// print airport ID and cost for each node along the way to F
			int i=0;
			for (i=0; i<shortestPath.size(); i++) {
				System.out.println("Step " + (i+1) + " - " + shortestPath.get(i).getAirportID() + ": " + shortestPath.get(i).getCost());
			}
			System.out.println("Step " + (i+1) + " - " + dest.getAirportID() + ": " + dest.getCost());
			System.out.println("Total Cost: " + dest.getCost());
		}
		
		else {
			System.out.println("No shortest path found");
		}
		
		// Printing execution time:
		long finish = System.nanoTime();
		long executionTime = finish - start;
		System.out.println("Execution Time: " + executionTime / 1000000 + "ms");

	}
	
	// currently calculates shortest path based only on cost (NOT duration)
	public static List<Node> calculateShortestPathFromSource(Graph graph, Node source, Node dest) 
	{
	    source.setCost(0);
	    source.setArrivalTime(LocalDateTime.of(2019, 1, 1, 0, 0));
	 
	    Set<Node> settledNodes = new HashSet<>();
	    Set<Node> unsettledNodes = new HashSet<>();
	 
	    unsettledNodes.add(source);
	 
	    while (unsettledNodes.size() != 0) {
	        Node currentNode = getLowestCostNode(unsettledNodes);
	        unsettledNodes.remove(currentNode);
	        //System.out.println("Current Node: " + currentNode.getAirportID());
	        for (Entry < Node, List<Double>> adjacencyPair: 
	          currentNode.getAdjacentNodesCost().entrySet()) {
	            Node adjacentNode = adjacencyPair.getKey();
	            
	            // if there are parallel flights, node should just return the best flight
	            List<Double> edgeWeights = adjacencyPair.getValue();
	            //System.out.println("Edge cost: " + edgeWeights + ". Adjacent Node: " + adjacentNode.getAirportID());
	            if (!settledNodes.contains(adjacentNode)) {
	                CalculateMinimumCost(adjacentNode, edgeWeights, currentNode);
	                unsettledNodes.add(adjacentNode);
	            }
	            
	            // give adjacent node an arrival time
    	        if (!adjacentNode.equals(source) && adjacentNode.getShortestPath().size() > 0) {
    	        	int indexOfLastNodeInShortestPath = adjacentNode.getShortestPath().size() - 1;
    		        Node lastNodeOnPathToAdjacentNode = adjacentNode.getShortestPath().get(indexOfLastNodeInShortestPath);
    		        int indexOfBestParallelFlight = lastNodeOnPathToAdjacentNode.getIndexOfBestParallelFlight(adjacentNode);
    		        LocalDateTime arrivalTimeToAdjacentNode = lastNodeOnPathToAdjacentNode.getAdjacentNodesDuration().get(adjacentNode).get(indexOfBestParallelFlight)[1];
    		        adjacentNode.setArrivalTime(arrivalTimeToAdjacentNode);
    	        }
	        }
	        
	        
	        // add current node to settled nodes
	        settledNodes.add(currentNode);
	        //System.out.println(currentNode.getArrivalTime());
	        
	        // if the node added is the destination node, return
	        if (currentNode == dest) {
	        	return currentNode.getShortestPath();
	        }
	    }
	    
	    // if the algorithm never adds the destination node, return null. There is no path to the destination
	    return null;
	}
	
	
	private static Node getLowestCostNode(Set < Node > unsettledNodes) 
	{
	    Node lowestCostNode = null;
	    double lowestCost = Double.MAX_VALUE;
	    for (Node node: unsettledNodes) {
	        double nodeCost = node.getCost();
	        if (nodeCost < lowestCost) {
	            lowestCost = nodeCost;
	            lowestCostNode = node;
	        }
	    }
	    return lowestCostNode;
	}
	
	
	private static void CalculateMinimumCost(Node evaluationNode, List<Double> edgeWeights, Node sourceNode) 
	{
	    Double sourceDistance = sourceNode.getCost();
	    
	    
	    // loop through all edge weights. Find best cost feasible edge weight
	    for(int i =0; i<edgeWeights.size(); i++) {
	    	// If the path through source node is cheaper than evaluation node's cost, change evaluation node's cost
		    // the flight must also be feasible (ie, edge must depart to evaluation node AFTER source's arrival time)
		    if (sourceDistance + edgeWeights.get(i) < evaluationNode.getValidCost(sourceNode, i)) {
		    	
		        evaluationNode.setCost(sourceDistance + edgeWeights.get(i));
		        LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
		        sourceNode.setIndexOfBestParallelFlight(evaluationNode, i);
		        
		        if (!shortestPath.contains(sourceNode)) {
		        	shortestPath.add(sourceNode);
			        evaluationNode.setShortestPath(shortestPath);
		        }
		        
		    }
		    
	    }
	    
	    //System.out.println("Index of Best Flight between " + sourceNode.getAirportID() + " and " + evaluationNode.getAirportID() + ": " + sourceNode.getIndexOfBestParallelFlight(evaluationNode));
	    
	    
	    
	    
	}

}
