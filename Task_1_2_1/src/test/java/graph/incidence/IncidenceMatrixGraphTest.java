package graph.incidence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import graph.Vertex;
import java.util.List;
import org.junit.jupiter.api.Test;

class IncidenceMatrixGraphTest {
    @Test
    void addingVerticesCreatesRowsWithOneEntryPerExistingEdge() {
        IncidenceMatrixGraph<String> graph = new IncidenceMatrixGraph<>();
        assertEquals(0, graph.addVertex("A"));
        assertEquals(1, graph.addVertex("B"));
        graph.addEdge(0, 1);
        assertEquals(2, graph.addVertex("C"));

        assertEquals(List.of(List.of(-1), List.of(1), List.of(0)), graph.getMatrix());
        assertEquals("C", graph.getVertex(2).getContent());
        assertEquals(2, graph.getVertex(2).getCode());
    }

    @Test
    void edgesAreDirectedAndEachEdgeCreatesAColumn() {
        IncidenceMatrixGraph<String> graph = makeGraph("A", "B", "C");
        graph.addEdge(0, 1);
        graph.addEdge(1, 0);
        graph.addEdge(1, 2);

        assertEquals(List.of(List.of(-1, 1, 0), List.of(1, -1, -1),
                List.of(0, 0, 1)), graph.getMatrix());
        assertEquals(List.of("B"), contents(graph.getNeighbours(0)));
        assertEquals(List.of("A", "C"), contents(graph.getNeighbours(1)));
        assertTrue(graph.getNeighbours(2).isEmpty());
    }

    @Test
    void duplicateEdgesAreIgnored() {
        IncidenceMatrixGraph<Integer> graph = makeGraph(10, 20);
        graph.addEdge(0, 1);
        graph.addEdge(0, 1);
        assertEquals(1, graph.getEdges().size());
        assertEquals(List.of(List.of(-1), List.of(1)), graph.getMatrix());
    }

    @Test
    void selfLoopHasUniqueMarkerAndAppearsInOutgoingNeighbours() {
        IncidenceMatrixGraph<String> graph = makeGraph("A", "B");
        graph.addEdge(1, 1);
        assertEquals(List.of(List.of(0), List.of(2)), graph.getMatrix());
        assertEquals(List.of("B"), contents(graph.getNeighbours(1)));
        graph.deleteEdge(1, 1);
        assertEquals(List.of(List.of(), List.of()), graph.getMatrix());
    }

    @Test
    void deletingAnEdgeRemovesItsColumnAndRenumbersRemainingEdges() {
        IncidenceMatrixGraph<String> graph = makeGraph("A", "B", "C");
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(2, 1);
        IncidenceMatrixEdge<String> retained = graph.getEdges().get(2);

        graph.deleteEdge(0, 2);
        assertEquals(2, graph.getEdges().size());
        assertSame(retained, graph.getEdges().get(1));
        assertEquals(1, retained.getCode());
        assertEquals(List.of(List.of(-1, 0), List.of(1, 1), List.of(0, -1)),
                graph.getMatrix());
        graph.deleteEdge(0, 2);
        assertEquals(2, graph.getEdges().size());
    }

    @Test
    void deletingVertexRemovesAllIncidentColumnsAndRenumbersRemainingVertices() {
        IncidenceMatrixGraph<String> graph = makeGraph("A", "B", "C", "D");
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 0);
        graph.addEdge(1, 1);
        IncidenceMatrixVertex<String> c = graph.getVertex(2);
        IncidenceMatrixEdge<String> edgeFromCtoD = graph.getEdges().get(2);

        graph.deleteVertex(1);

        assertEquals(3, graph.getVertices().size());
        assertSame(c, graph.getVertex(1));
        assertEquals(1, c.getCode());
        assertSame(edgeFromCtoD, graph.getEdges().get(0));
        assertEquals(0, edgeFromCtoD.getCode());
        assertEquals(List.of(List.of(0, 1), List.of(-1, 0), List.of(1, -1)),
                graph.getMatrix());
        assertEquals(List.of("D"), contents(graph.getNeighbours(1)));
        assertEquals(List.of("A"), contents(graph.getNeighbours(2)));
        assertEquals(3, graph.addVertex("E"));
    }

    @Test
    void edgesStoreReferencesToActualVertices() {
        IncidenceMatrixGraph<String> graph = makeGraph("A", "B");
        graph.addEdge(0, 1);
        IncidenceMatrixEdge<String> edge = graph.getEdges().get(0);
        assertEquals(0, edge.getCode());
        assertSame(graph.getVertex(0), edge.getStartVertex());
        assertSame(graph.getVertex(1), edge.getEndVertex());
    }

    @Test
    void invalidVertexCodesAreRejected() {
        IncidenceMatrixGraph<String> graph = makeGraph("A", "B");
        assertThrows(IndexOutOfBoundsException.class, () -> graph.getVertex(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> graph.getVertex(2));
        assertThrows(IndexOutOfBoundsException.class, () -> graph.getNeighbours(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> graph.deleteVertex(2));
        assertThrows(IndexOutOfBoundsException.class, () -> graph.addEdge(0, 2));
        assertThrows(IndexOutOfBoundsException.class, () -> graph.addEdge(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> graph.deleteEdge(0, 2));
    }

    @Test
    void collectionGettersCannotMutateInternalRepresentation() {
        IncidenceMatrixGraph<String> graph = makeGraph("A", "B");
        graph.addEdge(0, 1);
        assertThrows(UnsupportedOperationException.class,
                () -> graph.getVertices().clear());
        assertThrows(UnsupportedOperationException.class,
                () -> graph.getEdges().clear());
        assertThrows(UnsupportedOperationException.class,
                () -> graph.getMatrix().get(0).set(0, 50));
        assertFalse(graph.getMatrix().isEmpty());
    }

    @SafeVarargs
    private static <T> IncidenceMatrixGraph<T> makeGraph(T... values) {
        IncidenceMatrixGraph<T> graph = new IncidenceMatrixGraph<>();
        for (T value : values) {
            graph.addVertex(value);
        }
        return graph;
    }

    private static <T> List<T> contents(List<Vertex<T>> vertices) {
        return vertices.stream().map(Vertex::getContent).toList();
    }
}
