package graph;

import java.util.List;

/**
 * This interface declares methods that every class representing a graph should implement.
 * They are sufficient for topological sort algorithms.
 */
public interface Graph {
    int addVertex(int key);

    void deleteVertex(int key);

    boolean vertexExists(int key);

    List<Integer> getVertices();

    int getVertexCount();

    void addEdge(int start, int end);

    void deleteEdge(int start, int end);

    boolean edgeExists(int start, int end);

    List<Integer> getNeighbours(int code);
}