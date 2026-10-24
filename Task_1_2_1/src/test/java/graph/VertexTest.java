package graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class VertexTest {

    @Test
    void constructorStoresCodeAndContent() {
        Object content = new Object();
        Vertex<Object> vertex = new Vertex<Object>(7, content) {};

        assertEquals(7, vertex.getCode());
        assertSame(content, vertex.getContent());
    }

    @Test
    void setCodeChangesCodeButNotContent() {
        Vertex<String> vertex = new Vertex<String>(5, "payload") {};

        vertex.setCode(2);

        assertEquals(2, vertex.getCode());
        assertEquals("payload", vertex.getContent());
    }
}
