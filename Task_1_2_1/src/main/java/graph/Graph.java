package graph;

import java.util.List;

/**
 * This interface declares methods that every class representing a graph should implement.
 * They are sufficient for topological sort algorithms.
 *
 * @param <T> Vertices of the graph are supposed to store objects of some class T.
 */
public interface Graph<T> {
    int addVertex(T vertex);

    void deleteVertex(int code);

    Vertex<T> getVertex(int code);

    void addEdge(int start, int end);

    void deleteEdge(int start, int end);

    List<Vertex<T>> getNeighbours(int code);

    void readGraph();
}