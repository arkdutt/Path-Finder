import java.util.List;

public interface RestrictedGraphInterface extends GraphADT<String, Integer> {
    /**
     * Finds the shortest path from start to end where inbetweens has to be
     * visited in order
     * @param start      starting building
     * @param end        end building
     * @param inBetweens list of building required to be visited in order
     * @return list of building visited as a path
     */
    public List<String> shortestPathRestrictedData(String start, String end, List<String> inBetweens);

    /**
     * Finds the shortest cost from start to end where inbetweens has to be
     * visited in order
     * 
     * @param start      starting building
     * @param end        end building
     * @param inBetweens list of building required to be visited in order
     * @return cost of the shortest path
     */
    public Integer shortestPathRestrictedCost(String start, String end, List<String> inBetweens);
}
