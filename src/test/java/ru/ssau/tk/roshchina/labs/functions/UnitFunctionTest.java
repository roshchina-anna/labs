package ru.ssau.tk.roshchina.labs.functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitFunctionTest {
    @Test
    void testApplyAlwaysReturnsOne() {
        UnitFunction unitFunc = new UnitFunction();
        assertEquals(1.0, unitFunc.apply(0.0), 0.000000001);
        assertEquals(1.0, unitFunc.apply(10.0), 0.000000001);
        assertEquals(1.0, unitFunc.apply(-5.0), 0.000000001);
        assertEquals(1.0, unitFunc.apply(100.0), 0.000000001);
        assertEquals(1.0, unitFunc.apply(Double.MAX_VALUE), 0.000000001);
        assertEquals(1.0, unitFunc.apply(Double.MIN_VALUE), 0.000000001);
    }
    @Test
    void testGetConstantReturnsOne() {
        UnitFunction unitFunc = new UnitFunction();
        assertEquals(1.0, unitFunc.getConstant(), 0.000000001);
    }
    @Test
    void testMultipleUnitFunctionInstances() {
        UnitFunction func1 = new UnitFunction();
        UnitFunction func2 = new UnitFunction();
        assertEquals(1.0, func1.apply(100.0), 0.000000001);
        assertEquals(1.0, func2.apply(200.0), 0.000000001);
        assertEquals(func1.apply(300.0), func2.apply(300.0), 0.000000001);
    }
    @Test
    void testConstructorHasNoArguments() {
        UnitFunction unitFunc = new UnitFunction();
        assertNotNull(unitFunc);
    }
}