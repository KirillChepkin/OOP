package graph.incidence;

import graph.Graph;
import graph.Vertex;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Directed graph backed by an incidence matrix: rows are vertices and
 * columns are edges. -1 indicates an outgoing edge, +1 an incoming edge,
 * 2 a self-loop, and 0 no incidence. Parallel edges are not stored.
 */
public class IncidenceMatrixGraph<T> implements Graph<T> {
    private final List<List<Integer>> matrix = new ArrayList<>();
    private final List<IncidenceMatrixVertex<T>> vertices = new ArrayList<>();
    private final List<IncidenceMatrixEdge<T>> edges = new ArrayList<>();

    /** Returns an immutable snapshot, so callers cannot corrupt the matrix. */
    public List<List<Integer>> getMatrix() {
        List<List<Integer>> copy = new ArrayList<>();
        for (List<Integer> row : matrix) {
            copy.add(Collections.unmodifiableList(new ArrayList<>(row)));
        }
        return Collections.unmodifiableList(copy);
    }

    public List<IncidenceMatrixVertex<T>> getVertices() {
        return Collections.unmodifiableList(vertices);
    }

    public List<IncidenceMatrixEdge<T>> getEdges() {
        return Collections.unmodifiableList(edges);
    }

    @Override
    public int addVertex(T vertex) {
        int code = vertices.size();
        vertices.add(new IncidenceMatrixVertex<>(code, vertex));
        matrix.add(new ArrayList<>(Collections.nCopies(edges.size(), 0)));
        return code;
    }

    @Override
    public void deleteVertex(int code) {
        checkVertexCode(code);
        IncidenceMatrixVertex<T> removed = vertices.get(code);

        // Remove all incident columns from right to left, keeping indices valid.
        for (int column = edges.size() - 1; column >= 0; column--) {
            IncidenceMatrixEdge<T> edge = edges.get(column);
            if (edge.getStartVertex() == removed || edge.getEndVertex() == removed) {
                removeEdgeColumn(column);
            }
        }
        renumberEdgesFrom(0);

        vertices.remove(code);
        matrix.remove(code);
        for (int i = code; i < vertices.size(); i++) {
            vertices.get(i).setCode(i);
        }
    }

    @Override
    public IncidenceMatrixVertex<T> getVertex(int code) {
        checkVertexCode(code);
        return vertices.get(code);
    }

    @Override
    public void addEdge(int start, int end) {
        checkVertexCode(start);
        checkVertexCode(end);
        if (findEdgeColumn(start, end) >= 0) {
            return;  // Same semantics as Set/Boolean adjacency representations.
        }

        int column = edges.size();
        edges.add(new IncidenceMatrixEdge<>(column, vertices.get(start), vertices.get(end)));
        for (int row = 0; row < vertices.size(); row++) {
            int value = 0;
            if (start == end && row == start) {
                value = 2;
            } else if (row == start) {
                value = -1;
            } else if (row == end) {
                value = 1;
            }
            matrix.get(row).add(value);
        }
    }

    @Override
    public void deleteEdge(int start, int end) {
        checkVertexCode(start);
        checkVertexCode(end);
        int column = findEdgeColumn(start, end);
        if (column >= 0) {
            removeEdgeColumn(column);
            renumberEdgesFrom(column);
        }
    }

    @Override
    public List<Vertex<T>> getNeighbours(int code) {
        checkVertexCode(code);
        List<Vertex<T>> neighbours = new ArrayList<>();
        for (int column = 0; column < edges.size(); column++) {
            int incidence = matrix.get(code).get(column);
            if (incidence == -1 || incidence == 2) {
                neighbours.add(edges.get(column).getEndVertex());
            }
        }
        return neighbours;
    }

    @Override
    public void readGraph() {
        // Intentionally left unimplemented, as in the existing graph classes.
    }

    private void checkVertexCode(int code) {
        if (code < 0 || code >= vertices.size()) {
            throw new IndexOutOfBoundsException("Vertex with code " + code + " does not exist.");
        }
    }

    private int findEdgeColumn(int start, int end) {
        IncidenceMatrixVertex<T> startVertex = vertices.get(start);
        IncidenceMatrixVertex<T> endVertex = vertices.get(end);
        for (int column = 0; column < edges.size(); column++) {
            IncidenceMatrixEdge<T> edge = edges.get(column);
            if (edge.getStartVertex() == startVertex && edge.getEndVertex() == endVertex) {
                return column;
            }
        }
        return -1;
    }

    private void removeEdgeColumn(int column) {
        edges.remove(column);
        for (List<Integer> row : matrix) {
            row.remove(column);
        }
    }

    private void renumberEdgesFrom(int firstColumn) {
        for (int i = firstColumn; i < edges.size(); i++) {
            edges.get(i).setCode(i);
        }
    }
}
