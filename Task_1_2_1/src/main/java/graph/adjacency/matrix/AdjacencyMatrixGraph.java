package graph.adjacency.matrix;

import graph.BaseGraph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a directed graph using an adjacency matrix.
 *
 * <p>Rows and columns are indexed by compact internal vertex codes,
 * which are mapped to public vertex keys by {@link BaseGraph}.</p>
 */
public class AdjacencyMatrixGraph extends BaseGraph {

    private final List<List<Boolean>> matrix = new ArrayList<>();

    /**
     * Returns an unmodifiable snapshot of the adjacency matrix.
     *
     * <p>Both the outer list and its rows are unmodifiable, so modifying
     * the returned matrix cannot change the graph.</p>
     *
     * @return an unmodifiable copy of the adjacency matrix
     */
    public List<List<Boolean>> getMatrix() {
        List<List<Boolean>> copy = new ArrayList<>();

        for (List<Boolean> row : matrix) {
            copy.add(Collections.unmodifiableList(new ArrayList<>(row)));
        }

        return Collections.unmodifiableList(copy);
    }

    /**
     * Adds a vertex with the specified public key.
     *
     * @param key the public key of the vertex
     * @return the internal code assigned to the vertex
     * @throws IllegalArgumentException if a vertex with this key already exists
     */
    @Override
    public int addVertex(int key) {
        if (vertexExists(key)) {
            throw new IllegalArgumentException("Duplicate vertex key: " + key);
        }

        int code = getVertexCount();

        for (List<Boolean> row : matrix) {
            row.add(false);
        }

        matrix.add(new ArrayList<>(Collections.nCopies(code + 1, false)));
        registerVertex(key, code);

        return code;
    }

    /**
     * Removes a vertex and all edges connected to it.
     *
     * @param key the public key of the vertex to remove
     * @throws java.util.NoSuchElementException if the vertex does not exist
     */
    @Override
    public void deleteVertex(int key) {
        int code = requireCode(key);

        matrix.remove(code);

        for (List<Boolean> row : matrix) {
            row.remove(code);
        }

        unregisterAndCompact(key);
    }

    /**
     * Adds a directed edge between two vertices.
     *
     * @param start the public key of the starting vertex
     * @param end the public key of the ending vertex
     * @throws java.util.NoSuchElementException if either vertex does not exist
     */
    @Override
    public void addEdge(int start, int end) {
        matrix.get(requireCode(start)).set(requireCode(end), true);
    }

    /**
     * Removes a directed edge between two vertices.
     *
     * @param start the public key of the starting vertex
     * @param end the public key of the ending vertex
     * @throws java.util.NoSuchElementException if either vertex does not exist
     */
    @Override
    public void deleteEdge(int start, int end) {
        matrix.get(requireCode(start)).set(requireCode(end), false);
    }

    /**
     * Checks whether a directed edge exists between two vertices.
     *
     * @param start the public key of the starting vertex
     * @param end the public key of the ending vertex
     * @return {@code true} if both vertices exist and the edge exists;
     *         otherwise, {@code false}
     */
    @Override
    public boolean edgeExists(int start, int end) {
        Integer startCode = keyToCode.get(start);
        Integer endCode = keyToCode.get(end);

        return startCode != null && endCode != null
                && matrix.get(startCode).get(endCode);
    }

    /**
     * Returns the outgoing neighbors of a vertex.
     *
     * @param key the public key of the vertex
     * @return a list of public keys of outgoing neighbors
     * @throws java.util.NoSuchElementException if the vertex does not exist
     */
    @Override
    public List<Integer> getNeighbours(int key) {
        int code = requireCode(key);
        List<Integer> neighbours = new ArrayList<>();

        for (int column = 0; column < matrix.size(); column++) {
            if (matrix.get(code).get(column)) {
                neighbours.add(codeToKey.get(column));
            }
        }

        return neighbours;
    }
}