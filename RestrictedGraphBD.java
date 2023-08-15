import java.util.List;
import java.util.ArrayList;

public class RestrictedGraphBD extends DijkstraGraph<String, Integer> implements RestrictedGraphInterface {
  public List<String> shortestPathRestrictedData(String start, String end, List<String> inBetweens) {
    // return random list of buildings
    ArrayList<String> buildings = new ArrayList<String>();
    buildings.add("A");
    buildings.add("B");
    buildings.add("C");
    
    // return list of building as list of strings
    return buildings;
  }

  public Integer shortestPathRestrictedCost(String start, String end, List<String> inBetweens) {
    return 69420;
  }
}
