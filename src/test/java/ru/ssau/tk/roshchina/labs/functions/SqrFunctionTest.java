package ru.ssau.tk.roshchina.labs.functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SqrFunctionTest {
    @Test
    public void testSqr() {
        SqrFunction sqrFunction = new SqrFunction();
        assertEquals(0.0, sqrFunction.apply(0), 0.0001);
        assertEquals(49.0, sqrFunction.apply(7.0), 0.0001);
        assertEquals(2.25, sqrFunction.apply(-1.5), 0.0001);
        assertEquals(12.6025, sqrFunction.apply(3.55), 0.0001);
        assertEquals(0.0001, sqrFunction.apply(0.01), 0.0001);
    }
}