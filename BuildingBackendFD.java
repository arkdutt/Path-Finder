// --== CS400 File Header Information ==--
// Name: Ark Dutt
// Email: dutt3@wisc.edu
// Group and Team: DH
// Group TA: Callie
// Lecturer: Florian Heimerl

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
public class BuildingBackendFD implements BuildingBackendInterface{

    protected MapReaderInterface loader;
    protected RestrictedGraphInterface graph;

    public BuildingBackendFD(MapReaderInterface loader, RestrictedGraphInterface graph) {
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
        List<String> file = new ArrayList<>();
        file.add("Van Vleck");
        file.add("Engineering Hall");
        file.add("placeholder1");
        file.add("placeholder2");
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
        return "Van Vleck \n Engineering Hall";
    }
}
