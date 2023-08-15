import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MapReaderDW implements MapReaderInterface{

    private File file;

    @Override
    public void loadFile(String filename) throws FileNotFoundException {
        file = new File(filename);
    }

    @Override
    public List<String> getBuldings() {
        List<String> returnList = new ArrayList<>();

        try {
            //create scanner for document
            Scanner scnr = new Scanner(file);

            //scan through document
            while(scnr.hasNextLine()){
                String line = scnr.nextLine();

                //if the line has quotations in it
                if(line.contains("\"")){

                    //add the string between the
                    // quotation marks to the list
                    line = line.split("\"")[1];
                    returnList.add(line);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error: file not found");
        }

        return returnList;
    }

    @Override
    public Integer[][] getAdjacencyMatrix() {
        Integer[][] weightMatrix = new Integer[200][200];

        try {
            Scanner scnr = new Scanner(file);

            while(scnr.hasNextLine()){
                String line = scnr.nextLine();

                //if the current line is an edge def.
                if(line.contains("weight")){
                    int firstNum = Integer.parseInt(line.split("--")[0].trim());
                    int secondNum = Integer.parseInt(line.split("--")[1].trim().split("\\[")[0].trim());
                    int weight = Integer.parseInt(line.split("=")[1].trim().split("\\]")[0].trim());

                    weightMatrix[firstNum][secondNum] = weight;
                }

            }

        }catch(Exception e){
            System.out.println("Error: file not found");
        }

        return weightMatrix;
    }
}
