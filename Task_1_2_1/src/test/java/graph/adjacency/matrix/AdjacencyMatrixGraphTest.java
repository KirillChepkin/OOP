package graph.adjacency.matrix;

import java.util.List;

import graph.adjacency.matrix.AdjacencyMatrixGraph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AdjacencyMatrixGraphTest {
    @Test
    void matrixGrowsAsSquareAndUsesInternalCodes() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph();
        graph.addVertex(100);
        graph.addVertex(-200);
        graph.addVertex(300);
        graph.addEdge(100, 300);
        assertEquals(List.of(
                List.of(false, false, true),
                List.of(false, false, false),
                List.of(false, false, false)), graph.getMatrix());
    }

    @Test
    void removingMiddleVertexShrinksBothDimensionsAndCompactsCodes() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(3, 1);
        graph.deleteVertex(2);
        assertEquals(List.of(List.of(false, false), List.of(true, false)),
                graph.getMatrix());
        assertTrue(graph.edgeExists(3, 1));
        assertEquals(2, graph.addVertex(4));
    }

    @Test
    void matrixGetterCannotChangeGraph() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph();
        graph.addVertex(1);
        assertThrows(UnsupportedOperationException.class,
                () -> graph.getMatrix().get(0).set(0, true));
        assertFalse(graph.edgeExists(1, 1));
    }
}
