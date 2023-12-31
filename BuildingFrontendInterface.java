/**
* @author Ark Dutt
*/

import java.util.List;
public interface BuildingFrontendInterface {
    public void runCommandLoop();
    public void loadDataCommand(String filename);
    public String mainMenuPrompt();

    public void addBuilding(String building);

    //prompts the user to get the search the shortest path between a list of buildings
    public void returnShortestPathInformation(List<String> buildings);

    //resets the list to 0
    public void resetList();

    public List<String> listBuildings();
    public void removeBuilding(int building);

}
