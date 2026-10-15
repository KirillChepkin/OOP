package org.expression;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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