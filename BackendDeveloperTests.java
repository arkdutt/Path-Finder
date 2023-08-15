import java.io.FileNotFoundException;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class BackendDeveloperTests {
  /**
   * test the constructor of the BuildingBackend class to ensure that the
   * exception is thrown when the loader is null
   */
  @Test
  public void testFileNotFound() {
    MapReaderInterface loader = new MapReaderBD();
    RestrictedGraphInterface graph = new RestrictedGraphBD();
    BuildingBackendInterface backend = new BuildingBackendBD(loader, graph);
    assertThrows(FileNotFoundException.class, () -> backend.loadFile(null));
  }

  /**
   * test the constructor of the BuildingBackend class to ensure that the
   * exception is thrown when the loader is loaded with an invalid file
   */
  @Test
  public void testInvalidFile() {
    MapReaderInterface loader = new MapReaderBD();
    RestrictedGraphInterface graph = new RestrictedGraphBD();
    BuildingBackendInterface backend = new BuildingBackendBD(loader, graph);
    assertThrows(IllegalArgumentException.class, () -> backend.loadFile("file.txt"));
  }

  /**
   * test the constructor of the BuildingBackend class to ensure that the
   * exception is thrown when the loader is loaded with an valid file
   */
  @Test
  public void testCorrectLoad() {
    MapReaderInterface loader = new MapReaderBD();
    RestrictedGraphInterface graph = new RestrictedGraphBD();
    BuildingBackendInterface backend = new BuildingBackendBD(loader, graph);
    try {
      List<String> buildings = backend.loadFile("file.gv");
      assertEquals(3, buildings.size());
      assertEquals("A", buildings.get(0));
      assertEquals("B", buildings.get(1));
      assertEquals("C", buildings.get(2));
    } catch (Exception e) {
      fail();
    }
  }

  /**
   * test if the getShortestPathInformation method returns the correct result
   */
  @Test
  public void testShortestPath() {
    MapReaderInterface loader = new MapReaderBD();
    RestrictedGraphInterface graph = new RestrictedGraphBD();
    BuildingBackendInterface backend = new BuildingBackendBD(loader, graph);
    try {
      List<String> buildings = backend.loadFile("file.gv");
      String result = backend.getShortestPathInformation("A", "C", buildings);
      assertEquals("1. A\n2. B\n3. C\nEnergy cost = 69420", result);
    } catch (Exception e) {
      fail();
    }
  }

  /**
   * test if the getShortestPathInformation method returns the correct energy
   */
  @Test
  public void testEnergyCost() {
    MapReaderInterface loader = new MapReaderBD();
    RestrictedGraphInterface graph = new RestrictedGraphBD();
    BuildingBackendInterface backend = new BuildingBackendBD(loader, graph);
    try {
      List<String> buildings = backend.loadFile("file.gv");
      String result = backend.getShortestPathInformation("A", "C", buildings);
      assertEquals("69420", result.substring(result.length() - 5));
    } catch (Exception e) {
      fail();
    }
  }

  /**
   * test if the code works with other team members' code, Exception test
   */
  @Test
  public void testExceptionIntegration() {
    MapReaderInterface loader = new MapReaderAE();
    RestrictedGraphInterface graph = new RestrictedGraphAE();
    BuildingBackendInterface backend = new BuildingBackendBD(loader, graph);
    try {
      backend.loadFile("file.txt");
      fail();
    } catch (Exception e) {
      assertEquals("Invalid File Type (needed .gv or .dot)", e.getMessage());
    }
  }

  /**
   * test if the code works with other team members' code, Shortest Path test
   */
  @Test
  public void testShortestPathIntegration() {
    MapReaderInterface loader = new MapReaderAE();
    RestrictedGraphInterface graph = new RestrictedGraphAE();
    BuildingBackendInterface backend = new BuildingBackendBD(loader, graph);
    try {
      backend.loadFile("buildings.dot");
      List<String> inBetweens = Arrays.asList("Adams Residence Hall", "Van Hise Hall");
      String result = backend.getShortestPathInformation("Tripp Residence Hall", "Van Vleck Hall", inBetweens);
      String expected = "1. Tripp Residence Hall" +
                        "\n2. Bernieas Place Childcare" +
                        "\n3. Livestock Laboratory" + 
                        "\n4. Eagle Heights Buildings 401-408" +
                        "\n5. Middleton Building" +
                        "\n6. Adams Residence Hall" +
                        "\n7. Smith Residence Hall" +
                        "\n8. Van Hise Hall" +
                        "\n9. Van Vleck Hall" +
                        "\nEnergy cost = 82";
      assertEquals(expected, result);
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  /**
   * code review of the algorithm engineer's code, energy test
   */
  @Test
  public void CodeReviewOfAlgorithmEngineerEnergyTest() {
    // Create Graph
    RestrictedGraphInterface graph = new RestrictedGraphAE();
    graph.insertNode("A");
    graph.insertNode("B");
    graph.insertNode("C");
    graph.insertNode("D");
    graph.insertNode("E");

    graph.insertEdge("A", "B", 1);
    graph.insertEdge("B", "C", 1);
    graph.insertEdge("C", "D", 1);
    graph.insertEdge("D", "E", 1);
    graph.insertEdge("A", "E", 100);

    // Test
    List<String> inBetweens = Arrays.asList("B", "C", "D");
    assertEquals(4, graph.shortestPathRestrictedCost("A", "E", inBetweens));
  }

  /**
   * code review of the algorithm engineer's code, path test
   */
  @Test
  public void CodeReviewOfAlgorithmEngineerPathTest() {
    // Create Graph
    RestrictedGraphInterface graph = new RestrictedGraphAE();
    graph.insertNode("A");
    graph.insertNode("B");
    graph.insertNode("C");
    graph.insertNode("D");
    graph.insertNode("E");

    graph.insertEdge("A", "B", 1);
    graph.insertEdge("B", "C", 1);
    graph.insertEdge("C", "D", 1);
    graph.insertEdge("D", "E", 1);
    graph.insertEdge("A", "E", 100);

    // Test
    List<String> inBetweens = Arrays.asList("B", "C", "D");
    List<String> expected = Arrays.asList("A", "B", "C", "D", "E");
    assertEquals(expected, graph.shortestPathRestrictedData("A", "E", inBetweens));
  }

  /**
   * Demonstrate the use of the backend
   */
  public static void main(String[] args) {
    MapReaderInterface loader = new MapReaderAE();
    RestrictedGraphInterface graph = new RestrictedGraphAE();
    BuildingBackendInterface backend = new BuildingBackendBD(loader, graph);
    try {
      backend.loadFile("buildings.dot");
      List<String> inBetweens = Arrays.asList("Bascom Hall");
      String result = backend.getShortestPathInformation("Union South", "Memorial Union", inBetweens);
      System.out.println(result);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
