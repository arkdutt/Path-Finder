import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Tests the functionality of RestrictedGraphAE.java
 * @author Punnawish Thuwajit
 */
public class AlgorithmEngineerTests {

  /**
   * create an example graph to test on
   * 
   * @return the graph used in lecture
   */
  private RestrictedGraphAE getGraph() {
    // the graph used in lecture
    RestrictedGraphAE graph = new RestrictedGraphAE();

    // nodes
    graph.insertNode("A");
    graph.insertNode("B");
    graph.insertNode("C");
    graph.insertNode("D");
    graph.insertNode("E");
    graph.insertNode("F");
    graph.insertNode("G");
    graph.insertNode("H");

    // edges
    graph.insertEdge("A", "B", 4);
    graph.insertEdge("A", "C", 2);
    graph.insertEdge("A", "E", 15);
    graph.insertEdge("B", "D", 1);
    graph.insertEdge("B", "E", 10);
    graph.insertEdge("C", "D", 5);
    graph.insertEdge("D", "E", 3);
    graph.insertEdge("D", "F", 0);
    graph.insertEdge("F", "D", 2);
    graph.insertEdge("F", "H", 4);
    graph.insertEdge("G", "H", 4);
    graph.insertEdge("G", "F", 3);
    return graph;
  }

  /**
   * Tests whether or not RestrictedGraphAE throws NullPointerException if either
   * 1. Start is null
   * 2. End is null
   * 3. The in-between list is full
   * 4. The list has null elements
   * on its two restricted shortest path methods
   */
  @Test
  public void Test1() {
    try {
      RestrictedGraphAE graph = getGraph();
      String validStart = "A";
      String validEnd = "C";
      List<String> validList = new LinkedList<String>();
      validList.add("B");
      List<String> invalidList = new LinkedList<String>();
      invalidList.add("B");
      invalidList.add(null);

      // 1. Start is null
      assertThrows(NullPointerException.class, () -> {
        graph.shortestPathRestrictedCost(null, validEnd, validList);
      }, "Expected NPE for null start");
      assertThrows(NullPointerException.class, () -> {
        graph.shortestPathRestrictedData(null, validEnd, validList);
      }, "Expected NPE for null start");

      // 2. End is null
      assertThrows(NullPointerException.class, () -> {
        graph.shortestPathRestrictedCost(validStart, null, validList);
      }, "Expected NPE for null end");
      assertThrows(NullPointerException.class, () -> {
        graph.shortestPathRestrictedData(validStart, null, validList);
      }, "Expected NPE for null end");

      // 3. The in-between list is full
      assertThrows(NullPointerException.class, () -> {
        graph.shortestPathRestrictedCost(validStart, validEnd, null);
      }, "Expected NPE for null list");
      assertThrows(NullPointerException.class, () -> {
        graph.shortestPathRestrictedData(validStart, validEnd, null);
      }, "Expected NPE for null list");

      // 4. The list has null elements
      assertThrows(NullPointerException.class, () -> {
        graph.shortestPathRestrictedCost(validStart, validEnd, invalidList);
      }, "Expected NPE for list with null elements");
      assertThrows(NullPointerException.class, () -> {
        graph.shortestPathRestrictedData(validStart, validEnd, invalidList);
      }, "Expected NPE for list with null elements");
    } catch (Exception e) {
      fail("Other exception thrown elsewhere : " + e.getMessage());
    }
  }

  /**
   * Tests whether or not RestrictedGraphAE throws NoSuchElementException when
   * 1. start is not in the graph
   * 2. end is not in the graph
   * 3. there exists node in-between not in the graph
   * on its two restricted shortest path methods
   */
  @Test
  public void Test2() {
    try {
      RestrictedGraphAE graph = getGraph();

      // valid
      String validStart = "A";
      String validEnd = "C";
      List<String> validList = new LinkedList<String>();
      validList.add("B");

      // invalid
      String invalidStart = "X";
      String invalidEnd = "X";
      List<String> invalidList = new LinkedList<String>();
      invalidList.add("X");

      // 1. start is not in the graph
      assertThrows(NoSuchElementException.class, () -> {
        graph.shortestPathRestrictedCost(invalidStart, validEnd, validList);
      }, "Expected NSE for absent start");
      assertThrows(NoSuchElementException.class, () -> {
        graph.shortestPathRestrictedData(invalidStart, validEnd, validList);
      }, "Expected NSE for absent start");

      // 2. end is not in the graph
      assertThrows(NoSuchElementException.class, () -> {
        graph.shortestPathRestrictedCost(validStart, invalidEnd, validList);
      }, "Expected NSE for absent end");
      assertThrows(NoSuchElementException.class, () -> {
        graph.shortestPathRestrictedData(validStart, invalidEnd, validList);
      }, "Expected NSE for absent end");

      // 3. there exists node in-between not in the graph
      assertThrows(NoSuchElementException.class, () -> {
        graph.shortestPathRestrictedCost(validStart, validEnd, invalidList);
      }, "Expected NSE for absent list");
      assertThrows(NoSuchElementException.class, () -> {
        graph.shortestPathRestrictedData(validStart, validEnd, invalidList);
      }, "Expected NSE for absent list");
    } catch (Exception e) {
      fail("Other exception thrown elsewhere : " + e.getMessage());
    }
  }

  /**
   * Tests whether or not RestrictedGraphAE throws NoSuchElementException when
   * 1. a path does not exists from start to end with no inbetweens
   * 2. a path does exist from start to end, but is not valid when considering
   * inbetweens
   * on its two restricted shortest path methods
   */
  @Test
  public void Test3() {

    try {
      // the graph used in lecture
      RestrictedGraphAE graph = getGraph();

      // 1. a path does not exists from start to end with no inbetweens
      // no path from A to G
      List<String> inbetweens = new LinkedList<String>();
      assertThrows(NoSuchElementException.class, () -> {
        graph.shortestPathRestrictedCost("A", "G", inbetweens);
      }, "Expected NSE for if start->end does not exist with no intebetweens");
      assertThrows(NoSuchElementException.class, () -> {
        graph.shortestPathRestrictedData("A", "G", inbetweens);
      }, "Expected NSE for if start->end does not exist with no intebetweens");

      // 2. a path does exist from start to end, but is not valid when considering
      // inbetweens
      // there exists path A->H but not A->E->H
      inbetweens.add("E");
      assertThrows(NoSuchElementException.class, () -> {
        graph.shortestPathRestrictedCost("A", "H", inbetweens);
      }, "Expected NSE for if start->end does not exist considering intebetweens");
      assertThrows(NoSuchElementException.class, () -> {
        graph.shortestPathRestrictedData("A", "H", inbetweens);
      }, "Expected NSE for if start->end does not exist considering intebetweens");
    } catch (Exception e) {
      fail("Other exception thrown on valid: " + e.getMessage());
    }
  }

  /**
   * Tests whether or not RestrictedGraphAE's two restricted shortest path methods
   * correctly returns the shortest path with no inbetweens
   */
  @Test
  public void Test4() {

    try {
      RestrictedGraphAE graph = getGraph();
      List<String> inbetweens = new LinkedList<String>();

      // costs
      assertEquals(graph.shortestPathRestrictedCost("A", "B", inbetweens), 4, "Mismatch cost A->B");
      assertEquals(graph.shortestPathRestrictedCost("A", "C", inbetweens), 2, "Mismatch cost A->C");
      assertEquals(graph.shortestPathRestrictedCost("A", "D", inbetweens), 5, "Mismatch cost A->D");
      assertEquals(graph.shortestPathRestrictedCost("A", "E", inbetweens), 8, "Mismatch cost A->E");

      // paths
      assertEquals(graph.shortestPathRestrictedData("A", "B", inbetweens), Arrays.asList(new String[] { "A", "B" }),
          "Mismatch path data A->B");
      assertEquals(graph.shortestPathRestrictedData("A", "C", inbetweens), Arrays.asList(new String[] { "A", "C" }),
          "Mismatch path data A->C");
      assertEquals(graph.shortestPathRestrictedData("A", "D", inbetweens),
          Arrays.asList(new String[] { "A", "B", "D" }), "Mismatch path data A->D");
      assertEquals(graph.shortestPathRestrictedData("A", "E", inbetweens),
          Arrays.asList(new String[] { "A", "B", "D", "E" }), "Mismatch path data A->E");
    } catch (Exception e) {
      fail("Other exception thrown on valid: " + e.getMessage());
    }

  }

  /**
   * Tests whether or not RestrictedGraphAE's two restricted shortest path methods
   * correctly returns the shortest path with inbetweens where the path containing
   * inbetweens
   * is longer than the shortest path
   */
  @Test
  public void Test5() {

    try {
      RestrictedGraphAE graph = getGraph();

      // case 1 A->D but C has to be included
      assertEquals(graph.shortestPathRestrictedCost("A", "D", Arrays.asList(new String[] { "C" })), 7,
          "Mismatch cost A->C->D");
      assertEquals(graph.shortestPathRestrictedData("A", "D", Arrays.asList(new String[] { "C" })),
          Arrays.asList(new String[] { "A", "C", "D" }), "Mismatch path data A->C->D");

      // case 2 C->E but F has to be included
      assertEquals(graph.shortestPathRestrictedCost("C", "E", Arrays.asList(new String[] { "F" })), 10,
          "Mismatch cost C->F->E");
      assertEquals(graph.shortestPathRestrictedData("C", "E", Arrays.asList(new String[] { "F" })),
          Arrays.asList(new String[] { "C", "D", "F", "D", "E" }), "Mismatch path data C->F->E");

      // case 3 G->E but D,F has to be included in the exact order
      assertEquals(graph.shortestPathRestrictedCost("G", "E", Arrays.asList(new String[] { "D", "F" })), 10,
          "Mismatch cost G->D->F->E");
      assertEquals(graph.shortestPathRestrictedData("G", "E", Arrays.asList(new String[] { "D", "F" })),
          Arrays.asList(new String[] { "G", "F", "D", "F", "D", "E" }), "Mismatch path data G->D->F->E");

    } catch (Exception e) {
      fail("Other exception thrown on valid: " + e.getMessage());
    }

  }

  @Test
  /**
   * Tests the frontend file loader on valid and invalid files.
   * Search result should contain successful load
   */
  public void CodeReviewOfFrontendDeveloper1() {
    try{
      TextUITester tester = new TextUITester("L\nL file.txt\nL file.gv\nQ\n");
      Scanner userInput = new Scanner(System.in);
      BuildingBackendAE backend = new BuildingBackendAE(null, null);
      BuildingFrontendFD frontend = new BuildingFrontendFD(userInput, backend);
      frontend.runCommandLoop();
      String out = tester.checkOutput();
      assertTrue(out.contains("File Successfully loaded!"), "Load success message not found");
    } catch (Exception e) {
      fail("Exception thrown on valid: " + e.getMessage());
    }
  }

  @Test
  /**
   * Tests the frontend add and search function on valid and invalid buildings
   * by comparing results to backend placeholder's result
   */
  public void CodeReviewOfFrontendDeveloper2() {
    try{
      //command includes both valid and invalid buildings, correct implementation should ignore invalid ones
      TextUITester tester = new TextUITester("L file.gv\nA \"Building1\"\nA \"Building2\"\nA \"Building3\"\nA \"doesnotexist\"\nA invalid command\nF\nQ\n");
      List<String> inBetweens = new LinkedList<String>();
      inBetweens.add("Building2");
      Scanner userInput = new Scanner(System.in);
      BuildingBackendAE backend = new BuildingBackendAE(null, null);
      String expected = backend.getShortestPathInformation("Building1","Building3",inBetweens);
      BuildingFrontendFD frontend = new BuildingFrontendFD(userInput, backend);
      frontend.runCommandLoop();
      String out = tester.checkOutput();
      assertTrue(out.contains(expected), "Search string not found");
    } catch (Exception e) {
      fail("Exception thrown on valid: " + e.getMessage());
    }
  }

  @Test
  /**
   * Tests the full application's functionality when loading from a valid dataset file
   */
  public void Integration1() {
    try{
      TextUITester tester = new TextUITester("L\nL buildings.dot\nQ\n");
      MapReaderAE reader = new MapReaderAE();
      RestrictedGraphAE graph = new RestrictedGraphAE();
      BuildingBackendBD backend = new BuildingBackendBD(reader, graph);
      Scanner userInput = new Scanner(System.in);
      BuildingFrontendFD frontend = new BuildingFrontendFD(userInput, backend);
      frontend.runCommandLoop();
      String out = tester.checkOutput();
      assertTrue(out.contains("File Successfully loaded!"), "Load success message not found");
    } catch (Exception e) {
      fail("Exception thrown on valid: " + e.getMessage());
    }
  }

  @Test
  /**
   * Tests the full application's functionality when add and search function on valid and invalid buildings
   * In itself tests the R and S commands as well
   */
  public void Integration2() {
    try{
      String command = "L\nL buildings.dot\n"+
        "A \"Dejope Residence Hall\"\n"+ //add dummy item to remove
        "X\n"+//removed
        "A \"Van Vleck Hall\"\n"+ //valid
        "A \"Tripp Residence Hall\"\n"+ //valid
        "A \"Union South\"\n"+ //valid
        "A \"Van Hise Hall\"\n"+ //valid
        "A \"Dejope Residence Hall\"\n"+ //valid
        "R 3\n"+ //remove union south
        "A \"Does Not Exist\"\n"+ //invalid
        "A invalid lol\n"+//invalid
        "S\n"+//show list of buildings
        "F\n"+//perform search
        "Q\n";
      TextUITester tester = new TextUITester(command);
      MapReaderAE reader = new MapReaderAE();
      RestrictedGraphAE graph = new RestrictedGraphAE();
      BuildingBackendBD backend = new BuildingBackendBD(reader, graph);
      Scanner userInput = new Scanner(System.in);
      BuildingFrontendFD frontend = new BuildingFrontendFD(userInput, backend);
      frontend.runCommandLoop();
      String out = tester.checkOutput();
      
      String expectedSearchList = "1 Van Vleck Hall"
        +"\n2 Tripp Residence Hall"
        +"\n3 Van Hise Hall"
        +"\n4 Dejope Residence Hall";
      assertTrue(out.contains(expectedSearchList), "Correct search string not shown");

      String expectedResults = "1. Van Vleck Hall"
        +"\n2. Veterinary Medicine"
        +"\n3. Tripp Residence Hall"
        +"\n4. Veterinary Medicine"
        +"\n5. Van Vleck Hall"
        +"\n6. Van Hise Hall"
        +"\n7. Smith Residence Hall"
        +"\n8. Stock Pavilion"
        +"\n9. Dejope Residence Hall"
        +"\nEnergy cost = 96";
      assertTrue(out.contains(expectedResults), "Correct results string not shown");
    } catch (Exception e) {
      fail("Exception thrown on valid: " + e.getMessage());
    }
  }
}