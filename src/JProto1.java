import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class JProto1 {

	public static void main(String[] args) {
		// create nodes with airport ID's
		Node nodeA = new Node(1);
		Node nodeB = new Node(2);
		Node nodeC = new Node(3);
		Node nodeD = new Node(4); 
		Node nodeE = new Node(5);
		Node nodeF = new Node(6);
		 
		// add edges between nodes with cost and duration
		nodeA.addDestination(nodeB, 10, 12);
		nodeA.addDestination(nodeC, 15, 12);
		 
		nodeB.addDestination(nodeD, 12, 10);
		nodeB.addDestination(nodeF, 15, 8);
		 
		nodeC.addDestination(nodeE, 10, 8);
		 
		nodeD.addDestination(nodeE, 2, 3);
		nodeD.addDestination(nodeF, 1, 4);
		 
		nodeF.addDestination(nodeE, 5, 2);
		 
		// add nodes to the graph
		Graph graph = new Graph();
		 
		graph.addNode(nodeA);
		graph.addNode(nodeB);
		graph.addNode(nodeC);
		graph.addNode(nodeD);
		graph.addNode(nodeE);
		graph.addNode(nodeF);
		
		// run Dijkstra's on source nodeA
		graph = calculateShortestPathFromSource(graph, nodeA);
		
		// retrieve shortest cost path from A to F
		List<Node> shortestPathToF = nodeF.getShortestPath();
		
		// print airport ID and cost for each node along the way to F
		for (int i=0; i<shortestPathToF.size(); i++) {
			System.out.println("Step " + i + " - " + shortestPathToF.get(i).getAirportID() + ": " + shortestPathToF.get(i).getCost());
		}
		
		System.out.println("Total Cost to F: " + nodeF.getCost());
		

	}
	
	public static Graph calculateShortestPathFromSource(Graph graph, Node source) 
	{
	    source.setCost(0);
	 
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
	        }
	        settledNodes.add(currentNode);
	    }
	    return graph;
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
	    if (sourceDistance + edgeWeight < evaluationNode.getCost()) {
	        evaluationNode.setCost(sourceDistance + edgeWeight);
	        LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
	        shortestPath.add(sourceNode);
	        evaluationNode.setShortestPath(shortestPath);
	    }
	}

}
