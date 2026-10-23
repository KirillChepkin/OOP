package graph.adjacency;

import graph.Graph;
import graph.Vertex;

import java.util.ArrayList;
import java.util.List;

public class AdjacencyListsGraph<T> implements Graph<T> {
    private final List<AdjacencyGraphVertex<T>> vertexList = new ArrayList<>();

    public List<AdjacencyGraphVertex<T>> getVertexList() {
        return vertexList;
    }

    public int addVertex(T vertex) {
        int index = vertexList.size();
        vertexList.add(new AdjacencyGraphVertex<T>(index, vertex));
        return index;
    }

    public Vertex<T> getVertex(int code) {
        if (vertexList.size() <= code) {
            throw new IndexOutOfBoundsException(
                    "Cannot return requested vertex."
                    + "Vertex with index " + code + " is not in the "
                    + "graph that contains " + vertexList.size() + "vertices");
        }
        return vertexList.get(code);
    }

    public void addEdge(int start, int end) {
        if (vertexList.size() <= start || vertexList.size() <= end) {
            throw new IndexOutOfBoundsException(
                    "Cannot add the edge from " + start + " to " + end + " because there are "
                    + " only " + vertexList.size() + " vertices in the graph."
            );
        }
        vertexList.get(start).getAdjacencyList().add(end);
    }

    public List<Vertex<T>> getNeighbours(int code) {
        if (vertexList.size() <= code) {
            throw new IndexOutOfBoundsException(
                    "Cannot get Neighbours for the vertex. "
                    + "Vertex with index " + code + " is not in the "
                    + "graph that contains " + vertexList.size() + "vertices.");
        }
        List<Vertex<T>> list = new ArrayList<>();
        for (int vertexCode : vertexList.get(code).getAdjacencyList()) {
            list.add(vertexList.get(vertexCode));
        }
        return list;
    }

    public void readGraph() {

    }
}