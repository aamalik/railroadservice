package railroad;

import java.util.ArrayList;
import java.util.List;

public class Vertex implements Comparable<Vertex> {

    String value;

    // variables for Dijkstra Tree
    Vertex previous = null;
    int minDistance = Integer.MAX_VALUE;

    List<Vertex> inboundCity;
    List<Vertex> outboundCity;
    State state;

    public Vertex(String value){
        this.value = value;
        inboundCity = new ArrayList<>();
        outboundCity = new ArrayList<>();
        state = State.UNVISITED;
    }

    @Override
    public int compareTo(Vertex other){
        return Integer.compare(minDistance, other.minDistance);
    }

    public void addIncoming(Vertex v1){
        inboundCity.add(v1);
    }
    public void addOutgoing(Vertex v2){
        outboundCity.add(v2);
    }

    @Override
    public String toString(){

        StringBuilder outputString = new StringBuilder();

        outputString.append("City ");
        outputString.append(value + " => ");
        outputString.append("Inbound Cities: ");

        for (Vertex city : inboundCity){
            outputString.append(city.value + " ");
        }
        outputString.append("=> Outbound Cities: ");

        for (Vertex city : outboundCity){
            outputString.append(city.value + " ");
        }

        return outputString.toString();
    }
}
