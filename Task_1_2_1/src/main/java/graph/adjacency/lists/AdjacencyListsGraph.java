package graph.adjacency.lists;

import graph.Graph;
import graph.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjacencyListsGraph<T> implements Graph<T> {
    private final Map<Integer, AdjacencyListsVertex<T>> vertexMap = new HashMap<>();

    public Map<Integer, AdjacencyListsVertex<T>> getVertexMap() {
        return vertexMap;
    }

    @Override
    public int addVertex(T vertex) {
        int index = 0;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (!vertexMap.containsKey(i)) {
                index = i;
                break;
            }
        }
        vertexMap.put(index, new AdjacencyListsVertex<T>(index, vertex));
        return index;
    }

    @Override
    public void deleteVertex(int code) {
        if (!vertexMap.containsKey(code)) {
            throw new IndexOutOfBoundsException(
                    "Cannot delete requested vertex with the index" + code
                            + " from the graph. It's not in it.");
        }
        vertexMap.remove(code);
        for (Integer vertexCode : vertexMap.keySet()) {
            vertexMap.get(vertexCode).getAdjacencySet().remove(code);
        }
    }

    @Override
    public Vertex<T> getVertex(int code) {
        if (!vertexMap.containsKey(code)) {
            throw new IndexOutOfBoundsException(
                    "Cannot return requested vertex with the index" + code
                    + ". It's not in the graph.");
        }
        return vertexMap.get(code);
    }

    @Override
    public void addEdge(int start, int end) {
        if (!vertexMap.containsKey(start) && !vertexMap.containsKey(end)) {
            throw new IndexOutOfBoundsException(
                    "Cannot add the edge from " + start + " to " + end
                    + " because one of these vertices is not in the graph.");
        }
        vertexMap.get(start).getAdjacencySet().add(end);
    }

    @Override
    public void deleteEdge(int start, int end) {
        if (!vertexMap.containsKey(start) && !vertexMap.containsKey(end)) {
            throw new IndexOutOfBoundsException(
                    "Cannot delete the edge from " + start + " to " + end
                    + " because one of these vertices is not in the graph.");
        }
        vertexMap.get(start).getAdjacencySet().remove(end);
    }

    @Override
    public List<Vertex<T>> getNeighbours(int code) {
        if (!vertexMap.containsKey(code)) {
            throw new IndexOutOfBoundsException(
                    "Cannot find Neighbours for the vertex with index "
                    + code + " because is not in the graph.");
        }
        List<Vertex<T>> list = new ArrayList<>();
        for (int vertexCode : vertexMap.get(code).getAdjacencySet()) {
            list.add(vertexMap.get(vertexCode));
        }
        return list;
    }

    @Override
    public void readGraph() {

    }
}