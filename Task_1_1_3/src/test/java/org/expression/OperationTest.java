package org.expression;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class OperationTest {

    @Test
    void gettersReturnCorrectOperands() {
        Number left = new Number(2.0);
        Number right = new Number(3.0);
        Add operation = new Add(left, right);

        assertSame(left, operation.getLeft());
        assertSame(right, operation.getRight());
    }

    @Test
    void toStringContainsClassNameAndOperands() {
        Add operation = new Add(new Number(2.0), new Number(3.0));

        assertEquals("Add(Number(2.0), Number(3.0))",
                operation.toString());
    }
}