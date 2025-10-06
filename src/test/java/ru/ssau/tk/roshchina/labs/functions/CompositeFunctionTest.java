package ru.ssau.tk.roshchina.labs.functions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CompositeFunctionTest {

    @Test
    public void testConstructorAndGetters() {
        MathFunction firstFunction = new IdentityFunction();
        MathFunction secondFunction = new SqrFunction();

        CompositeFunction composite = new CompositeFunction(firstFunction, secondFunction);

        assertSame(composite.getFirstFunction(), firstFunction);
        assertSame(composite.getSecondFunction(), secondFunction);
    }


    @Test
    public void testApplyWithSquareAndIdentity() {
        MathFunction firstFunction = new SqrFunction();
        MathFunction secondFunction = new IdentityFunction();
        CompositeFunction composite = new CompositeFunction(firstFunction, secondFunction);

        assertEquals(0.0, composite.apply(0.0), 0.0001);
        assertEquals(4.0, composite.apply(2.0), 0.0001);
        assertEquals(9.0, composite.apply(-3.0), 0.0001);
    }


    @Test
    public void testApplyWithSquareAndConstant() {
        MathFunction firstFunction = new SqrFunction();
        MathFunction secondFunction = new ConstantFunction(10.0);
        CompositeFunction composite = new CompositeFunction(firstFunction, secondFunction);

        assertEquals(10.0, composite.apply(0.0), 0.0001);
        assertEquals(10.0, composite.apply(2.0), 0.0001);
        assertEquals(10.0, composite.apply(-3.0), 0.0001);
    }

    @Test
    public void testApplyWithComplexFunctions() {
        MathFunction firstFunction = x -> x * 2 + 1;
        MathFunction secondFunction = x -> x * x - 1;
        CompositeFunction composite = new CompositeFunction(firstFunction, secondFunction);

        assertEquals(0.0, composite.apply(0.0), 0.0001);
        assertEquals(8.0, composite.apply(1.0), 0.0001);
    }

    @Test
    public void testApplyWithZeroFunctions() {
        MathFunction firstFunction = x -> 0.0;
        MathFunction secondFunction = x -> 0.0;
        CompositeFunction composite = new CompositeFunction(firstFunction, secondFunction);

        assertEquals(0.0, composite.apply(0.0), 0.0001);
        assertEquals(0.0, composite.apply(5.0), 0.0001);
        assertEquals(0.0, composite.apply(-5.0), 0.0001);
    }

    @Test
    public void testApplyWithSameFunction() {
        MathFunction squareFunction = new SqrFunction();
        CompositeFunction composite = new CompositeFunction(squareFunction, squareFunction);

        assertEquals(0.0, composite.apply(0.0), 0.0001);
        assertEquals(16.0, composite.apply(2.0), 0.0001);
        assertEquals(16.0, composite.apply(-2.0), 0.0001);
        assertEquals(81.0, composite.apply(3.0), 0.0001);
    }

    @Test
    public void testApplyEdgeCases() {
        MathFunction firstFunction = new IdentityFunction();
        MathFunction secondFunction = new SqrFunction();
        CompositeFunction composite = new CompositeFunction(firstFunction, secondFunction);

        assertEquals(Double.POSITIVE_INFINITY, composite.apply(Double.MAX_VALUE), 0.0001);
        assertEquals(0.0, composite.apply(Double.MIN_VALUE), 0.0001);
        assertEquals(Double.POSITIVE_INFINITY, composite.apply(Double.POSITIVE_INFINITY), 0.0001);
        assertEquals(Double.POSITIVE_INFINITY, composite.apply(Double.NEGATIVE_INFINITY), 0.0001);
    }

    @Test
    public void testApplyWithNaN() {
        MathFunction firstFunction = new IdentityFunction();
        MathFunction secondFunction = new SqrFunction();
        CompositeFunction composite = new CompositeFunction(firstFunction, secondFunction);

        assertTrue(Double.isNaN(composite.apply(Double.NaN)));
    }

    @Test
    public void testApplyWithNegativeToPositiveTransformation() {
        MathFunction firstFunction = Math::abs;
        MathFunction secondFunction = x -> x + 1;
        CompositeFunction composite = new CompositeFunction(firstFunction, secondFunction);

        assertEquals(4.0, composite.apply(3.0), 0.0001);
        assertEquals(4.0, composite.apply(-3.0), 0.0001);
        assertEquals(1.0, composite.apply(0.0), 0.0001);
    }

    @Test
    public void testApplyWithLinearFunctions() {
        MathFunction firstFunction = x -> 2 * x;
        MathFunction secondFunction = x -> 3 * x;
        CompositeFunction composite = new CompositeFunction(firstFunction, secondFunction);

        assertEquals(0.0, composite.apply(0.0), 0.0001);
        assertEquals(6.0, composite.apply(1.0), 0.0001);
        assertEquals(15.0, composite.apply(2.5), 0.0001);
        assertEquals(-12.0, composite.apply(-2.0), 0.0001);
    }

    @Test
    public void testCompositeFunctionImmutability() {
        // Arrange
        MathFunction firstFunction = new IdentityFunction();
        MathFunction secondFunction = new SqrFunction();
        CompositeFunction composite = new CompositeFunction(firstFunction, secondFunction);

        MathFunction retrievedFirst = composite.getFirstFunction();
        MathFunction retrievedSecond = composite.getSecondFunction();

        assertSame(retrievedFirst, firstFunction);
        assertSame(retrievedSecond, secondFunction);

        assertEquals(9.0, composite.apply(3.0), 0.0001);
    }
}