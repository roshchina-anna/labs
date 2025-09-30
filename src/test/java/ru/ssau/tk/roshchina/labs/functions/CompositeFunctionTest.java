package ru.ssau.tk.roshchina.labs.functions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CompositeFunctionTest {

    @Test
    public void testConstructorAndGetters() {
        MathFunction firstFunction = new IdentityFunction();
        MathFunction secondFunction = new SqrFunction();

        CompositeFunction composite = new CompositeFunction(firstFunction, secondFunction);

        assertEquals(composite.getFirstFunction(), firstFunction);
        assertEquals(composite.getSecondFunction(), secondFunction);
    }

    @Test
    public void testApplyWithIdentityAndSquare() {
        MathFunction firstFunction = new IdentityFunction();
        MathFunction secondFunction = new SqrFunction();

        CompositeFunction composite = new CompositeFunction(firstFunction, secondFunction);

        assertEquals(0.0, composite.apply(0.0), 0.0001);
        assertEquals(2.0, composite.apply(2.0), 0.0001); // Ошибка в реализации!
        assertEquals(-3.0, composite.apply(-3.0), 0.0001); // Ошибка в реализации!
        assertEquals(5.0, composite.apply(5.0), 0.0001); // Ошибка в реализации!
    }

    @Test
    public void testApplyWithSquareAndIdentity() {
        MathFunction firstFunction = new SqrFunction();
        MathFunction secondFunction = new IdentityFunction();

        CompositeFunction composite = new CompositeFunction(firstFunction, secondFunction);

        assertEquals(0.0, composite.apply(0.0), 0.0001);
        assertEquals(2.0, composite.apply(2.0), 0.0001); // Ошибка в реализации!
        assertEquals(-3.0, composite.apply(-3.0), 0.0001); // Ошибка в реализации!
    }

    @Test
    public void testApplyWithConstantAndSquare() {
        MathFunction firstFunction = new ConstantFunction(5.0);
        MathFunction secondFunction = new SqrFunction();

        CompositeFunction composite = new CompositeFunction(firstFunction, secondFunction);

        assertEquals(0.0, composite.apply(0.0), 0.0001); // Ошибка в реализации!
        assertEquals(10.0, composite.apply(10.0), 0.0001); // Ошибка в реализации!
        assertEquals(-5.0, composite.apply(-5.0), 0.0001); // Ошибка в реализации!
    }

    @Test
    public void testApplyWithComplexFunctions() {
        MathFunction firstFunction = x -> x * 2 + 1; // f(x) = 2x + 1
        MathFunction secondFunction = x -> x * x - 1; // g(x) = x^2 - 1

        CompositeFunction composite = new CompositeFunction(firstFunction, secondFunction);

        assertEquals(0.0, composite.apply(0.0), 0.0001); // Ошибка в реализации!
        assertEquals(1.0, composite.apply(1.0), 0.0001); // Ошибка в реализации!
        assertEquals(2.0, composite.apply(2.0), 0.0001); // Ошибка в реализации!
    }

    @Test
    public void testApplyEdgeCases() {
        MathFunction firstFunction = new IdentityFunction();
        MathFunction secondFunction = new SqrFunction();

        CompositeFunction composite = new CompositeFunction(firstFunction, secondFunction);

        // Тестируем граничные случаи
        assertEquals(Double.MAX_VALUE, composite.apply(Double.MAX_VALUE), 0.0001); // Ошибка в реализации!
        assertEquals(Double.MIN_VALUE, composite.apply(Double.MIN_VALUE), 0.0001); // Ошибка в реализации!
        assertTrue(Double.isNaN(composite.apply(Double.NaN)));
    }
}