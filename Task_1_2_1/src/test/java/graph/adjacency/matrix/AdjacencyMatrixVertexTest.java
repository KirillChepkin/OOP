package graph.adjacency.matrix;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AdjacencyMatrixVertexTest {

    @Test
    void constructorAndInheritedMethodsWork() {
        AdjacencyMatrixVertex<String> vertex = new AdjacencyMatrixVertex<>(9, "node");

        assertEquals(9, vertex.getCode());
        assertEquals("node", vertex.getContent());

        vertex.setCode(2);
        assertEquals(2, vertex.getCode());
        assertEquals("node", vertex.getContent());
    }
}
