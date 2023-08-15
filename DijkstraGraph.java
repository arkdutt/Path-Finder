// --== CS400 Spring 2023 File Header Information ==--
// Name: Punnawish Thuwajit
// Email: thuwajt@wisc.edu
// Team: DH red
// TA: Callie Kim
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>

import java.util.PriorityQueue;
import java.util.Hashtable;
import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
    extends BaseGraph<NodeType, EdgeType>
    implements GraphADT<NodeType, EdgeType> {

  /**
   * While searching for the shortest path between two nodes, a SearchNode
   * contains data about one specific path between the start node and another
   * node in the graph. The final node in this path is stored in it's node
   * field. The total cost of this path is stored in its cost field. And the
   * predecessor SearchNode within this path is referened by the predecessor
   * field (this field is null within the SearchNode containing the starting
   * node in it's node field).
   *
   * SearchNodes are Comparable and are sorted by cost so that the lowest cost
   * SearchNode has the highest priority within a java.util.PriorityQueue.
   */
  protected class SearchNode implements Comparable<SearchNode> {
    public Node node;
    public double cost;
    public SearchNode predecessor;

    public SearchNode(Node node, double cost, SearchNode predecessor) {
      this.node = node;
      this.cost = cost;
      this.predecessor = predecessor;
    }

    public int compareTo(SearchNode other) {
      if (cost > other.cost)
        return +1;
      if (cost < other.cost)
        return -1;
      return 0;
    }
  }

  /**
   * This helper method creates a network of SearchNodes while computing the
   * shortest path between the provided start and end locations. The
   * SearchNode that is returned by this method is represents the end of
   * SearchNodethe
   * shortest path that is found: it's cost is the cost of that shortest path,
   * and the nodes linked together through predecessor references represent
   * all of the nodes along that shortest path (ordered from end to start).
   *
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return SearchNode for the final end node within the shortest path
   * @throws NoSuchElementException when no path from start to end is found
   *                                or when either start or end data do not
   *                                correspond to a graph node
   */
  protected SearchNode computeShortestPath(NodeType start, NodeType end) {
    // check whether or not start and end are actually in the graph
    if (!this.nodes.containsKey(start)) {
      throw new NoSuchElementException("start node does not exist in graph");
    }
    if (!this.nodes.containsKey(end)) {
      throw new NoSuchElementException("end node does not exist in graph");
    }

    // creates priority queue that stores searchNodes
    PriorityQueue<SearchNode> dist = new PriorityQueue<SearchNode>(); 

    // creats a hashmap storing visited node
    Hashtable<NodeType, Boolean> visited = new Hashtable<NodeType, Boolean>(); 

    // initialize all nodes' visited state to be false
    for (NodeType node : nodes.keySet()){
      visited.put(node, false);
    }

    //creat start node and add to priority queue
    SearchNode startNode = new SearchNode(nodes.get(start), 0.0, null); 
    dist.add(startNode); 

    //loops through every node
    while (dist.size() > 0) {

      // get closest node
      SearchNode closest = dist.remove(); 

      // have already found the shortest distance to this node
      if (visited.get(closest.node.data)) continue; 

      // stops if closest == end and return
      if (closest.node.data.equals(end)) return closest; 

      // update visited status to true
      visited.replace(closest.node.data, true); 
      
      // loop through its neighbor
      for (Edge edge : closest.node.edgesLeaving) { 

        // if not visited
        if (!visited.get(edge.successor.data)) { 

          // create neighbor node
          SearchNode neighbor = new SearchNode(edge.successor, edge.data.doubleValue() + closest.cost, closest);  

          // add to priority queue
          dist.add(neighbor); 

        }

      }

    }
    throw new NoSuchElementException("graph is disconnected from start to end"); // can't find end -> throw exception
  }

  /**
   * Returns the list of data values from nodes along the shortest path
   * from the node with the provided start value through the node with the
   * provided end value. This list of data values starts with the start
   * value, ends with the end value, and contains intermediary values in the
   * order they are encountered while traversing this shorteset path. This
   * method uses Dijkstra's shortest path algorithm to find this solution.
   *
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return list of data item from node along this shortest path
   */
  public List<NodeType> shortestPathData(NodeType start, NodeType end) {
    // path stores the list of nodes from start to end
    List<NodeType> path = new LinkedList<NodeType>();
    // call defined helper method, automatically throws exception
    SearchNode currentNode = computeShortestPath(start, end);
    // put nodes from end to start to linkedlist, adding predecessor nodes to the
    // front
    while (currentNode != null) {
      // add predecessor node to front of list
      path.add(0, currentNode.node.data);
      // move node to predecessor
      currentNode = currentNode.predecessor;
    }
    return path;
  }

  /**
   * Returns the cost of the path (sum over edge weights) of the shortest
   * path freom the node containing the start data to the node containing the
   * end data. This method uses Dijkstra's shortest path algorithm to find
   * this solution.
   *
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return the cost of the shortest path between these nodes
   */
  public double shortestPathCost(NodeType start, NodeType end) {
    // we have already precomputed the cost from the shortest path helper
    return computeShortestPath(start, end).cost;
  }

  /**
   * unused main method
   * @param args args if any
   */
  public static void main(String[] args) {
  }
}
