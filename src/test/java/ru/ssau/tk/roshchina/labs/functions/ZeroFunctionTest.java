package ru.ssau.tk.roshchina.labs.functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ZeroFunctionTest {
    @Test
    void testApplyAlwaysReturnsZero() {
        ZeroFunction zeroFunc = new ZeroFunction();
        assertEquals(0.0, zeroFunc.apply(0.0), 0.000000001);
        assertEquals(0.0, zeroFunc.apply(10.0),0.000000001);
        assertEquals(0.0, zeroFunc.apply(-5.0), 0.000000001);
        assertEquals(0.0, zeroFunc.apply(100.0), 0.000000001);
        assertEquals(0.0, zeroFunc.apply(Double.MAX_VALUE), 0.000000001);
        assertEquals(0.0, zeroFunc.apply(Double.MIN_VALUE), 0.000000001);
    }
    @Test
    void testGetConstantReturnsZero() {
        ZeroFunction zeroFunc = new ZeroFunction();
        assertEquals(0.0, zeroFunc.getConstant(), 0.000000001);
    }
    @Test
    void testMultipleZeroFunctionInstances() {
        ZeroFunction func1 = new ZeroFunction();
        ZeroFunction func2 = new ZeroFunction();
        assertEquals(0.0, func1.apply(100.0), 0.000000001);
        assertEquals(0.0, func2.apply(200.0), 0.000000001);
        assertEquals(func1.apply(300.0), func2.apply(300.0), 0.000000001);
    }
    @Test
    void testConstructorHasNoArguments() {
        ZeroFunction zeroFunc = new ZeroFunction();
        assertNotNull(zeroFunc);
    }
}