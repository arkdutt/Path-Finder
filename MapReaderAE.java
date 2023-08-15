import java.util.List;
import java.util.LinkedList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Hashtable;

/**
 * This class allows for dot file loading and parsing into node and edges
 * @author Punnawish Thuwajit
 */
public class MapReaderAE implements MapReaderInterface {

  private List<String> buildings;
  private List<Edge> edges;

  protected class Edge {
    protected Integer predecessor;
    protected Integer successor;
    protected Integer cost;
    public Edge(Integer predecessor, Integer successor, Integer cost){
      this.predecessor = predecessor;
      this.successor = successor;
      this.cost = cost;
    }
  }

  /**
   * loads a file and saves into its internal data (the buldings list and
   * adjacency matrix)
   * 
   * @param filename path to map file
   * @throws FileNotFoundException    if file does not exist
   * @throws IllegalArgumentException if the file does not have a .gv or .dot
   *                                  extension
   */
  public void loadFile(String filename) throws FileNotFoundException {
    if (!filename.endsWith(".gv") && !filename.endsWith(".dot"))
      throw new IllegalArgumentException("Invalid File Type (needed .gv or .dot)");
    File file = new File(filename);
    if (!file.exists())
      throw new FileNotFoundException("File not found");
    Scanner scanner = new Scanner(file);
    buildings = new LinkedList<String>();
    edges = new LinkedList<Edge>();
    Hashtable<String, Integer> idToIndex = new Hashtable<String, Integer>();
    int index = 0;
    while(scanner.hasNextLine()){
      try{
        String line = scanner.nextLine().trim();
        String nodes = line.substring(0, line.indexOf('[')).trim();
        String condition = line.substring(line.indexOf('[')+1, line.length()-1);
        if (condition.contains("label")){
          String name = condition.substring(7,condition.length()-1);
          buildings.add(name);
          idToIndex.put(nodes, index);
          index ++;
        } else {
          String weights = condition.substring(7,condition.length());
          String start = nodes.split("--")[0].trim();
          String end = nodes.split("--")[1].trim();
          edges.add(new Edge(idToIndex.get(start), idToIndex.get(end), Integer.valueOf(weights)));
        }
      } catch (Exception e) {
        continue;
      }
    }
    scanner.close();
  }

  /**
   * get the map's list of buildings
   * 
   * @return the map's list of buildings
   */
  public List<String> getBuldings() {
    return buildings;
  }

  /**
   * Gets the map's adjacency matrix. The order of building in matrix corresponds
   * to getBuldings(). A value of null denotes the two buildings are not connected in
   * any way.
   * 
   * @return the map's list of edges as an adjacency matrix
   */
  public Integer[][] getAdjacencyMatrix() {
    Integer[][] adjacencyMatrix = new Integer[buildings.size()][buildings.size()];
    for (Edge edge : edges) {
      adjacencyMatrix[edge.predecessor][edge.successor] = edge.cost;
      adjacencyMatrix[edge.successor][edge.predecessor] = edge.cost;
    }
    return adjacencyMatrix;
  }

  public static void main(String[] args) {
  }
}
