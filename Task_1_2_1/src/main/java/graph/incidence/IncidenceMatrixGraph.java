package graph.incidence;

import graph.BaseGraph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a directed graph using an incidence matrix.
 *
 * <p>Rows represent vertices and columns represent edges. Each matrix entry
 * is {@code -1} for an outgoing edge, {@code 1} for an incoming edge,
 * {@code 2} for a self-loop, or {@code 0} if the vertex is not incident
 * to the edge.</p>
 */
public class IncidenceMatrixGraph extends BaseGraph {

    private final List<List<Integer>> matrix = new ArrayList<>();

    private final List<Integer> vertexKeys = new ArrayList<>();

    private final List<IncidenceMatrixEdge> edges = new ArrayList<>();

    /**
     * Returns an unmodifiable snapshot of the incidence matrix.
     *
     * @return an unmodifiable copy of the matrix.
     */
    public List<List<Integer>> getMatrix() {
        List<List<Integer>> copy = new ArrayList<>();

        for (List<Integer> row : matrix) {
            copy.add(Collections.unmodifiableList(new ArrayList<>(row)));
        }

        return Collections.unmodifiableList(copy);
    }

    /**
     * Returns the public vertex keys in matrix row order.
     *
     * @return an unmodifiable list of vertex keys.
     */
    public List<Integer> getRowKeys() {
        return Collections.unmodifiableList(new ArrayList<>(vertexKeys));
    }

    /**
     * Returns the edges in matrix column order.
     *
     * @return an unmodifiable snapshot of the edge list.
     */
    public List<IncidenceMatrixEdge> getEdges() {
        return Collections.unmodifiableList(new ArrayList<>(edges));
    }

    /**
     * Adds a vertex with the specified public key.
     *
     * @param key the public key of the vertex.
     *
     * @return the internal code assigned to the vertex.
     *
     * @throws IllegalArgumentException if a vertex with this key already exists.
     */
    @Override
    public int addVertex(int key) {
        if (vertexExists(key)) {
            throw new IllegalArgumentException("Duplicate vertex key: " + key);
        }

        int code = getVertexCount();

        matrix.add(new ArrayList<>(Collections.nCopies(edges.size(), 0)));
        vertexKeys.add(key);
        registerVertex(key, code);

        return code;
    }

    /**
     * Removes a vertex and all edges incident to it.
     *
     * @param key the public key of the vertex to remove.
     *
     * @throws java.util.NoSuchElementException if the vertex does not exist.
     */
    @Override
    public void deleteVertex(int key) {
        int row = requireCode(key);

        // Reverse order ensures each deleted column index remains valid.
        for (int column = edges.size() - 1; column >= 0; column--) {
            IncidenceMatrixEdge edge = edges.get(column);

            if (edge.getStartKey() == key || edge.getEndKey() == key) {
                removeColumn(column);
            }
        }

        matrix.remove(row);
        vertexKeys.remove(row);
        unregisterAndCompact(key);
    }

    /**
     * Adds a directed edge between two vertices.
     *
     * <p>If the edge already exists, the graph remains unchanged.</p>
     *
     * @param start the public key of the starting vertex.
     *
     * @param end the public key of the ending vertex.
     *
     * @throws java.util.NoSuchElementException if either vertex does not exist.
     */
    @Override
    public void addEdge(int start, int end) {
        int startRow = requireCode(start);
        int endRow = requireCode(end);

        if (edgeExists(start, end)) {
            return;
        }

        int column = edges.size();
        edges.add(new IncidenceMatrixEdge(column, start, end));

        for (int row = 0; row < matrix.size(); row++) {
            int value = 0;

            if (startRow == endRow && row == startRow) {
                value = 2;
            } else if (row == startRow) {
                value = -1;
            } else if (row == endRow) {
                value = 1;
            }

            matrix.get(row).add(value);
        }
    }

    /**
     * Removes a directed edge between two vertices if it exists.
     *
     * @param start the public key of the starting vertex.
     *
     * @param end the public key of the ending vertex.
     *
     * @throws java.util.NoSuchElementException if either vertex does not exist
     */
    @Override
    public void deleteEdge(int start, int end) {
        requireCode(start);
        requireCode(end);

        int column = findEdgeColumn(start, end);

        if (column >= 0) {
            removeColumn(column);
        }
    }

    /**
     * Checks whether a directed edge exists between two vertices.
     *
     * @param start the public key of the starting vertex.
     *
     * @param end the public key of the ending vertex.
     *
     * @return {@code true} if both vertices exist and the edge exists;
     *         otherwise, {@code false}
     */
    @Override
    public boolean edgeExists(int start, int end) {
        return vertexExists(start) && vertexExists(end)
                && findEdgeColumn(start, end) >= 0;
    }

    /**
     * Returns the outgoing neighbors of a vertex.
     *
     * @param key the public key of the vertex.
     *
     * @return a list of public keys of outgoing neighbors.
     *
     * @throws java.util.NoSuchElementException if the vertex does not exist.
     */
    @Override
    public List<Integer> getNeighbours(int key) {
        int row = requireCode(key);
        List<Integer> neighbours = new ArrayList<>();

        for (int column = 0; column < edges.size(); column++) {
            int value = matrix.get(row).get(column);

            if (value == -1 || value == 2) {
                neighbours.add(edges.get(column).getEndKey());
            }
        }

        return neighbours;
    }

    /**
     * Finds the matrix column corresponding to a directed edge.
     *
     * @param start the public key of the starting vertex
     *
     * @param end the public key of the ending vertex
     *
     * @return the edge column index, or {@code -1} if the edge does not exist
     */
    private int findEdgeColumn(int start, int end) {
        for (int column = 0; column < edges.size(); column++) {
            IncidenceMatrixEdge edge = edges.get(column);

            if (edge.getStartKey() == start && edge.getEndKey() == end) {
                return column;
            }
        }

        return -1;
    }

    /**
     * Removes a matrix column and updates the codes of the remaining edges.
     *
     * @param column the index of the column to remove
     */
    private void removeColumn(int column) {
        edges.remove(column);

        for (List<Integer> row : matrix) {
            row.remove(column);
        }

        for (int i = column; i < edges.size(); i++) {
            edges.get(i).setCode(i);
        }
    }
}