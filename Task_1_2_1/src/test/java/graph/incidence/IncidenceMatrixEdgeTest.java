package graph.incidence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the behavior of {@link IncidenceMatrixEdge}.
 */
class IncidenceMatrixEdgeTest {

    /**
     * Verifies that an edge stores its code and endpoint keys correctly.
     */
    @Test
    void storesCodeAndEndpointKeys() {
        IncidenceMatrixEdge edge = new IncidenceMatrixEdge(2, -100, 200);

        assertEquals(2, edge.getCode());
        assertEquals(-100, edge.getStartKey());
        assertEquals(200, edge.getEndKey());

        edge.setCode(1);

        assertEquals(1, edge.getCode());
        assertEquals(-100, edge.getStartKey());
    }
}