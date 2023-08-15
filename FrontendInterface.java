import java.util.List;
import java.io.FileNotFoundException;
/*
       L (filename) : Loads the database file
       X : reset the list to 0
       A (building name) : Adds a building name to the list
       R (building number) : removes the building number on the added list
       S : Shows the list of added buildings
       F : finds the shortest path given the list of buildings
	 X : Exits program
*/

public interface FrontendInterface {
public void runCommandLoop() throws FileNotFoundException;
public void loadDataCommand(String filename) throws FileNotFoundException;
public String mainMenuPrompt();
public void addBuilding(String building);
//prompts the user to get the search the shortest path between a list of buildings
public String returnShortestPathInformation(List<String> buildings);
//resets the list to 0
public void resetList();
public List<String> listBuildings();
}
