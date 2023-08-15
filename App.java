import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        MapReaderAE reader = new MapReaderAE();
        RestrictedGraphAE graph = new RestrictedGraphAE();
        BuildingBackendBD backend = new BuildingBackendBD(reader, graph);
        Scanner userInput = new Scanner(System.in);
        BuildingFrontendFD frontend = new BuildingFrontendFD(userInput, backend);
        frontend.runCommandLoop();
    }
}