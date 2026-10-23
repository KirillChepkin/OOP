package graph.adjacency.lists;

import graph.Vertex;

import java.util.HashSet;
import java.util.Set;

public class AdjacencyListsVertex<T> extends Vertex<T> {
    private final Set<Integer> adjacencyList = new HashSet<>();

    public Set<Integer> getAdjacencyList() {
        return adjacencyList;
    }

    public AdjacencyListsVertex(int code, T content) {
        super(code, content);
    }

    public void addAdjacentVertex(int code) {
        adjacencyList.add(code);
    }
}