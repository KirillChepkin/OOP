package graph.adjacency.lists;

import graph.Graph;
import graph.Vertex;

import java.util.*;

public class AdjacencyListsGraph<T> implements Graph<T> {
    private final Map<Integer, AdjacencyListsVertex<T>> vertexMap = new HashMap<>();

    public Map<Integer, AdjacencyListsVertex<T>> getVertexMap() {
        return vertexMap;
    }

    private long nextCode = 0;

    private final PriorityQueue<Integer> freeCodes = new PriorityQueue<>();

    @Override
    public int addVertex(T vertex) {
//        int index = 0;
//        for (int i = 0; i < Integer.MAX_VALUE; i++) {
//            if (!vertexMap.containsKey(i)) {
//                index = i;
//                break;
//            }
//        }

        int code;
        if (!freeCodes.isEmpty()) {
            code = freeCodes.poll();
        } else {
            if (nextCode > Integer.MAX_VALUE) {
                throw new IllegalStateException("No more vertex codes available.");
            }
            code = (int) nextCode++;
        }
        vertexMap.put(code, new AdjacencyListsVertex<T>(code, vertex));
        return code;
    }

    @Override
    public void deleteVertex(int code) {
        if (!vertexMap.containsKey(code)) {
            throw new IndexOutOfBoundsException(
                    "Cannot delete requested vertex with the index" + code
                            + " from the graph. It's not in it.");
        }
        vertexMap.remove(code);
        for (AdjacencyListsVertex<T> vertex : vertexMap.values()) {
            vertex.getAdjacencySet().remove(code);
        }
        freeCodes.add(code);
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
        if (start < 0 || end < 0 || !vertexMap.containsKey(start) || !vertexMap.containsKey(end)) {
            throw new IndexOutOfBoundsException(
                    "Cannot add the edge from " + start + " to " + end
                    + " because one of these vertices is not in the graph.");
        }
        vertexMap.get(start).getAdjacencySet().add(end);
    }

    @Override
    public void deleteEdge(int start, int end) {
        if (start < 0 || end < 0 || !vertexMap.containsKey(start) || !vertexMap.containsKey(end)) {
            throw new IndexOutOfBoundsException(
                    "Cannot delete the edge from " + start + " to " + end
                    + " because one of these vertices is not in the graph.");
        }
        vertexMap.get(start).getAdjacencySet().remove(end);
    }

    @Override
    public List<Vertex<T>> getNeighbours(int code) {
        if (code < 0 || !vertexMap.containsKey(code)) {
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