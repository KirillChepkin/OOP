package graph.incidence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IncidenceMatrixEdgeTest {
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
