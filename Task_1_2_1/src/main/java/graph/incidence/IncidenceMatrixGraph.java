package graph.incidence;

import graph.BaseGraph;
import graph.Graph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Directed incidence matrix: rows are vertex codes and columns are edges. */
public class IncidenceMatrixGraph extends BaseGraph {
    // -1 = outgoing, +1 = incoming, 2 = self-loop, 0 = not incident.
    private final List<List<Integer>> matrix = new ArrayList<>();
    // Row index -> public vertex key; kept aligned with the incidence matrix.
    private final List<Integer> vertexKeys = new ArrayList<>();
    // Column index -> directed edge.
    private final List<IncidenceMatrixEdge> edges = new ArrayList<>();

    /** Defensive snapshot of the incidence matrix. */
    public List<List<Integer>> getMatrix() {
        List<List<Integer>> copy = new ArrayList<>();
        for (List<Integer> row : matrix) {
            copy.add(Collections.unmodifiableList(new ArrayList<>(row)));
        }
        return Collections.unmodifiableList(copy);
    }

    /** Vertex keys in incidence-matrix row order. */
    public List<Integer> getRowKeys() {
        return Collections.unmodifiableList(new ArrayList<>(vertexKeys));
    }

    /** Snapshot of the edges, in matrix-column order. */
    public List<IncidenceMatrixEdge> getEdges() {
        return Collections.unmodifiableList(new ArrayList<>(edges));
    }

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

    @Override
    public void deleteVertex(int key) {
        int row = requireCode(key);
        // Reverse order ensures each deleted column index is still valid.
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

    @Override
    public void deleteEdge(int start, int end) {
        requireCode(start);
        requireCode(end);
        int column = findEdgeColumn(start, end);
        if (column >= 0) {
            removeColumn(column);
        }
    }

    @Override
    public boolean edgeExists(int start, int end) {
        return vertexExists(start) && vertexExists(end)
                && findEdgeColumn(start, end) >= 0;
    }

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

    private int findEdgeColumn(int start, int end) {
        for (int column = 0; column < edges.size(); column++) {
            IncidenceMatrixEdge edge = edges.get(column);
            if (edge.getStartKey() == start && edge.getEndKey() == end) {
                return column;
            }
        }
        return -1;
    }

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
