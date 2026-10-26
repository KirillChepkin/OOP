package graph;

import graph.incidence.IncidenceMatrixGraph;
import graph.adjacency.lists.AdjacencyListsGraph;
import graph.adjacency.matrix.AdjacencyMatrixGraph;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Every Graph implementation must obey these exact same public semantics. */
class GraphContractTest {
    static Stream<Arguments> implementations() {
        return Stream.of(
                Arguments.of("adjacency lists", (Supplier<Graph>) AdjacencyListsGraph::new),
                Arguments.of("adjacency matrix", (Supplier<Graph>) AdjacencyMatrixGraph::new),
                Arguments.of("incidence matrix", (Supplier<Graph>) IncidenceMatrixGraph::new));
    }

    @ParameterizedTest(name = "{0}: empty graph")
    @MethodSource("implementations")
    void emptyGraph(String name, Supplier<Graph> factory) {
        Graph graph = factory.get();
        assertEquals(0, graph.getVertexCount());
        assertTrue(graph.getVertices().isEmpty());
        assertFalse(graph.vertexExists(0));
        assertFalse(graph.edgeExists(0, 1));
    }

    @ParameterizedTest(name = "{0}: arbitrary integer keys")
    @MethodSource("implementations")
    void arbitraryIntegerKeys(String name, Supplier<Graph> factory) {
        Graph graph = factory.get();
        assertEquals(0, graph.addVertex(100));
        assertEquals(1, graph.addVertex(-55));
        assertEquals(2, graph.addVertex(Integer.MIN_VALUE));
        assertEquals(3, graph.addVertex(Integer.MAX_VALUE));
        assertEquals(new HashSet<>(List.of(Integer.MIN_VALUE, -55, 100, Integer.MAX_VALUE)),
                new HashSet<>(graph.getVertices()));
        assertEquals(4, graph.getVertexCount());
        assertTrue(graph.vertexExists(-55));
        assertFalse(graph.vertexExists(-56));
    }

    @ParameterizedTest(name = "{0}: reject duplicate vertex")
    @MethodSource("implementations")
    void duplicateVertexRejectedWithoutChanges(String name, Supplier<Graph> factory) {
        Graph graph = factory.get();
        graph.addVertex(123);
        assertThrows(IllegalArgumentException.class, () -> graph.addVertex(123));
        assertEquals(1, graph.getVertexCount());
        assertEquals(List.of(123), graph.getVertices());
        assertEquals(1, graph.addVertex(456));
    }

    @ParameterizedTest(name = "{0}: delete missing vertex")
    @MethodSource("implementations")
    void deleteMissingVertexThrows(String name, Supplier<Graph> factory) {
        Graph graph = factory.get();
        graph.addVertex(10);
        assertThrows(NoSuchElementException.class, () -> graph.deleteVertex(11));
        assertEquals(List.of(10), graph.getVertices());
    }

    @ParameterizedTest(name = "{0}: directed edge")
    @MethodSource("implementations")
    void directedEdges(String name, Supplier<Graph> factory) {
        Graph graph = factory.get();
        graph.addVertex(10);
        graph.addVertex(20);
        graph.addEdge(10, 20);
        assertTrue(graph.edgeExists(10, 20));
        assertFalse(graph.edgeExists(20, 10));
        assertEquals(List.of(20), graph.getNeighbours(10));
        assertTrue(graph.getNeighbours(20).isEmpty());
    }

    @ParameterizedTest(name = "{0}: duplicate edges")
    @MethodSource("implementations")
    void repeatedEdgesNotDuplicated(String name, Supplier<Graph> factory) {
        Graph graph = factory.get();
        graph.addVertex(4);
        graph.addVertex(8);
        graph.addEdge(4, 8);
        graph.addEdge(4, 8);
        graph.addEdge(4, 8);
        assertEquals(List.of(8), graph.getNeighbours(4));
        graph.deleteEdge(4, 8);
        assertFalse(graph.edgeExists(4, 8));
        assertTrue(graph.getNeighbours(4).isEmpty());
    }

    @ParameterizedTest(name = "{0}: self-loop")
    @MethodSource("implementations")
    void selfLoop(String name, Supplier<Graph> factory) {
        Graph graph = factory.get();
        graph.addVertex(-3);
        graph.addEdge(-3, -3);
        assertTrue(graph.edgeExists(-3, -3));
        assertEquals(List.of(-3), graph.getNeighbours(-3));
        graph.deleteEdge(-3, -3);
        assertFalse(graph.edgeExists(-3, -3));
    }

    @ParameterizedTest(name = "{0}: independent directions")
    @MethodSource("implementations")
    void oppositeDirectionsAreIndependent(String name, Supplier<Graph> factory) {
        Graph graph = factory.get();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);
        graph.addEdge(2, 1);
        graph.deleteEdge(1, 2);
        assertFalse(graph.edgeExists(1, 2));
        assertTrue(graph.edgeExists(2, 1));
    }

    @ParameterizedTest(name = "{0}: invalid addEdge endpoints")
    @MethodSource("implementations")
    void addEdgeRejectsEitherMissingEndpoint(String name, Supplier<Graph> factory) {
        Graph graph = factory.get();
        graph.addVertex(0);
        graph.addVertex(2);
        assertThrows(NoSuchElementException.class, () -> graph.addEdge(0, 99));
        assertThrows(NoSuchElementException.class, () -> graph.addEdge(99, 2));
        assertThrows(NoSuchElementException.class, () -> graph.addEdge(98, 99));
        assertTrue(graph.getNeighbours(0).isEmpty());
    }

    @ParameterizedTest(name = "{0}: invalid deleteEdge endpoints")
    @MethodSource("implementations")
    void deleteEdgeRejectsEitherMissingEndpoint(String name, Supplier<Graph> factory) {
        Graph graph = factory.get();
        graph.addVertex(5);
        graph.addVertex(6);
        graph.addEdge(5, 6);
        assertThrows(NoSuchElementException.class, () -> graph.deleteEdge(5, 7));
        assertThrows(NoSuchElementException.class, () -> graph.deleteEdge(7, 6));
        assertTrue(graph.edgeExists(5, 6));
    }

    @ParameterizedTest(name = "{0}: missing edge no-op")
    @MethodSource("implementations")
    void deletingAbsentEdgeIsNoOp(String name, Supplier<Graph> factory) {
        Graph graph = factory.get();
        graph.addVertex(5);
        graph.addVertex(6);
        graph.deleteEdge(5, 6);
        assertFalse(graph.edgeExists(5, 6));
    }

    @ParameterizedTest(name = "{0}: query missing keys")
    @MethodSource("implementations")
    void queriesHandleMissingKeys(String name, Supplier<Graph> factory) {
        Graph graph = factory.get();
        graph.addVertex(10);
        assertThrows(NoSuchElementException.class, () -> graph.getNeighbours(0));
        assertFalse(graph.edgeExists(10, 0));
        assertFalse(graph.edgeExists(0, 10));
        assertFalse(graph.edgeExists(0, 0));
    }

    @ParameterizedTest(name = "{0}: delete incident edges")
    @MethodSource("implementations")
    void deleteVertexRemovesAllIncidentEdges(String name, Supplier<Graph> factory) {
        Graph graph = factory.get();
        graph.addVertex(10);
        graph.addVertex(20);
        graph.addVertex(30);
        graph.addVertex(40);
        graph.addEdge(10, 20);
        graph.addEdge(20, 30);
        graph.addEdge(30, 20);
        graph.addEdge(20, 20);
        graph.addEdge(10, 40);
        graph.deleteVertex(20);
        assertEquals(3, graph.getVertexCount());
        assertFalse(graph.vertexExists(20));
        assertEquals(Set.of(40), new HashSet<>(graph.getNeighbours(10)));
        assertTrue(graph.getNeighbours(30).isEmpty());
        assertFalse(graph.edgeExists(10, 20));
        assertFalse(graph.edgeExists(20, 30));
        assertTrue(graph.edgeExists(10, 40));
    }

    @ParameterizedTest(name = "{0}: delete and re-add same key")
    @MethodSource("implementations")
    void deletedKeyCanBeAddedBackWithoutOldEdges(String name, Supplier<Graph> factory) {
        Graph graph = factory.get();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);
        graph.addEdge(2, 1);
        graph.deleteVertex(2);
        graph.addVertex(2);
        assertFalse(graph.edgeExists(1, 2));
        assertFalse(graph.edgeExists(2, 1));
        assertTrue(graph.getNeighbours(1).isEmpty());
        graph.addEdge(2, 1);
        assertTrue(graph.edgeExists(2, 1));
    }

    @ParameterizedTest(name = "{0}: vertex list is snapshot")
    @MethodSource("implementations")
    void vertexListDoesNotExposeGraph(String name, Supplier<Graph> factory) {
        Graph graph = factory.get();
        graph.addVertex(10);
        List<Integer> keys = graph.getVertices();
        keys.clear();
        assertTrue(graph.vertexExists(10));
    }

    @ParameterizedTest(name = "{0}: sparse public keys")
    @MethodSource("implementations")
    void neighbourResultsUseKeysNotInternalCodes(String name, Supplier<Graph> factory) {
        Graph graph = factory.get();
        graph.addVertex(1000);
        graph.addVertex(-1000);
        graph.addVertex(42);
        graph.addEdge(42, 1000);
        graph.addEdge(42, -1000);
        assertEquals(Set.of(1000, -1000), new HashSet<>(graph.getNeighbours(42)));
    }

    @ParameterizedTest(name = "{0}: retain edges after deleting unrelated vertex")
    @MethodSource("implementations")
    void deletingUnrelatedVertexPreservesEdges(String name, Supplier<Graph> factory) {
        Graph graph = factory.get();
        graph.addVertex(11);
        graph.addVertex(22);
        graph.addVertex(33);
        graph.addVertex(44);
        graph.addEdge(44, 33);
        graph.addEdge(33, 44);
        graph.deleteVertex(22);
        assertTrue(graph.edgeExists(44, 33));
        assertTrue(graph.edgeExists(33, 44));
        assertEquals(List.of(33), graph.getNeighbours(44));
    }

    @ParameterizedTest(name = "{0}: clear graph via vertex deletion")
    @MethodSource("implementations")
    void deleteAllVerticesAndRebuild(String name, Supplier<Graph> factory) {
        Graph graph = factory.get();
        graph.addVertex(30);
        graph.addVertex(20);
        graph.addVertex(10);
        graph.addEdge(30, 20);
        graph.addEdge(10, 10);
        graph.deleteVertex(30);
        graph.deleteVertex(20);
        graph.deleteVertex(10);
        assertEquals(0, graph.getVertexCount());
        assertEquals(0, graph.addVertex(100));
        assertTrue(graph.getNeighbours(100).isEmpty());
    }
}
