package ru.ssau.tk.roshchina.labs.functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathFunctionTest {
    @Test
    void testAndThenWithIdentityFunction() {
        MathFunction identity = new IdentityFunction();
        MathFunction sqr = new SqrFunction();
        MathFunction composite = identity.andThen(sqr);
        assertEquals(0.0, composite.apply(0.0), 0.000000001);
        assertEquals(1.0, composite.apply(1.0), 0.000000001);
        assertEquals(4.0, composite.apply(2.0), 0.000000001);
        assertEquals(25.0, composite.apply(5.0), 0.000000001);
    }
    @Test
    void testAndThenWithSqrFunction() {
        MathFunction sqr = new SqrFunction();
        MathFunction identity = new IdentityFunction();
        MathFunction composite = sqr.andThen(identity);
        assertEquals(0.0, composite.apply(0.0), 0.000000001);
        assertEquals(1.0, composite.apply(1.0), 0.000000001);
        assertEquals(4.0, composite.apply(2.0), 0.000000001);
        assertEquals(9.0, composite.apply(3.0), 0.000000001);
    }
    @Test
    void testAndThenChainWithThreeFunctions() {
        MathFunction identity = new IdentityFunction();
        MathFunction sqr = new SqrFunction();
        MathFunction constant = new ConstantFunction(10.0);
        MathFunction composite = identity.andThen(sqr).andThen(constant);
        assertEquals(10.0, composite.apply(0.0), 0.000000001);
        assertEquals(10.0, composite.apply(5.0), 0.000000001);
        assertEquals(10.0, composite.apply(-3.0), 0.000000001);
        assertEquals(10.0, composite.apply(100.0), 0.000000001);
    }
    @Test
    void testAndThenWithConstantFunction() {
        MathFunction constant = new ConstantFunction(5.0);
        MathFunction sqr = new SqrFunction();
        MathFunction composite = constant.andThen(sqr);
        assertEquals(25.0, composite.apply(0.0), 0.000000001);
        assertEquals(25.0, composite.apply(10.0), 0.000000001);
        assertEquals(25.0, composite.apply(-5.0), 0.000000001);
    }
    @Test
    void testAndThenWithZeroFunction() {
        ZeroFunction zero = new ZeroFunction();
        MathFunction sqr = new SqrFunction();
        MathFunction composite = zero.andThen(sqr);
        assertEquals(0.0, composite.apply(0.0), 0.000000001);
        assertEquals(0.0, composite.apply(10.0), 0.000000001);
        assertEquals(0.0, composite.apply(-5.0), 0.000000001);
    }
    @Test
    void testAndThenWithUnitFunction() {
        UnitFunction unit = new UnitFunction();
        MathFunction sqr = new SqrFunction();
        MathFunction composite = unit.andThen(sqr);
        assertEquals(1.0, composite.apply(0.0), 0.000000001);
        assertEquals(1.0, composite.apply(10.0), 0.000000001);
        assertEquals(1.0, composite.apply(-5.0), 0.000000001);
    }
    @Test
    void testAndThenWithCompositeFunctions() {
        MathFunction identity = new IdentityFunction();
        MathFunction sqr = new SqrFunction();
        MathFunction constant = new ConstantFunction(2.0);
        MathFunction firstComposite = identity.andThen(sqr);
        MathFunction secondComposite = firstComposite.andThen(constant);
        assertEquals(2.0, secondComposite.apply(0.0), 0.000000001);
        assertEquals(2.0, secondComposite.apply(1.0), 0.000000001);
        assertEquals(2.0, secondComposite.apply(5.0), 0.000000001);
    }
    @Test
    void testAndThenOrderMatters() {
        MathFunction identity = new IdentityFunction();
        MathFunction sqr = new SqrFunction();
        MathFunction constant = new ConstantFunction(3.0);
        MathFunction composite1 = identity.andThen(sqr).andThen(constant);
        MathFunction composite2 = constant.andThen(identity).andThen(sqr);
        assertEquals(3.0, composite1.apply(10.0), 0.000000001);
        assertEquals(9.0, composite2.apply(10.0), 0.000000001);
    }
    @Test
    void testAndThenWithSameFunction() {
        MathFunction sqr = new SqrFunction();
        MathFunction composite = sqr.andThen(sqr);
        assertEquals(0.0, composite.apply(0.0), 0.000000001);
        assertEquals(1.0, composite.apply(1.0), 0.000000001);
        assertEquals(16.0, composite.apply(2.0), 0.000000001);
        assertEquals(81.0, composite.apply(3.0), 0.000000001);
        assertEquals(625.0, composite.apply(5.0), 0.000000001);
    }
    @Test
    void testAndThenDirectApplication() {
        MathFunction identity = new IdentityFunction();
        MathFunction sqr = new SqrFunction();
        MathFunction constant = new ConstantFunction(5.0);
        double result = identity.andThen(sqr).andThen(constant).apply(3.0);
        assertEquals(5.0, result, 0.000000001);
    }

    @Test
    void testComplexChainExampleFromTask() {
        MathFunction f = new ConstantFunction(10.0);
        MathFunction g = new SqrFunction();
        MathFunction h = new IdentityFunction();
        MathFunction composite = h.andThen(g).andThen(f);
        assertEquals(10.0, composite.apply(0.0), 0.000000001);
        assertEquals(10.0, composite.apply(5.0), 0.000000001);
        assertEquals(10.0, composite.apply(-3.0), 0.000000001);
        MathFunction alternative = f.andThen(g).andThen(h);
        assertEquals(100.0, alternative.apply(0.0), 0.000000001);
    }
    @Test
    void testAndThenReturnsCompositeFunctionInstance() {
        MathFunction identity = new IdentityFunction();
        MathFunction sqr = new SqrFunction();
        MathFunction composite = identity.andThen(sqr);
        assertTrue(composite instanceof CompositeFunction);
    }
}