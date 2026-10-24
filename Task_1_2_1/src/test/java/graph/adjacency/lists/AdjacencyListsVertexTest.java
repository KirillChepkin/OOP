package graph.adjacency.lists;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.Test;

class AdjacencyListsVertexTest {

    @Test
    void constructorStoresInheritedPropertiesAndEmptyAdjacencySet() {
        AdjacencyListsVertex<String> vertex = new AdjacencyListsVertex<>(4, "node");

        assertEquals(4, vertex.getCode());
        assertEquals("node", vertex.getContent());
        assertTrue(vertex.getAdjacencySet().isEmpty());
    }

    @Test
    void addAdjacentVertexStoresUniqueCodes() {
        AdjacencyListsVertex<Integer> vertex = new AdjacencyListsVertex<>(0, 42);

        vertex.addAdjacentVertex(3);
        vertex.addAdjacentVertex(1);
        vertex.addAdjacentVertex(3);

        assertEquals(Set.of(1, 3), vertex.getAdjacencySet());
    }
}
