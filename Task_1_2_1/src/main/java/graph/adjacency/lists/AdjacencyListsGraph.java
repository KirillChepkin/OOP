package graph.adjacency.lists;

import graph.Graph;
import graph.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjacencyListsGraph<T> implements Graph<T> {
    private final Map<Integer, AdjacencyListsVertex<T>> vertexList = new HashMap<>();

    public Map<Integer, AdjacencyListsVertex<T>> getVertexList() {
        return vertexList;
    }

    @Override
    public int addVertex(T vertex) {
        int index = 0;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (!vertexList.containsKey(i)) {
                index = i;
                break;
            }
        }
        vertexList.put(index, new AdjacencyListsVertex<T>(index, vertex));
        return index;
    }

    @Override
    public void deleteVertex(int code) {
        if (!vertexList.containsKey(code)) {
            throw new IndexOutOfBoundsException(
                    "Cannot delete requested vertex with the index" + code
                            + " from the graph. It's not in it.");
        }
        vertexList.remove(code);
        for (Integer vertexCode : vertexList.keySet()) {
            vertexList.get(vertexCode).getAdjacencyList().remove(code);
        }
    }

    @Override
    public Vertex<T> getVertex(int code) {
        if (!vertexList.containsKey(code)) {
            throw new IndexOutOfBoundsException(
                    "Cannot return requested vertex with the index" + code
                    + ". It's not in the graph.");
        }
        return vertexList.get(code);
    }

    @Override
    public void addEdge(int start, int end) {
        if (!vertexList.containsKey(start) && !vertexList.containsKey(end)) {
            throw new IndexOutOfBoundsException(
                    "Cannot add the edge from " + start + " to " + end
                    + " because one of these vertices is not in the graph.");
        }
        vertexList.get(start).getAdjacencyList().add(end);
    }

    @Override
    public List<Vertex<T>> getNeighbours(int code) {
        if (!vertexList.containsKey(code)) {
            throw new IndexOutOfBoundsException(
                    "Cannot find Neighbours for the vertex with index "
                    + code + " because is not in the graph.");
        }
        List<Vertex<T>> list = new ArrayList<>();
        for (int vertexCode : vertexList.get(code).getAdjacencyList()) {
            list.add(vertexList.get(vertexCode));
        }
        return list;
    }

    @Override
    public void readGraph() {

    }
}