package railroad;

import java.util.*;

import static railroad.State.COMPLETE;
import static railroad.State.UNVISITED;
import static railroad.State.VISITED;

public class GraphMap {

    private ArrayList<Vertex> vertices;
    private ArrayList<Edge> edges;

    public GraphMap() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public void add(String fromCity, String toCity, int distance){

        Edge temp = findEdge(fromCity, toCity);

        if (temp != null) {
            System.out.println("Edge " + fromCity + "," + toCity + " already exists. Changing cost.");
            temp.distance = distance;
        }
        else {
            Edge e = new Edge(fromCity, toCity, distance);
            edges.add(e);
        }
    }

    public Vertex findVertex(String v){

        for (Vertex each : vertices){
            if (each.value.compareTo(v)==0)
                return each;
        }
        return null;
    }


    public Edge findEdge(Vertex v1, Vertex v2){

        for (Edge each : edges){
            if (each.fromCity.equals(v1) && each.toCity.equals(v2)){
                return each;
            }
        }
        return null;
    }

    public Edge findEdge(String from, String to){
        for (Edge each : edges){
            if (each.fromCity.value.equals(from) && each.toCity.value.equals(to)){
                return each;
            }
        }
        return null;
    }

    @Override
    public String toString(){
        String retval = "";
        for (Vertex each : vertices){
            retval += each.toString() + "\n";
        }
        return retval;
    }

    private void clearStates(){
        for (Vertex each : vertices){
            each.state = UNVISITED;
        }
    }

    public boolean isConnected(){
        for (Vertex each : vertices){
            if (each.state != State.COMPLETE)
                return false;
        }
        return true;
    }

    public String edgesToString(){
        String retval = "";
        for (Edge each : edges){
            retval += each + "\n";
        }
        return retval;
    }

    public List<String> getPath(String fromCity, String toCity){
        boolean test = Dijkstra(fromCity);
        if (test==false)
            return null;
        List<String> path = getShortestPath(findVertex(toCity));
        return path;
    }

    private boolean Dijkstra(String city){

        if (vertices.isEmpty())
            return false;

        resetDistances();

        Vertex cityVertex = findVertex(city);
        if (cityVertex==null)
            return false;

        cityVertex.minDistance = 0;
        PriorityQueue<Vertex> pq = new PriorityQueue<>();
        pq.add(cityVertex);

        while (!pq.isEmpty()){
            Vertex u = pq.poll();

            for (Vertex v : u.outboundCity){
                Edge e = findEdge(u, v);
                if (e==null)
                    return false;

                int totalDistance = u.minDistance + e.distance;
                if (totalDistance < v.minDistance){
                    pq.remove(v);
                    v.minDistance = totalDistance;
                    v.previous = u;
                    pq.add(v);
                }
            }
        }
        return true;
    }

    private List<String> getShortestPath(Vertex target){

        List<String> path = new ArrayList<>();

        if (target.minDistance==Integer.MAX_VALUE){
            path.add("NO SUCH ROUTE");
            return path;
        }

        for (Vertex v = target; v !=null; v = v.previous){
            path.add(v.value + " : cost : " + v.minDistance);
        }

        Collections.reverse(path);
        return path;
    }

    public Integer getShortesPathInDistance(String fromCity, String toCity){
        boolean test = Dijkstra(fromCity);
        if (test==false)
            return null;
        Integer finalDistance = getShortestPathDistance(findVertex(toCity));
        return finalDistance;
    }


    private Integer getShortestPathDistance(Vertex target){

        Integer outputDistance = 0;

        if (target.minDistance==Integer.MAX_VALUE){
            System.out.println("NO SUCH ROUTE");
            return outputDistance;
        }

        ArrayList<Integer> maxValue = new ArrayList<>();

        for (Vertex v = target; v !=null; v = v.previous){
            maxValue.add(v.minDistance);
        }
        outputDistance = Collections.max(maxValue);

        return outputDistance;
    }

    private void resetDistances(){
        for (Vertex each : vertices){
            each.minDistance = Integer.MAX_VALUE;
            each.previous = null;
        }
    }

    public String getRouteDistance(List<String> routes){

        Integer routeTotalDistance = 0;
        for(int i = 0; i < routes.size() - 1; i++){
            if(findEdge(routes.get(i), routes.get(i + 1)) != null){
                routeTotalDistance += findEdge(routes.get(i), routes.get(i + 1)).distance;
            }
            else{
                return "NO SUCH ROUTE";
            }
        }
        return routeTotalDistance.toString();
    }


    public boolean DepthFirstSearch(){

        if (vertices.isEmpty())
            return false;

        clearStates();
        Vertex root = vertices.get(0);
        if (root==null)
            return false;

        DepthFirstSearch(root);

        return isConnected();
    }

    private void DepthFirstSearch(Vertex v){

        v.state = VISITED;

        for (Vertex city : v.outboundCity){
            if (city.state == UNVISITED){
                DepthFirstSearch(city);
            }
        }
        v.state = State.COMPLETE;
    }


    public void printAllPathsUtil(Vertex v, Vertex d, ArrayList<Vertex> path, ArrayList<String> pathWithSpecificLength){

        v.state = VISITED;
        path.add(v);

        if (v == d) {
            String route = "";
            for (Vertex p : path) {
//                System.out.print("" + p.value + " ");
                route += p.value + "";
            }
            pathWithSpecificLength.add(route);
        }
        else {
            for (Vertex city : v.outboundCity){
                if (city.state == UNVISITED || city.state == COMPLETE) {
                    printAllPathsUtil(city, d, path, pathWithSpecificLength);
                }
            }
        }
        path.remove(v);
        v.state = UNVISITED;
    }

    void printAllPaths(Vertex v, Vertex u){
        clearStates();
        ArrayList<Vertex> path = new ArrayList<>();
        ArrayList<String> allpaths = new ArrayList<>();
        printAllPathsUtil(v, u, path, allpaths);
    }

    public ArrayList<String> specificDistancePathLessThan(Vertex v, Vertex u, Integer distance){

        clearStates();
        ArrayList<Vertex> path = new ArrayList<>();
        ArrayList<String> pathWithSpecificLength = new ArrayList<>();
        ArrayList<String> allpaths = new ArrayList<>();
        printAllPathsUtil(v, u, path, allpaths);

        for(String str: allpaths){

            String[] ary = str.split("");
            String value = getRouteDistance(Arrays.asList(ary));
            Integer splitDistance = Integer.parseInt(value);
            if(splitDistance <= distance){
                pathWithSpecificLength.add(str);
            }
        }
        return pathWithSpecificLength;
    }

    public ArrayList<String> specificLengthPathEqual(Vertex v, Vertex u, Integer length){

        clearStates();
        ArrayList<Vertex> path = new ArrayList<>();
        ArrayList<String> pathWithSpecificLength = new ArrayList<>();
        ArrayList<String> allpaths = new ArrayList<>();
        printAllPathsUtil(v, u, path, allpaths);

        for(String str: allpaths){
            if(str.length() == length){
                pathWithSpecificLength.add(str);
            }
        }
        return pathWithSpecificLength;
    }

    public ArrayList<String> specificLengthPathLessThan(Vertex v, Vertex u, Integer length){

        clearStates();
        ArrayList<Vertex> path = new ArrayList<>();
        ArrayList<String> pathWithSpecificLength = new ArrayList<>();
        ArrayList<String> allpaths = new ArrayList<>();
        printAllPathsUtil(v, u, path, allpaths);

        for(String str: allpaths){
            if(str.length() <= length){
                pathWithSpecificLength.add(str);
            }
        }
        return pathWithSpecificLength;
    }




    public class Edge {

        Integer distance;
        Vertex fromCity;
        Vertex toCity;

        public Edge(Integer distance, Vertex fromCity, Vertex toCity) {
            this.distance = distance;
            this.fromCity = fromCity;
            this.toCity = toCity;
        }

        public Edge(String v1, String v2, int distance) {

            fromCity = findVertex(v1);
            if (fromCity == null) {
                fromCity = new Vertex(v1);
                vertices.add(fromCity);
            }

            toCity = findVertex(v2);
            if (toCity == null) {
                toCity = new Vertex(v2);
                vertices.add(toCity);
            }
            this.distance = distance;

            fromCity.addOutgoing(toCity);
            toCity.addIncoming(fromCity);
        }

        @Override
        public String toString() {
            return "From City " + fromCity.value + " to City" + toCity.value + " distance is: " + distance;
        }
    }
}
