import java.io.FileNotFoundException;
import java.util.List;
public interface BuildingBackendInterface {
    // public BuildingBackendInterface(MapReaderInterface loader, RestrictedGraphInterface graph);
    /**
     * loads a file and dumps is contents into a graph
     * 
     * @param filename path to map file
     * @return List of all building options
     * @throws FileNotFoundException    if file does not exist
     * @throws IllegalArgumentException if the file does not have a .gv extension
     */
    public List<String> loadFile(String filename) throws FileNotFoundException;

    /** Creates an informative string of the path from the start to end with the same restrictions
     * mentioned in the data structure.
     * @param start      starting building
     * @param end        end building
     * @param inBetweens list of building required to be visited in order
     * @return String containing the path and cost of path
     */
    public String getShortestPathInformation(String start, String end, List<String> inBetweens);
}
