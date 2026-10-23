package graph;

import java.util.List;

public interface Graph<T> {
    int addVertex(T vertex);
    Vertex<T> getVertex(int code);
    void addEdge(int start, int end);
    List<Vertex<T>> getNeighbours(int code);
    void readGraph();
}