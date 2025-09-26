package ru.ssau.tk.roshchina.labs.functions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CompositeFunctionTest {
    @Test
    void testSimpleComposition() {
        MathFunction identity = new IdentityFunction();
        MathFunction square = new SqrFunction();
        CompositeFunction composite = new CompositeFunction(identity, square);

        assertEquals(0.0, composite.apply(0.0), 1e-10);
        assertEquals(1.0, composite.apply(1.0), 1e-10);
        assertEquals(4.0, composite.apply(2.0), 1e-10);
        assertEquals(9.0, composite.apply(3.0), 1e-10);
        assertEquals(25.0, composite.apply(5.0), 1e-10);
    }

    @Test
    void testCompositionInDifferentOrder() {
        MathFunction square = new SqrFunction();
        MathFunction identity = new IdentityFunction();
        CompositeFunction composite = new CompositeFunction(square, identity);

        assertEquals(4.0, composite.apply(2.0), 1e-10);
        assertEquals(9.0, composite.apply(3.0), 1e-10);
        assertEquals(16.0, composite.apply(4.0), 1e-10);
    }

    @Test
    void testTripleComposition() {
        MathFunction identity = new IdentityFunction();
        MathFunction square = new SqrFunction();

        CompositeFunction firstLevel = new CompositeFunction(identity, square);
        CompositeFunction secondLevel = new CompositeFunction(firstLevel, square);

        assertEquals(0.0, secondLevel.apply(0.0), 0.0000000001);
        assertEquals(1.0, secondLevel.apply(1.0), 0.0000000001);
        assertEquals(16.0, secondLevel.apply(2.0), 0.0000000001);
        assertEquals(81.0, secondLevel.apply(3.0), 0.0000000001);
        assertEquals(625.0, secondLevel.apply(5.0), 0.0000000001);
    }

    @Test
    void testCompositionWithItself() {
        MathFunction square = new SqrFunction();
        CompositeFunction squareOfSquare = new CompositeFunction(square, square);

        assertEquals(16.0, squareOfSquare.apply(2.0), 0.0000000001);
        assertEquals(81.0, squareOfSquare.apply(3.0), 0.0000000001);
        assertEquals(256.0, squareOfSquare.apply(4.0), 0.0000000001);
    }

    @Test
    void testComplexComposition() {
        MathFunction identity = new IdentityFunction();
        MathFunction square = new SqrFunction();

        CompositeFunction inner = new CompositeFunction(square, identity);
        CompositeFunction outer = new CompositeFunction(inner, square);

        assertEquals(16.0, outer.apply(2.0), 0.0000000001);
        assertEquals(81.0, outer.apply(3.0), 0.0000000001);
    }

    @Test
    void testCompositionWithLambdaFunctions() {
        MathFunction increment = x -> x + 1;
        MathFunction doubleValue = x -> x * 2;
        CompositeFunction composite = new CompositeFunction(increment, doubleValue);

        assertEquals(2.0, composite.apply(0.0), 0.0000000001);
        assertEquals(4.0, composite.apply(1.0), 0.0000000001);
        assertEquals(6.0, composite.apply(2.0), 0.0000000001);
        assertEquals(10.0, composite.apply(4.0), 0.0000000001);
    }

    @Test
    void testCompositionOrderMatters() {
        MathFunction increment = x -> x + 1;
        MathFunction doubleValue = x -> x * 2;

        CompositeFunction h1 = new CompositeFunction(increment, doubleValue);

        CompositeFunction h2 = new CompositeFunction(doubleValue, increment);

        assertEquals(6.0, h1.apply(2.0), 0.0000000001);
        assertEquals(5.0, h2.apply(2.0), 0.0000000001);

        assertNotEquals(h1.apply(2.0), h2.apply(2.0), 0.0000000001);
    }

    @Test
    void testGetters() {
        MathFunction first = new IdentityFunction();
        MathFunction second = new SqrFunction();
        CompositeFunction composite = new CompositeFunction(first, second);

        assertEquals(first, composite.getFirstFunction());
        assertEquals(second, composite.getSecondFunction());
    }

    @Test
    void testCompositionWithNegativeNumbers() {
        MathFunction identity = new IdentityFunction();
        MathFunction square = new SqrFunction();
        CompositeFunction composite = new CompositeFunction(identity, square);

        assertEquals(4.0, composite.apply(-2.0), 0.0000000001);
        assertEquals(9.0, composite.apply(-3.0), 0.0000000001);
        assertEquals(25.0, composite.apply(-5.0), 0.0000000001);
    }

    @Test
    void testDeepNestedComposition() {
        MathFunction square = new SqrFunction();

        CompositeFunction level1 = new CompositeFunction(square, square);
        CompositeFunction level2 = new CompositeFunction(level1, square);

        assertEquals(1.0, level2.apply(1.0), 0.0000000001);
        assertEquals(256.0, level2.apply(2.0), 0.0000000001);
        assertEquals(6561.0, level2.apply(3.0), 0.0000000001);
    }
}