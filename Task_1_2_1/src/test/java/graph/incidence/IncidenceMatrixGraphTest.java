package graph.incidence;

import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IncidenceMatrixGraphTest {
    @Test
    void matrixStoresVerticesAsRowsAndEdgesAsColumns() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        graph.addVertex(10);
        graph.addVertex(20);
        graph.addVertex(30);
        graph.addEdge(10, 20);
        graph.addEdge(20, 30);
        assertEquals(List.of(
                List.of(-1, 0),
                List.of(1, -1),
                List.of(0, 1)), graph.getMatrix());
    }

    @Test
    void selfLoopUsesTwoAndIsOutgoingNeighbour() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        graph.addVertex(8);
        graph.addEdge(8, 8);
        assertEquals(List.of(List.of(2)), graph.getMatrix());
        assertEquals(List.of(8), graph.getNeighbours(8));
    }

    @Test
    void reverseEdgeCreatesSeparateColumn() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);
        graph.addEdge(2, 1);
        assertEquals(List.of(List.of(-1, 1), List.of(1, -1)), graph.getMatrix());
        assertEquals(2, graph.getEdges().size());
    }

    @Test
    void edgeDeletionCompactsColumnsAndEdgeCodes() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        graph.addVertex(10);
        graph.addVertex(20);
        graph.addVertex(30);
        graph.addEdge(10, 20);
        graph.addEdge(20, 30);
        graph.addEdge(30, 10);
        graph.deleteEdge(20, 30);
        assertEquals(List.of(
                List.of(-1, 1),
                List.of(1, 0),
                List.of(0, -1)), graph.getMatrix());
        assertEquals(0, graph.getEdges().get(0).getCode());
        assertEquals(1, graph.getEdges().get(1).getCode());
        assertEquals(30, graph.getEdges().get(1).getStartKey());
        assertEquals(10, graph.getEdges().get(1).getEndKey());
    }

    @Test
    void deletingMiddleVertexRemovesIncidentColumnsAndCompactsRows() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        graph.addVertex(10);
        graph.addVertex(20);
        graph.addVertex(30);
        graph.addVertex(40);
        graph.addEdge(10, 20);
        graph.addEdge(20, 30);
        graph.addEdge(30, 40);
        graph.addEdge(40, 30);
        graph.addEdge(20, 20);
        graph.deleteVertex(20);
        assertEquals(List.of(10, 30, 40), graph.getRowKeys());
        assertEquals(List.of(
                List.of(0, 0),
                List.of(-1, 1),
                List.of(1, -1)), graph.getMatrix());
        assertTrue(graph.edgeExists(30, 40));
        assertTrue(graph.edgeExists(40, 30));
        assertFalse(graph.edgeExists(10, 20));
        assertEquals(3, graph.getVertexCount());
        assertEquals(3, graph.addVertex(50));
    }

    @Test
    void emptyColumnRowsOnAddingVerticesWithoutEdges() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        graph.addVertex(10);
        graph.addVertex(20);
        assertEquals(List.of(List.of(), List.of()), graph.getMatrix());
        assertEquals(List.of(10, 20), graph.getRowKeys());
    }

    @Test
    void matrixGetterCannotChangeGraph() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();
        graph.addVertex(10);
        graph.addEdge(10, 10);
        assertThrows(UnsupportedOperationException.class,
                () -> graph.getMatrix().get(0).set(0, 0));
        assertEquals(List.of(List.of(2)), graph.getMatrix());
    }
}
