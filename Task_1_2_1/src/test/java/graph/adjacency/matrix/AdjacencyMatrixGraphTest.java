package graph.adjacency.matrix;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class AdjacencyMatrixGraphTest {

    @Test
    void addVertexKeepsMatrixAndVertexListInSync() {
        AdjacencyMatrixGraph<String> graph = new AdjacencyMatrixGraph<>();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");

        assertEquals(3, graph.getVertices().size());
        assertEquals(3, graph.getMatrix().size());
        assertTrue(graph.getMatrix().stream().allMatch(row -> row.size() == 3));
        assertTrue(
                graph.getMatrix().stream().flatMap(List::stream).noneMatch(Boolean::booleanValue));
        assertInstanceOf(AdjacencyMatrixVertex.class, graph.getVertex(0));
        assertEquals("B", graph.getVertex(1).getContent());
    }

    @Test
    void adjacencyMatrixStoresDirectedEdges() {
        AdjacencyMatrixGraph<String> graph = new AdjacencyMatrixGraph<>();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge(0, 2);
        graph.addEdge(1, 0);

        assertEquals(List.of(
                List.of(false, false, true),
                List.of(true, false, false),
                List.of(false, false, false)), graph.getMatrix());

        graph.deleteEdge(0, 2);
        assertFalse(graph.getMatrix().get(0).get(2));
        assertTrue(graph.getMatrix().get(1).get(0));
    }

    @Test
    void deletingMiddleVertexRenumbersVerticesAndPreservesOtherEdges() {
        AdjacencyMatrixGraph<String> graph = new AdjacencyMatrixGraph<>();
        graph.addVertex("A"); // 0
        graph.addVertex("B"); // 1, removed below
        graph.addVertex("C"); // 2 -> 1
        graph.addVertex("D"); // 3 -> 2
        graph.addEdge(0, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 0);
        graph.addEdge(1, 2); // Disappears when vertex 1 is removed.

        graph.deleteVertex(1);

        assertEquals(3, graph.getVertices().size());
        assertEquals(3, graph.getMatrix().size());
        assertEquals(List.of("A", "C", "D"), graph.getVertices().stream()
                .map(vertex -> vertex.getContent()).collect(Collectors.toList()));
        for (int i = 0; i < 3; i++) {
            assertEquals(i, graph.getVertex(i).getCode());
        }
        assertEquals(List.of(
                List.of(false, true, false),
                List.of(false, false, true),
                List.of(true, false, false)), graph.getMatrix());
        assertEquals(Set.of("C"), graph.getNeighbours(0).stream()
                .map(vertex -> vertex.getContent()).collect(Collectors.toSet()));
    }
}
