package graph.adjacency;

import graph.Vertex;

import java.util.HashSet;
import java.util.Set;

public class AdjacencyGraphVertex<T> extends Vertex<T> {
    private final Set<Integer> adjacencyList = new HashSet<Integer>();

    public Set<Integer> getAdjacencyList() {
        return adjacencyList;
    }

    public AdjacencyGraphVertex(int code, T content) {
        super(code, content);
    }

    public void addAdjacentVertex(int code) {
        adjacencyList.add(code);
    }
}