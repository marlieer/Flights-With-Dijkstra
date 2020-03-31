import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.time.LocalDateTime;

public class JProto1 {

	public static void main(String[] args) {
		
		// example data from excel
		
		// create nodes with airport ID's
		Node nodeA = new Node(1);
		Node nodeB = new Node(2);
		Node nodeC = new Node(3);
		Node nodeD = new Node(4); 
		Node nodeE = new Node(5);
		Node nodeF = new Node(6);
		Node nodeG = new Node(7);
		 
		// add edges between nodes with cost and duration
		nodeA.addDestination(nodeB, 10, LocalDateTime.of(2020, 1, 1, 0, 0), LocalDateTime.of(2020, 1, 1, 2, 0));
		nodeA.addDestination(nodeC, 15, LocalDateTime.of(2020, 1, 13, 0, 0), LocalDateTime.of(2020, 1, 13, 2, 0));
		 
		nodeB.addDestination(nodeD, 12, LocalDateTime.of(2020, 1, 12, 0, 0), LocalDateTime.of(2020, 1, 13, 2, 0));
		nodeB.addDestination(nodeF, 15, LocalDateTime.of(2020, 1, 2, 0, 0), LocalDateTime.of(2020, 1, 8, 2, 0));
		 
		nodeC.addDestination(nodeE, 10, LocalDateTime.of(2020, 1, 18, 0, 0), LocalDateTime.of(2020, 1, 19, 2, 0));
		 
		nodeD.addDestination(nodeE, 2, LocalDateTime.of(2020, 1, 20, 0, 0), LocalDateTime.of(2020, 1, 21, 2, 0));
		nodeD.addDestination(nodeF, 1, LocalDateTime.of(2020, 1, 7, 0, 0), LocalDateTime.of(2020, 1, 8, 2, 0));
		 
		nodeF.addDestination(nodeE, 5, LocalDateTime.of(2020, 1, 15, 0, 0), LocalDateTime.of(2020, 1, 16, 2, 0));
		 
		// add nodes to the graph
		Graph graph = new Graph();
		 
		graph.addNode(nodeA);
		graph.addNode(nodeB);
		graph.addNode(nodeC);
		graph.addNode(nodeD);
		graph.addNode(nodeE);
		graph.addNode(nodeF);
		graph.addNode(nodeG);
		
		// run Dijkstra's on source nodeA with dest nodeG
		Node src = nodeA;
		Node dest = nodeF;
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
	        for (Entry < Node, Double> adjacencyPair: 
	          currentNode.getAdjacentNodesCost().entrySet()) {
	            Node adjacentNode = adjacencyPair.getKey();
	            Double edgeWeight = adjacencyPair.getValue();
	            if (!settledNodes.contains(adjacentNode)) {
	                CalculateMinimumCost(adjacentNode, edgeWeight, currentNode);
	                unsettledNodes.add(adjacentNode);
	            }
	            
	            // give adjacent node an arrival time
    	        if (!adjacentNode.equals(source)) {
    	        	int indexOfLastNodeInShortestPath = adjacentNode.getShortestPath().size() - 1;
    		        Node lastNodeOnPathToAdjacentNode = adjacentNode.getShortestPath().get(indexOfLastNodeInShortestPath);
    		        LocalDateTime arrivalTimeToAdjacentNode = lastNodeOnPathToAdjacentNode.getAdjacentNodesDuration().get(adjacentNode)[1];
    		        adjacentNode.setArrivalTime(arrivalTimeToAdjacentNode);
    	        }
	        }
	        
	        
	        // add current node to settled nodes
	        settledNodes.add(currentNode);
	       
	        
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
	
	
	private static void CalculateMinimumCost(Node evaluationNode, Double edgeWeight, Node sourceNode) 
	{
	    Double sourceDistance = sourceNode.getCost();
	    
	    // If the path through source node is cheaper than evaluation node's cost, change evaluation node's cost
	    // the flight must also be feasible (ie, edge must depart to evaluation node AFTER source's arrival time)
	    if (sourceDistance + edgeWeight < evaluationNode.getValidCost(sourceNode)) {
	        evaluationNode.setCost(sourceDistance + edgeWeight);
	        LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
	        shortestPath.add(sourceNode);
	        evaluationNode.setShortestPath(shortestPath);
	    }
	}

}
