import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

public class MapReaderBD implements MapReaderInterface {
  public void loadFile(String filename) throws FileNotFoundException {
    if (filename == null) {
      throw new FileNotFoundException("File not found");
    } else if (!filename.endsWith(".gv") && !filename.endsWith(".dot")) {
      throw new IllegalArgumentException("File must be .gv or .dot");
    }
  }
  public List<String> getBuldings() {
    // return random list of buildings
    ArrayList<String> buildings = new ArrayList<String>();
    buildings.add("A");
    buildings.add("B");
    buildings.add("C");
    
    // return list of building as list of strings
    return buildings;
  }
  public Integer[][] getAdjacencyMatrix() {
    Integer[][] matrix = {{0,1,0},{0,0,1},{1,0,0}};
    return matrix;
  }
}