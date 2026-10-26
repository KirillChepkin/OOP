package graph.adjacency.matrix;

import graph.BaseGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Directed adjacency matrix: row and column numbers are compact internal codes. */
public class AdjacencyMatrixGraph extends BaseGraph {
    private final List<List<Boolean>> matrix = new ArrayList<>();

    /** Defensive snapshot; modifying it cannot corrupt the graph. */
    public List<List<Boolean>> getMatrix() {
        List<List<Boolean>> copy = new ArrayList<>();
        for (List<Boolean> row : matrix) {
            copy.add(Collections.unmodifiableList(new ArrayList<>(row)));
        }
        return Collections.unmodifiableList(copy);
    }

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

    @Override
    public void deleteVertex(int key) {
        int code = requireCode(key);
        matrix.remove(code);
        for (List<Boolean> row : matrix) {
            row.remove(code);
        }
        unregisterAndCompact(key);
    }

    @Override
    public void addEdge(int start, int end) {
        matrix.get(requireCode(start)).set(requireCode(end), true);
    }

    @Override
    public void deleteEdge(int start, int end) {
        matrix.get(requireCode(start)).set(requireCode(end), false);
    }

    @Override
    public boolean edgeExists(int start, int end) {
        Integer startCode = keyToCode.get(start);
        Integer endCode = keyToCode.get(end);
        return startCode != null && endCode != null
                && matrix.get(startCode).get(endCode);
    }

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
