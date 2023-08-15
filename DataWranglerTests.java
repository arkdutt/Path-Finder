import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.fail;

public class DataWranglerTests {

    /**
     * this method tests to ensure that no exceptions are thrown
     * when the DOT file is loaded
     */
    @Test
    public void testLoadFile(){
        MapReaderDW reader = new MapReaderDW();

        try{
            reader.loadFile("buildings.dot");
        }catch(Exception e){
            fail();
        }
    }

    /**
     * This method tests the accuracy of the getBuildings method
     * at the maximum extreme of the data file
     * @throws FileNotFoundException
     */
    @Test
    public void testGetBuildingsMax() throws FileNotFoundException {
        MapReaderDW reader = new MapReaderDW();
        reader.loadFile("buildings.dot");

        if(!reader.getBuldings().get(197).equals("Witte Residence Hall")){
            fail("Incorrect String at 197, should be \"Witte" +
                    " Residence Hall\", but instead is : " + reader.getBuldings().get(197));
        }

    }

    /**
     * This method tests the accuracy of the getBuildings method
     * at the maximum extreme of the data file
     * @throws FileNotFoundException
     */
    @Test
    public void testGetBuildingsMin() throws FileNotFoundException{

        MapReaderDW reader = new MapReaderDW();
        reader.loadFile("buildings.dot");

        if(!reader.getBuldings().get(0).equals("Adams Residence Hall")){
            fail("Incorrect String at 0, should be \"Adams Residence Hall\"" +
                    ", but instead is : " + reader.getBuldings().get(197));
        }
    }

    /**
     * this method tests the accuracy of retrieving a valid edge that exists from
     * getAdjacencyMatrix()
     * @throws FileNotFoundException
     */
    @Test
    public void testGetAdjacencyMatrix() throws FileNotFoundException {
        MapReaderDW reader = new MapReaderDW();
        reader.loadFile("buildings.dot");

        Integer[][] list = reader.getAdjacencyMatrix();

        if(list[0][154] != 13){
            fail("incorrect int value");
        }

    }

    /**
     * this method tests the accuracy of retrieving an invalid edge from
     *      * getAdjacencyMatrix()
     * @throws FileNotFoundException
     */
    @Test
    public void testNullMatrixElement() throws FileNotFoundException {
        MapReaderDW reader = new MapReaderDW();
        reader.loadFile("buildings.dot");

        Integer[][] list = reader.getAdjacencyMatrix();

        if(list[0][2] != null){
            fail("incorrect int value");
        }
    }
}
