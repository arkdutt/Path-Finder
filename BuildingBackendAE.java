import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
public class BuildingBackendAE implements BuildingBackendInterface{

    protected MapReaderInterface loader;
    protected RestrictedGraphInterface graph;

    public BuildingBackendAE(MapReaderInterface loader, RestrictedGraphInterface graph) {
        this.loader = loader;
        this.graph = graph;
    }
    /**
     * loads a file and dumps is contents into a graph
     *
     * @param filename path to map file
     * @return List of all building options
     * @throws FileNotFoundException    if file does not exist
     * @throws IllegalArgumentException if the file does not have a .gv or .dot extension
     */
    public List<String> loadFile(String filename) throws FileNotFoundException {
        List<String> file = new ArrayList<String>();
        file.add("Building1");
        file.add("Building2");
        file.add("Building3");
        file.add("Building4");
        return file;
    }


    /** Creates an informative string of the path from the start to end with the same restrictions
     * mentioned in the data structure.
     * @param start      starting building
     * @param end        end building
     * @param inBetweens list of building required to be visited in order
     * @return String containing the path and cost of path
     */
    public String getShortestPathInformation(String start, String end, List<String> inBetweens) {
        String out = start+"\n";
        for (String stuff : inBetweens) {
            out += stuff+"\n";
        }
        return out+end;
    }
}
