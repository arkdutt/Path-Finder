# run app (TBD)

runApp: App.class
	java App

# run tests

runTests: AlgorithmEngineerTests.class BackendDeveloperTests.class FrontendDeveloperTests.class
	java -jar junit5.jar -cp . --select-class=AlgorithmEngineerTests
	java -jar junit5.jar -cp . --select-class=BackendDeveloperTests
	java -jar junit5.jar -cp . --select-class=FrontendDeveloperTests

# interfaces and common code

TextUITester.class: TextUITester.java
	javac TextUITester.java

MapReaderInterface.class: MapReaderInterface.java
	javac MapReaderInterface.java

RestrictedGraphInterface.class: RestrictedGraphInterface.java
	javac RestrictedGraphInterface.java

DijkstraGraph.class: DijkstraGraph.java BaseGraph.class GraphADT.class
	javac DijkstraGraph.java

BaseGraph.class: BaseGraph.java
	javac BaseGraph.java

GraphADT.class: GraphADT.java
	javac GraphADT.java

BuildingBackendInterface.class: BuildingBackendInterface.java
	javac BuildingBackendInterface.java

BuildingFrontendInterface.class: BuildingFrontendInterface.java
	javac BuildingFrontendInterface.java

# data wrangler

MapReaderAE.class: MapReaderAE.java MapReaderInterface.class
	javac MapReaderAE.java

MapReaderDW.class: MapReaderDW.java MapReaderInterface.class
	javac MapReaderDW.java

DataWranglerTests.class: DataWranglerTests.java MapReaderDW.class
	javac -cp .:junit5.jar DataWranglerTests.java

# algorithm engineer

RestrictedGraphAE.class: RestrictedGraphAE.java RestrictedGraphInterface.class DijkstraGraph.class
	javac RestrictedGraphAE.java

BuildingBackendAE.class: BuildingBackendAE.java BuildingBackendInterface.class
	javac BuildingBackendAE.java

AlgorithmEngineerTests.class: AlgorithmEngineerTests.java RestrictedGraphAE.class BuildingBackendAE.class TextUITester.class
	javac -cp junit5.jar:. AlgorithmEngineerTests.java

# backend developer

MapReaderBD.class: MapReaderBD.java MapReaderInterface.class
	javac MapReaderBD.java

RestrictedGraphBD.class: RestrictedGraphBD.java RestrictedGraphInterface.class DijkstraGraph.class
	javac RestrictedGraphBD.java

BuildingBackendBD.class: BuildingBackendBD.java BuildingBackendInterface.class
	javac BuildingBackendBD.java

BackendDeveloperTests.class: BackendDeveloperTests.java MapReaderBD.class RestrictedGraphBD.class BuildingBackendBD.class
	javac -cp junit5.jar:. BackendDeveloperTests.java

# frontend developer

FrontendDeveloperTests.class: FrontendDeveloperTests.java junit5.jar BuildingFrontendFD.class BuildingBackendFD.class TextUITester.class
	javac -cp .:junit5.jar FrontendDeveloperTests.java

BuildingFrontendFD.class: BuildingFrontendFD.java BuildingFrontendInterface.java
	javac BuildingFrontendFD.java

BuildingBackendFD.class: BuildingBackendFD.java BuildingBackendInterface.class
	javac BuildingBackendFD.java

App.class: App.java BuildingFrontendFD.class BuildingBackendBD.class RestrictedGraphAE.class MapReaderAE.class
	javac App.java

# clean
clean:
	rm *.class