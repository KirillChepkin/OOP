package graph.adjacency.lists;

import java.util.List;
import java.util.NoSuchElementException;

import graph.adjacency.lists.AdjacencyListsGraph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AdjacencyListsGraphTest {
    @Test
    void deletedCodesReusedInAscendingOrder() {
        AdjacencyListsGraph graph = new AdjacencyListsGraph();
        graph.addVertex(10); // code 0
        graph.addVertex(20); // code 1
        graph.addVertex(30); // code 2
        graph.addVertex(40); // code 3
        graph.deleteVertex(30);
        graph.deleteVertex(10);
        assertEquals(0, graph.addVertex(50));
        assertEquals(2, graph.addVertex(60));
        assertEquals(4, graph.addVertex(70));
    }

    @Test
    void deletingEdgeRemovesValueNotListPosition() {
        AdjacencyListsGraph graph = new AdjacencyListsGraph();
        graph.addVertex(10);
        graph.addVertex(20);
        graph.addVertex(30);
        graph.addEdge(10, 30); // code 2, but at list INDEX 0
        graph.addEdge(10, 20); // code 1, but at list INDEX 1
        graph.deleteEdge(10, 30);
        assertFalse(graph.edgeExists(10, 30));
        assertEquals(List.of(20), graph.getNeighbours(10));
    }

    @Test
    void deletingVertexRemovesReferencesEvenIfCodeIsLarge() {
        AdjacencyListsGraph graph = new AdjacencyListsGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(1, 3);
        graph.deleteVertex(3);
        assertTrue(graph.getNeighbours(1).isEmpty());
        assertThrows(NoSuchElementException.class, () -> graph.getNeighbours(3));
    }

    @Test
    void edgeExistsMustTranslateEndKeyIntoCode() {
        AdjacencyListsGraph graph = new AdjacencyListsGraph();
        graph.addVertex(100);
        graph.addVertex(200);
        graph.addEdge(100, 200);
        assertTrue(graph.edgeExists(100, 200));
    }
}
