package ru.ssau.tk.roshchina.labs.functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstantFunctionTest {
    @Test
    void testApplyWithDifferentValues() {
        ConstantFunction func = new ConstantFunction(5.0);
        assertEquals(5.0, func.apply(0.0), 0.000000001);
        assertEquals(5.0, func.apply(10.0), 0.000000001);
        assertEquals(5.0, func.apply(-5.0), 0.000000001);
        assertEquals(5.0, func.apply(100.0), 0.000000001);
        assertEquals(5.0, func.apply(Double.MAX_VALUE), 0.000000001);
        assertEquals(5.0, func.apply(Double.MIN_VALUE), 0.000000001);
    }
    @Test
    void testApplyWithNegativeConstant() {
        ConstantFunction func = new ConstantFunction(-3.5);
        assertEquals(-3.5, func.apply(0.0), 0.000000001);
        assertEquals(-3.5, func.apply(15.0), 0.000000001);
        assertEquals(-3.5, func.apply(-20.0), 0.000000001);
    }
    @Test
    void testApplyWithZeroConstant() {
        ConstantFunction func = new ConstantFunction(0.0);
        assertEquals(0.0, func.apply(0.0), 0.000000001);
        assertEquals(0.0, func.apply(1.0), 0.000000001);
        assertEquals(0.0, func.apply(-1.0), 0.000000001);
        assertEquals(0.0, func.apply(1000.0), 0.000000001);
    }
}