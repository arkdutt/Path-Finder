import java.util.List;
import java.io.FileNotFoundException;

public interface MapReaderInterface {
  /**
   * loads a file and saves into its internal data (the buldings list and
   * adjacency matrix)
   * 
   * @param filename path to map file
   * @throws FileNotFoundException    if file does not exist
   * @throws IllegalArgumentException if the file does not have a .gv extension
   */
  public void loadFile(String filename) throws FileNotFoundException;

  /**
   * get the map's list of buildings
   * 
   * @return the map's list of buildings
   */
  public List<String> getBuldings();

  /**
   * Gets the map's adjacency matrix. The order of building in matrix corresponds to
   * getBuldings(). A value of null denotes the two buildings are not connected in any way.
   * 
   * @return
   */
  public Integer[][] getAdjacencyMatrix();
}
