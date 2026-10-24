package graph.adjacency.lists;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class AdjacencyListsGraphTest {

    @Test
    void vertexCodesAreReusedWithoutRenumberingOtherVertices() {
        AdjacencyListsGraph<String> graph = new AdjacencyListsGraph<>();
        graph.addVertex("A"); // 0
        graph.addVertex("B"); // 1
        graph.addVertex("C"); // 2

        graph.deleteVertex(1);

        assertEquals(Set.of(0, 2), graph.getVertexMap().keySet());
        assertEquals(2, graph.getVertex(2).getCode());
        assertEquals("C", graph.getVertex(2).getContent());

        int recycledCode = graph.addVertex("D");
        assertEquals(1, recycledCode);
        assertEquals("D", graph.getVertex(1).getContent());
        assertEquals("C", graph.getVertex(2).getContent());
    }

    @Test
    void deletingVertexClearsIncomingEdgesBeforeItsCodeIsReused() {
        AdjacencyListsGraph<String> graph = new AdjacencyListsGraph<>();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge(0, 1);
        graph.addEdge(2, 1);
        graph.addEdge(1, 0);

        graph.deleteVertex(1);
        int newCode = graph.addVertex("D");

        assertEquals(1, newCode);
        assertTrue(graph.getNeighbours(0).isEmpty());
        assertTrue(graph.getNeighbours(2).isEmpty());
        assertTrue(graph.getNeighbours(1).isEmpty());
    }

    @Test
    void adjacencySetRepresentsOutgoingEdges() {
        AdjacencyListsGraph<String> graph = new AdjacencyListsGraph<>();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge(0, 2);
        graph.addEdge(0, 1);

        AdjacencyListsVertex<String> vertex = graph.getVertexMap().get(0);
        assertNotNull(vertex);
        assertEquals(Set.of(1, 2), vertex.getAdjacencySet());
        assertEquals(Set.of("B", "C"), graph.getNeighbours(0).stream()
                .map(v -> v.getContent()).collect(Collectors.toSet()));
        assertFalse(graph.getVertexMap().get(1).getAdjacencySet().contains(0));
    }
}
