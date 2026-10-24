package graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import graph.adjacency.lists.AdjacencyListsGraph;
import graph.adjacency.matrix.AdjacencyMatrixGraph;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GraphContractTest {

    static Stream<Arguments> implementations() {
        return Stream.of(
                Arguments.of("adjacency lists",
                        (Supplier<Graph<String>>) () -> new AdjacencyListsGraph<>()),
                Arguments.of("adjacency matrix",
                        (Supplier<Graph<String>>) () -> new AdjacencyMatrixGraph<>())
        );
    }

    private static Set<Integer> neighbourCodes(Graph<String> graph, int code) {
        return graph.getNeighbours(code).stream()
                .map(Vertex::getCode)
                .collect(Collectors.toSet());
    }

    @ParameterizedTest(name = "{0}: vertices store codes and contents")
    @MethodSource("implementations")
    void addAndRetrieveVertices(String name, Supplier<Graph<String>> factory) {
        Graph<String> graph = factory.get();

        assertEquals(0, graph.addVertex("A"));
        assertEquals(1, graph.addVertex("B"));
        assertEquals(2, graph.addVertex("C"));
        assertEquals("A", graph.getVertex(0).getContent());
        assertEquals("B", graph.getVertex(1).getContent());
        assertEquals("C", graph.getVertex(2).getContent());
        assertEquals(2, graph.getVertex(2).getCode());
    }

    @ParameterizedTest(name = "{0}: newly added vertices have no edges")
    @MethodSource("implementations")
    void newVerticesHaveNoNeighbours(String name, Supplier<Graph<String>> factory) {
        Graph<String> graph = factory.get();
        graph.addVertex("A");
        graph.addVertex("B");

        assertTrue(graph.getNeighbours(0).isEmpty());
        assertTrue(graph.getNeighbours(1).isEmpty());
    }

    @ParameterizedTest(name = "{0}: edges are directed")
    @MethodSource("implementations")
    void addEdgeIsDirected(String name, Supplier<Graph<String>> factory) {
        Graph<String> graph = factory.get();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");

        graph.addEdge(0, 1);
        graph.addEdge(0, 2);

        assertEquals(Set.of(1, 2), neighbourCodes(graph, 0));
        assertTrue(graph.getNeighbours(1).isEmpty());
        assertTrue(graph.getNeighbours(2).isEmpty());
    }

    @ParameterizedTest(name = "{0}: duplicate edges are ignored")
    @MethodSource("implementations")
    void duplicateEdgesAreNotStored(String name, Supplier<Graph<String>> factory) {
        Graph<String> graph = factory.get();
        graph.addVertex("A");
        graph.addVertex("B");

        graph.addEdge(0, 1);
        graph.addEdge(0, 1);

        assertEquals(Set.of(1), neighbourCodes(graph, 0));
        assertEquals(1, graph.getNeighbours(0).size());
    }

    @ParameterizedTest(name = "{0}: self-loops are supported")
    @MethodSource("implementations")
    void selfLoopIsStored(String name, Supplier<Graph<String>> factory) {
        Graph<String> graph = factory.get();
        graph.addVertex("A");

        graph.addEdge(0, 0);

        assertEquals(Set.of(0), neighbourCodes(graph, 0));
    }

    @ParameterizedTest(name = "{0}: edge deletion is selective")
    @MethodSource("implementations")
    void deleteEdgeRemovesOnlySpecifiedEdge(String name, Supplier<Graph<String>> factory) {
        Graph<String> graph = factory.get();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 0);

        graph.deleteEdge(0, 1);
        graph.deleteEdge(0, 1); // Deleting an absent edge is a no-op.

        assertEquals(Set.of(2), neighbourCodes(graph, 0));
        assertEquals(Set.of(0), neighbourCodes(graph, 1));
    }

    @ParameterizedTest(name = "{0}: vertex deletion removes incident edges")
    @MethodSource("implementations")
    void deleteLastVertexRemovesIncidentEdges(String name, Supplier<Graph<String>> factory) {
        Graph<String> graph = factory.get();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge(0, 1);
        graph.addEdge(1, 0);

        graph.deleteVertex(1);

        assertTrue(graph.getNeighbours(0).isEmpty());
        assertEquals("A", graph.getVertex(0).getContent());
        assertThrows(IndexOutOfBoundsException.class, () -> graph.getVertex(1));
    }

    @ParameterizedTest(name = "{0}: nonexistent vertex codes are rejected")
    @MethodSource("implementations")
    void invalidVertexCodesAreRejected(String name, Supplier<Graph<String>> factory) {
        Graph<String> graph = factory.get();
        graph.addVertex("A");

        assertThrows(IndexOutOfBoundsException.class, () -> graph.getVertex(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> graph.getVertex(1));
        assertThrows(IndexOutOfBoundsException.class, () -> graph.getNeighbours(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> graph.getNeighbours(1));
        assertThrows(IndexOutOfBoundsException.class, () -> graph.deleteVertex(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> graph.deleteVertex(1));
    }

    @ParameterizedTest(name = "{0}: edges require two existing endpoints")
    @MethodSource("implementations")
    void invalidEdgeEndpointsAreRejected(String name, Supplier<Graph<String>> factory) {
        Graph<String> graph = factory.get();
        graph.addVertex("A");
        graph.addVertex("B");

        assertThrows(IndexOutOfBoundsException.class, () -> graph.addEdge(-1, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> graph.addEdge(0, 2));
        assertThrows(IndexOutOfBoundsException.class, () -> graph.deleteEdge(-1, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> graph.deleteEdge(0, 2));
        assertTrue(graph.getNeighbours(0).isEmpty());
        assertTrue(graph.getNeighbours(1).isEmpty());
    }
}
