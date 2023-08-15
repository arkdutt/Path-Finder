import java.io.FileNotFoundException;
import java.util.List;
import java.util.Hashtable;

public class BuildingBackendBD implements BuildingBackendInterface {
  MapReaderInterface loader; // map reader object
  RestrictedGraphInterface graph; // graph object

  public BuildingBackendBD(MapReaderInterface loader, RestrictedGraphInterface graph) {
    this.loader = loader; // initialize map reader
    this.graph = graph; // initialize graph
  }

  public List<String> loadFile(String filename) throws FileNotFoundException {
    loader.loadFile(filename); // load grapj file
    Hashtable<Integer, String> index2building = new Hashtable<Integer, String>(); // create hashtable to store index to building
    int index = 0; // initialize index
    for (String building : loader.getBuldings()){ // for each building in loader
      graph.insertNode(building); // insert building into graph
      index2building.put(index, building); // add building to hashtable
      index++; // increment index
    }
    Integer[][] adjacency = loader.getAdjacencyMatrix(); // get adjacency matrix
    for (int i=0; i<adjacency.length; i++){ // for each row in adjacency matrix
      for (int j=0; j<adjacency.length; j++){ // for each column in adjacency matrix
        // if there is an edge between i and j, insert edge into graph
        if (adjacency[i][j] != null) graph.insertEdge(index2building.get(i), index2building.get(j), adjacency[i][j]);
      }
    }
    return loader.getBuldings(); // return list of buildings
  }

  public String getShortestPathInformation(String start, String end, List<String> inBetweens) {
    // get shortest path
    List<String> path = graph.shortestPathRestrictedData(start, end, inBetweens);
    // get cost of shortest path
    Integer cost = graph.shortestPathRestrictedCost(start, end, inBetweens);
    // create string and add each building in path to string 
    String result = "";
    for (int i = 0; i < path.size(); i++) {
      result += i+1 + ". " + path.get(i) + "\n";
    }
    // add cost to string
    result += "Energy cost = " + cost;
    return result; // return string
  }
}
