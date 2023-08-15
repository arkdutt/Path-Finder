// --== CS400 File Header Information ==--
// Name: Ark Dutt
// Email: dutt3@wisc.edu
// Group and Team: DH
// Group TA: Callie
// Lecturer: Florian Heimerl

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BuildingFrontendFD implements BuildingFrontendInterface {

    private Scanner userInput; // scanner for user input
    private BuildingBackendInterface backend; // backend interface
    private List<String> buildings;
    private List<String> databaseBuildings;


    public BuildingFrontendFD(Scanner userInput, BuildingBackendInterface backend) {
        this.userInput = userInput; // set user input
        this.backend = backend; // set backend
        this.buildings = new ArrayList<String>();
    }

    /*
       L (filename) : Loads the database file
       X : reset the list to 0
       A (building name) : Adds a building name to the list
       R (building number) : removes the building number on the added list
       S : Shows the list of added buildings
       F : finds the shortest path given the list of buildings
     */


    /**
     * This method uses a while-loop to prompt the user for commands and displays appropriate
     * feedback for each. This continues until the user enters 'Q' to quit the program.
     */
    public void runCommandLoop() {
        String choice = mainMenuPrompt(); // get the first choice
        while (choice!=null && !choice.isEmpty() && choice.toUpperCase().charAt(0) != 'Q') {// loop until the user chooses to quit
            switch (choice.toUpperCase().charAt(0)) {
                case 'L': // load data
                    try {
                        loadDataCommand(choice.split(" ")[1]);
                    } catch(Exception e) {
                        System.out.println("Cannot execute the command : invalid L command format");
                    }
                    break;
                case 'X': // reset current filter
                    resetList();
                    break;
                case 'A':
                    try {
                        addBuilding(choice.split("\"")[1]);
                        break;
                    } catch (Exception e) {
                        System.out.println("Cannot execute the command : invalid string format (string needed to be in quotation)");
                    }
                    break;

                case 'R':
                    try {
                        int num = Integer.valueOf(choice.split(" ")[1]);
                        removeBuilding(num);
                    } catch(IndexOutOfBoundsException e) {
                        System.out.println("Please enter a valid number!");
                    }catch(Exception e) {
                        System.out.println("Invalid!");
                    }
                    break;
                case 'S':
                    System.out.println("");
                    List<String> l = listBuildings();
                    for(int i=0;i<l.size();i++) {
                        System.out.println(l.get(i));
                    }
                    break;
                case 'F':
                    returnShortestPathInformation(buildings);
                    break;
                default: // invalid choice
                    System.out.println("Invalid choice");
            }
            choice = mainMenuPrompt(); // get the next choice
        }
        userInput.close();
        System.out.println("Program Quitted. Thanks for using!");
    }

    /**
     * Prints the command options to System.out and reads user's choice through
     * userInput scanner.
     * @return the input of the user
     */
    public String mainMenuPrompt() {
        System.out.println("--- Shortest Path Finder ---");
        System.out.println("L (filename) : Loads the database file");
        System.out.println("X : reset the list to 0");
        System.out.println("A (building name) : Adds a building name to the list");
        System.out.println("R (building number) : removes the building number on the added list");
        System.out.println("S : Shows the list of added buildings");
        System.out.println("F : finds the shortest path given the list of buildings");
        System.out.println("Q : Quit program");

        System.out.print("Enter choice: ");
        String input = userInput.nextLine();
        if (input.trim().length() == 0) {
            return null;
        }
        return input;
    }

    /**
     *
     * Prompt user to enter filename, and display error message when loading fails.
     *
     * @param filename which the user wants to load
     */
    public void loadDataCommand(String filename) {
        try {
            this.databaseBuildings = backend.loadFile(filename);
            System.out.println("File Successfully loaded!");
        } catch(FileNotFoundException e) {
            System.out.println("ERROR! File not found!");
            loadDataCommand(userInput.nextLine());
        }
    }

    /**
     * Helper method to remove the building when the user types 'R (building number)'
     * @param buildingNumber
     */
    public void removeBuilding(int buildingNumber) {
        String removed = buildings.remove(buildingNumber-1);
        System.out.println("Building #"+buildingNumber+". "+removed+" removed!");
    }

    /**
     * Helper method to add a building the when the user types 'A buildingName'
     * @param building
     */
    public void addBuilding(String building)  {
        try{
            if (this.databaseBuildings.contains(building)) {
                this.buildings.add(building);
                System.out.println("Building \""+building+"\" successfully added!");
            } else {
                System.out.println("Building \""+building+"\" is not found in database, add aborted.");
            }
        } catch (Exception e) {
            System.out.println("Database not initialized");
        }
        
    }

    /**
     * prompts the user to get the search the shortest path between a list of buildings
     * @param listOfBuildings which the user has already added
     */
    public void returnShortestPathInformation(List<String> listOfBuildings) {
        String start = listOfBuildings.get(0);
        String end = listOfBuildings.get(listOfBuildings.size()-1);
        List<String> inBetweens = new ArrayList<String>();
        for(int i=1; i<listOfBuildings.size()-1;i++){
            inBetweens.add(listOfBuildings.get(i));
        }
        System.out.println(backend.getShortestPathInformation(start, end, inBetweens));
    }

    /**
     * Resets the list to 0
     */
    public void resetList() {
        this.buildings.removeAll(this.buildings);
    }

    /**
     * Displays a list of buildings which the user added so far along with a building number
     * @return
     */
    public List<String> listBuildings() {
        List<String> buildingList = new ArrayList<>();
        if(buildings.size() == 0) {
            System.out.println("The list is empty. Please enter the names of the building.");
        }
        int index = 0;
        for(String building : this.buildings) {
            index++;
            buildingList.add(index + " " + building);
        }
        return buildingList;
    }
}