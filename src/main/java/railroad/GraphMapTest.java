package railroad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphMapTest {

    public static void main(String[] args){

        GraphMap graph = new GraphMap();

        graph.add("A", "B", 5);
        graph.add("B", "C", 4);
        graph.add("C", "D", 8);
        graph.add("D", "C", 8);
        graph.add("D", "E", 6);
        graph.add("A", "D", 5);
        graph.add("C", "E", 2);
        graph.add("E", "B", 3);
        graph.add("A", "E", 7);


        //The distance of the route
        //Output #1
        System.out.println("The distance of the route A-B-C");
        List<String> routes = Arrays.asList("A", "B", "C");
        System.out.println("Output: " + graph.getRouteDistance(routes));

        //Output #2
        System.out.println("The distance of the route A-D");
        routes = Arrays.asList("A", "D");
        System.out.println("Output: " + graph.getRouteDistance(routes));

        //Output #3
        System.out.println("The distance of the route A-D-C");
        routes = Arrays.asList("A", "D", "C");
        System.out.println("Output: " + graph.getRouteDistance(routes));

        //Output #4
        System.out.println("The distance of the route A-E-B-C-D");
        routes = Arrays.asList("A", "E", "B", "C", "D");
        System.out.println("Output: " + graph.getRouteDistance(routes));

        //Output #4
        System.out.println("The distance of the route A-E-D");
        routes = Arrays.asList("A", "E", "D");
        System.out.println("Output: " + graph.getRouteDistance(routes));

        // The number of trips with stops less than maximum specified
        System.out.println("===================");
        ArrayList<String> specificLengthPathLessThan = graph.specificLengthPathLessThan(graph.findVertex("A"), graph.findVertex("C"), 3);
        System.out.println("Paths with less than specified length: " + specificLengthPathLessThan.toString());

        // The number of trips with stops exactly equal to specified amount
        ArrayList<String> specificLengthPathEqual = graph.specificLengthPathEqual(graph.findVertex("A"), graph.findVertex("C"), 3);
        System.out.println("Paths with exact specified length: " + specificLengthPathEqual.toString());

        // The length of the shortest route
        System.out.println("The length of shortest route: " + graph.getShortesPathInDistance("A", "C"));

        // number of different routes with Distance less than a specific distance
        System.out.println("Number of different routes: " + graph.specificDistancePathLessThan(graph.findVertex("A"), graph.findVertex("C"), 30).size());


    }
}
