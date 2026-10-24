package graph.incidence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class IncidenceMatrixVertexTest {
    @Test
    void inheritsContentAndCodeFromVertex() {
        IncidenceMatrixVertex<String> vertex = new IncidenceMatrixVertex<>(7, "vertex");
        assertEquals(7, vertex.getCode());
        assertEquals("vertex", vertex.getContent());
        vertex.setCode(3);
        assertEquals(3, vertex.getCode());
    }
}
