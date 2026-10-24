package graph.incidence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class IncidenceMatrixEdgeTest {
    @Test
    void storesCodeAndEndpointReferences() {
        IncidenceMatrixVertex<Integer> start = new IncidenceMatrixVertex<>(0, 11);
        IncidenceMatrixVertex<Integer> end = new IncidenceMatrixVertex<>(1, 22);
        IncidenceMatrixEdge<Integer> edge = new IncidenceMatrixEdge<>(4, start, end);
        assertEquals(4, edge.getCode());
        assertSame(start, edge.getStartVertex());
        assertSame(end, edge.getEndVertex());
    }
}
