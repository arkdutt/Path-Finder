import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FrontendDeveloperTests {

    @Test
    public void test1() {
        TextUITester tester = new TextUITester("L buildings.dot\nQ");
        Scanner userInput = new Scanner(System.in);


        BuildingBackendFD placeholder = new BuildingBackendFD(null, null);
        BuildingFrontendInterface front = new BuildingFrontendFD(userInput, placeholder);
        front.runCommandLoop();

        String out = tester.checkOutput();
        Assertions.assertTrue(out.contains("File Successfully loaded!"));
    }

    @Test
    public void test2() {
        TextUITester tester = new TextUITester("L buildings.dot\nA \"Van Vleck\"\nS\nQ");
        Scanner userInput = new Scanner(System.in);


        BuildingBackendFD placeholder = new BuildingBackendFD(null, null);
        BuildingFrontendInterface front = new BuildingFrontendFD(userInput, placeholder);
        front.runCommandLoop();

        String out = tester.checkOutput();
        Assertions.assertTrue(out.contains("1 Van Vleck"));
    }

    @Test
    public void test3() {
        TextUITester tester = new TextUITester("L buildings.dot\nA \"Van Vleck\"\nA \"Engineering Hall\"\nS\nQ");
        Scanner userInput = new Scanner(System.in);
        BuildingBackendFD placeholder = new BuildingBackendFD(null, null);
        BuildingFrontendInterface front = new BuildingFrontendFD(userInput, placeholder);
        front.runCommandLoop();

        String out = tester.checkOutput();
        Assertions.assertTrue(out.contains("1 Van Vleck\n2 Engineering Hall"));
    }

    @Test
    public void test4() {
        TextUITester tester = new TextUITester("L buildings.dot\nA \"Van Vleck\"\nA \"Engineering Hall\"" +
                "\nR 2\nS\nQ");
        Scanner userInput = new Scanner(System.in);
        BuildingBackendFD placeholder = new BuildingBackendFD(null, null);
        BuildingFrontendInterface front = new BuildingFrontendFD(userInput, placeholder);
        front.runCommandLoop();

        String out = tester.checkOutput();
        Assertions.assertTrue(out.contains("1 Van Vleck"));
        Assertions.assertTrue(!out.contains("2 Engineering Hall"));
    }

    @Test
    public void test5() {
        TextUITester tester = new TextUITester("L buildings.dot\nA \"Van Vleck\"\nA \"Engineering Hall\"\nS" +
                "\nR2\nX\nS\nQ");
        Scanner userInput = new Scanner(System.in);
        BuildingBackendFD placeholder = new BuildingBackendFD(null, null);
        BuildingFrontendInterface front = new BuildingFrontendFD(userInput, placeholder);
        front.runCommandLoop();

        String out = tester.checkOutput();
        Assertions.assertTrue(out.contains("The list is empty. Please enter the names of the building."));
    }

    /**
     * Testing the backend loader by comparing its output with the data loader's output
     */
    @Test
    public void BackendTest1() {
        MapReaderInterface loader = new MapReaderAE();
        RestrictedGraphInterface graph = new RestrictedGraphBD();
        BuildingBackendInterface backend = new BuildingBackendBD(loader, graph);
        try {
            List<String> buildings = backend.loadFile("buildings.dot");
            List<String> loading = loader.getBuldings();
            Assertions.assertEquals(loading, buildings);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Comparing the backend's result string with what is expected to return from the graph
     */
    @Test
    public void BackendTest2() {
        MapReaderInterface loader = new MapReaderAE();
        RestrictedGraphInterface graph = new RestrictedGraphAE();
        BuildingBackendInterface backend = new BuildingBackendBD(loader, graph);
        try {
            List<String> loading = backend.loadFile("buildings.dot");
            List<String> buildings = new ArrayList();
            buildings.add("Chadbourne Residence Hall");
            buildings.add("Chamberlin Hall");
            buildings.add("Engineering Hall");
            String result = backend.getShortestPathInformation("Van Vleck Hall", "Union South", buildings);

            List<String> path = graph.shortestPathRestrictedData("Van Vleck Hall", "Union South", buildings);
            Integer cost = graph.shortestPathRestrictedCost("Van Vleck Hall", "Union South", buildings);
            String expectedString = "";
            for (int i = 0; i < path.size(); i++) {
                expectedString += i+1 + ". " + path.get(i) + "\n";
            }
            expectedString += "Energy cost = " + cost;
            Assertions.assertEquals(expectedString, result);
        } catch (Exception e) {
            fail();
        }
    }



    @Test
    public void IntegrationTest1() {
        try{
            String command = "L\nL buildings.dot\n"+
                    "A \"Ogg Residence Hall\"\n"+
                    "A \"Van Vleck Hall\"\n"+
                    "A \"Chadbourne Residence Hall\"\n"+
                    "X\n"+
                    "A \"Union South\"\n"+
                    "A \"University Club\"\n"+
                    "A \"Dejope Residence Hall\"\n"+
                    "A \"Taylor Hall\"\n"+
                    "A \"South Hall\"\n"+
                    "R 2\n"+
                    "S\n"+
                    "Q\n";
            TextUITester ui = new TextUITester(command);
            MapReaderAE read = new MapReaderAE();
            RestrictedGraphAE graph = new RestrictedGraphAE();
            Scanner userInput = new Scanner(System.in);
            BuildingBackendBD back = new BuildingBackendBD(read, graph);
            BuildingFrontendFD front = new BuildingFrontendFD(userInput, back);
            front.runCommandLoop();
            String output = ui.checkOutput();

            String expectedSearchList = "1 Union South"
                    +"\n2 Dejope Residence Hall"
                    +"\n3 Taylor Hall"
                    +"\n4 South Hall";
            assertTrue(output.contains(expectedSearchList));
        } catch (Exception e) {
            fail("Exception thrown on valid: " + e.getMessage());
        }
    }


    @Test
    public void IntegrationTest2() {
        try{
            String command = "L\nL buildings.dot\n"+
                    "A \"Ogg Residence Hall\"\n"+
                    "A \"Van Vleck Hall\"\n"+
                    "A \"Chadbourne Residence Hall\"\n"+
                    "X\n"+ //removes everything
                    "A \"Union South\"\n"+
                    "A \"University Club\"\n"+
                    "A \"Dejope Residence Hall\"\n"+
                    "A \"Taylor Hall\"\n"+
                    "A \"South Hall\"\n"+
                    "R 2\n"+
                    "F\n"+
                    "Q\n";
            TextUITester ui = new TextUITester(command);
            MapReaderAE read = new MapReaderAE();
            RestrictedGraphAE graph = new RestrictedGraphAE();
            Scanner userInput = new Scanner(System.in);
            BuildingBackendBD back = new BuildingBackendBD(read, graph);
            BuildingFrontendFD front = new BuildingFrontendFD(userInput, back);
            front.runCommandLoop();
            String output = ui.checkOutput();
            String expectedResults = "1. Union South"
                    +"\n2. University Hospital"
                    +"\n3. Showerman House (Kronshage)"
                    +"\n4. Grainger Hall"
                    +"\n5. Dejope Residence Hall"
                    +"\n6. Helen C. White Hall"
                    +"\n7. Tripp Residence Hall"
                    +"\n8. Zoe Bayliss Co-Op"
                    +"\n9. Taylor Hall"
                    +"\n10. Meiklejohn House"
                    +"\n11. Plant Sciences"
                    +"\n12. South Hall"
                    +"\nEnergy cost = 97";
            assertTrue(output.contains(expectedResults));
        } catch (Exception e) {
            fail("Exception thrown on valid: " + e.getMessage());
        }
    }

}
