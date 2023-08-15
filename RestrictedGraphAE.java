import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

/**
 * This class creates a graph to store buildings and retrieve shortest path given start, end, and inbetween nodes
 * @author Punnawish Thuwajit
 */
public class RestrictedGraphAE extends DijkstraGraph<String, Integer> implements RestrictedGraphInterface {

  /**
   * Finds the shortest path from start to end where inbetweens has to be
   * visited in order
   * 
   * @param start      starting building
   * @param end        end building
   * @param inBetweens list of building required to be visited in order
   * @return list of building visited as a path
   * @throws NoSuchElementException when no path from start to end is found
   *                                or when either start, end, or inbetween data do not
   *                                correspond to a graph node
   * @throws NullPointerException   when start, end, nodes in between, or the list
   *                                itself is null
   */
  public List<String> shortestPathRestrictedData(String start, String end, List<String> inBetweens) {
    // throws nullpointer on parameters
    if (start == null || end == null || inBetweens == null)
      throw new NullPointerException("Input parameters are null");
    List<String> path = new LinkedList<String>();

    // create a pair of nodes (start-end) for each segment in our final path
    // startingNodes stores starting nodes and endingNodes stores ending nodes
    Queue<String> startingNodes = new LinkedList<String>();
    startingNodes.add(start);
    Queue<String> endingNodes = new LinkedList<String>();
    for (String node : inBetweens) {
      if (node == null)
        throw new NullPointerException("Null in-between nodes are found");
      startingNodes.add(node);
      endingNodes.add(node);
    }
    endingNodes.add(end);

    // loops through ever segment from start to end and add the computed path accordingly
    while (startingNodes.size() > 0) {
      // add every node in path
      for (String node : shortestPathData(startingNodes.poll(), endingNodes.poll())) {
        path.add(node);
      }
      // last node will be repeated, in which we remove
      path.remove(path.size()-1);
    }
    // add the removed end node back to the end
    path.add(end);

    return path;
  }

  /**
   * Finds the shortest cost from start to end where inbetweens has to be
   * visited in order
   * 
   * @param start      starting building
   * @param end        end building
   * @param inBetweens list of building required to be visited in order
   * @return cost of the shortest path
   * @throws NoSuchElementException when no path from start to end is found
   *                                or when either start, end, or inbetween data do not
   *                                correspond to a graph node
   * @throws NullPointerException   when start, end, nodes in between, or the list
   *                                itself is null
   */
  public Integer shortestPathRestrictedCost(String start, String end, List<String> inBetweens) {
    // throws nullpointer on parameters
    if (start == null || end == null || inBetweens == null)
      throw new NullPointerException("Input parameters are null");
    Integer totalCost = 0;

    // create a pair of nodes (start-end) for each segment in our final path
    // startingNodes stores starting nodes and endingNodes stores ending nodes
    Queue<String> startingNodes = new LinkedList<String>();
    startingNodes.add(start);
    Queue<String> endingNodes = new LinkedList<String>();
    for (String node : inBetweens) {
      if (node == null)
        throw new NullPointerException("Null in-between nodes are found");
      startingNodes.add(node);
      endingNodes.add(node);
    }
    endingNodes.add(end);

    // loops through ever segment from start to end and add the computed cost to totalCost
    while (startingNodes.size() > 0) {
      totalCost += (int) shortestPathCost(startingNodes.poll(), endingNodes.poll());
    }
    return totalCost;
  }
}
