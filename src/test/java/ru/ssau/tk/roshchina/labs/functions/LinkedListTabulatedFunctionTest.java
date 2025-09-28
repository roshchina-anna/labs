package ru.ssau.tk.roshchina.labs.functions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class LinkedListTabulatedFunctionTest {
    @Test
    void testConstructorFromArrays() {
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {1.0, 4.0, 9.0, 16.0};

        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        assertEquals(4, function.getCount());
        assertEquals(1.0, function.leftBound(), 1e-10);
        assertEquals(4.0, function.rightBound(), 1e-10);
    }

    @Test
    void testConstructorFromFunction() {
        MathFunction source = x -> x * x;
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(source, 0.0, 4.0, 5);

        assertEquals(5, function.getCount());
        assertEquals(0.0, function.leftBound(), 1e-10);
        assertEquals(4.0, function.rightBound(), 1e-10);
        assertEquals(0.0, function.getY(0), 1e-10);
        assertEquals(16.0, function.getY(4), 1e-10);
    }

    @Test
    void testConstructorWithReversedBounds() {
        MathFunction source = x -> x + 1;
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(source, 5.0, 1.0, 5);

        assertEquals(1.0, function.leftBound(), 1e-10);
        assertEquals(5.0, function.rightBound(), 1e-10);
    }
    @Test
    void testGetX() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {10.0, 20.0, 30.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        assertEquals(1.0, function.getX(0), 1e-10);
        assertEquals(2.0, function.getX(1), 1e-10);
        assertEquals(3.0, function.getX(2), 1e-10);
    }

    @Test
    void testGetY() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {10.0, 20.0, 30.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        assertEquals(10.0, function.getY(0), 1e-10);
        assertEquals(20.0, function.getY(1), 1e-10);
        assertEquals(30.0, function.getY(2), 1e-10);
    }

    @Test
    void testSetY() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {10.0, 20.0, 30.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        function.setY(1, 25.0);
        assertEquals(25.0, function.getY(1), 1e-10);

        function.setY(2, 35.0);
        assertEquals(35.0, function.getY(2), 1e-10);
    }

    @Test
    void testIndexOfX() {
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {1.0, 2.0, 3.0, 4.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        assertEquals(0, function.indexOfX(1.0));
        assertEquals(2, function.indexOfX(3.0));
        assertEquals(-1, function.indexOfX(5.0));
        assertEquals(-1, function.indexOfX(0.0));
    }

    @Test
    void testIndexOfY() {
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {10.0, 20.0, 30.0, 20.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        assertEquals(0, function.indexOfY(10.0));
        assertEquals(1, function.indexOfY(20.0));
        assertEquals(2, function.indexOfY(30.0));
        assertEquals(-1, function.indexOfY(40.0));
    }
    @Test
    void testApplyInterpolation() {
        double[] xValues = {0.0, 1.0, 2.0};
        double[] yValues = {0.0, 1.0, 4.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        // Интерполяция
        assertEquals(0.5, function.apply(0.5), 1e-10);
        assertEquals(2.5, function.apply(1.5), 1e-10);
    }

    @Test
    void testApplyExtrapolationLeft() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 4.0, 9.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        // Экстраполяция слева
        assertEquals(-2.0, function.apply(0.0), 1e-10);
        assertEquals(-5.0, function.apply(-1.0), 1e-10);
    }

    @Test
    void testApplyExtrapolationRight() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 4.0, 9.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        // Экстраполяция справа
        assertEquals(14.0, function.apply(4.0), 1e-10);
        assertEquals(19.0, function.apply(5.0), 1e-10);
    }
    @Test
    void testTwoLinkedListFunctionsComposition() {
        //f(x) = x + 1
        double[] xValues1 = {0.0, 1.0, 2.0, 3.0};
        double[] yValues1 = {1.0, 2.0, 3.0, 4.0};
        LinkedListTabulatedFunction func1 = new LinkedListTabulatedFunction(xValues1, yValues1);

        //g(x) = x * 2
        double[] xValues2 = {1.0, 2.0, 3.0, 4.0};
        double[] yValues2 = {2.0, 4.0, 6.0, 8.0};
        LinkedListTabulatedFunction func2 = new LinkedListTabulatedFunction(xValues2, yValues2);

        CompositeFunction composition = new CompositeFunction(func1, func2);

        assertEquals(2.0, composition.apply(0.0), 1e-10);
        assertEquals(4.0, composition.apply(1.0), 1e-10);
        assertEquals(6.0, composition.apply(2.0), 1e-10);
        assertEquals(5.0, composition.apply(1.5), 1e-10);
    }
    @Test
    void testLinkedListWithSimpleFunctionComposition() {
        //f(x) = x^2
        double[] xValues = {0.0, 1.0, 2.0, 3.0};
        double[] yValues = {0.0, 1.0, 4.0, 9.0};
        LinkedListTabulatedFunction linkedListFunc = new LinkedListTabulatedFunction(xValues, yValues);

        //g(x) = sin(x)
        MathFunction sinFunction = Math::sin;
        CompositeFunction sinOfSquare = new CompositeFunction(linkedListFunc, sinFunction);

        assertEquals(Math.sin(0.0), sinOfSquare.apply(0.0), 1e-10);
        assertEquals(Math.sin(1.0), sinOfSquare.apply(1.0), 1e-10);
        assertEquals(Math.sin(4.0), sinOfSquare.apply(2.0), 1e-10);
        assertEquals(Math.sin(2.25), sinOfSquare.apply(1.5), 1e-10);
    }
    @Test
    void testLinkedListExtrapolationInComposition() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 4.0, 9.0}; // x²
        LinkedListTabulatedFunction squareFunc = new LinkedListTabulatedFunction(xValues, yValues);

        //g(x) = sqrt(x)
        MathFunction sqrtFunction = Math::sqrt;

        CompositeFunction absFunction = new CompositeFunction(squareFunc, sqrtFunction);

        assertEquals(0.0, absFunction.apply(0.0), 1e-10);
        assertEquals(4.0, absFunction.apply(4.0), 1e-10);
    }
    @Test
    void testMultipleLinkedListCompositions() {
        // f(x) = 2x
        double[] xValues1 = {0.0, 1.0, 2.0};
        double[] yValues1 = {0.0, 2.0, 4.0};
        LinkedListTabulatedFunction doubleFunc = new LinkedListTabulatedFunction(xValues1, yValues1);

        // g(x) = x + 3
        double[] xValues2 = {0.0, 2.0, 4.0};
        double[] yValues2 = {3.0, 5.0, 7.0};
        LinkedListTabulatedFunction incrementFunc = new LinkedListTabulatedFunction(xValues2, yValues2);

        // h(x) = x^2
        double[] xValues3 = {3.0, 5.0, 7.0};
        double[] yValues3 = {9.0, 25.0, 49.0};
        LinkedListTabulatedFunction squareFunc = new LinkedListTabulatedFunction(xValues3, yValues3);

        CompositeFunction level1 = new CompositeFunction(doubleFunc, incrementFunc);
        CompositeFunction level2 = new CompositeFunction(level1, squareFunc);

        assertEquals(9.0, level2.apply(0.0), 1e-10);
        assertEquals(25.0, level2.apply(1.0), 1e-10);
        assertEquals(49.0, level2.apply(2.0), 1e-10);
        assertEquals(36.0, level2.apply(1.5), 1e-10);
    }
    @Test
    void testComplexInterpolationChain() {
        double[] xValues = {0.0, 0.5, 1.5, 3.0};
        double[] yValues = {0.0, 0.25, 2.25, 9.0};
        LinkedListTabulatedFunction irregularFunc = new LinkedListTabulatedFunction(xValues, yValues);

        double[] xValues2 = {0.0, 2.25, 9.0};
        double[] yValues2 = {0.0, 1.5, 3.0};
        ArrayTabulatedFunction sqrtFunc = new ArrayTabulatedFunction(xValues2, yValues2);

        CompositeFunction composition = new CompositeFunction(irregularFunc, sqrtFunc);

        assertEquals(0.0, composition.apply(0.0), 1e-10);
        assertEquals(0.5, composition.apply(0.5), 1e-10);
        assertEquals(1.0, composition.apply(1.0), 1e-10);
        assertEquals(1.5, composition.apply(1.5), 1e-10);
        assertEquals(2.0, composition.apply(2.0), 1e-10);
    }
    @Test
    void testInsertIntoEmptyList() {
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(
                new double[]{}, new double[]{}
        );

        function.insert(5.0, 25.0);

        assertEquals(1, function.getCount());
        assertEquals(5.0, function.getX(0), 1e-12);
        assertEquals(25.0, function.getY(0), 1e-12);
    }
    @Test
    void testInsertMaintainsOrder() {
        double[] xValues = {1.0, 5.0};
        double[] yValues = {1.0, 25.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        function.insert(3.0, 9.0);
        function.insert(4.0, 16.0);
        function.insert(2.0, 4.0);

        assertEquals(5, function.getCount());
        assertEquals(1.0, function.getX(0), 1e-12);
        assertEquals(2.0, function.getX(1), 1e-12);
        assertEquals(3.0, function.getX(2), 1e-12);
        assertEquals(4.0, function.getX(3), 1e-12);
        assertEquals(5.0, function.getX(4), 1e-12);
    }
    @Test
    void testInsertWithSingleNode() {
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(
                new double[]{2.0}, new double[]{4.0}
        );
        function.insert(1.0, 1.0);

        function.insert(3.0, 9.0);

        assertEquals(3, function.getCount());
        assertEquals(1.0, function.getX(0), 1e-12);
        assertEquals(2.0, function.getX(1), 1e-12);
        assertEquals(3.0, function.getX(2), 1e-12);
    }
    @Test
    void testInsertUpdatesCount() {
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(
                new double[]{}, new double[]{}
        );
        assertEquals(0, function.getCount());
        function.insert(1.0, 1.0);
        assertEquals(1, function.getCount());
        function.insert(2.0, 4.0);
        assertEquals(2, function.getCount());
        function.insert(1.5, 2.25);
        assertEquals(3, function.getCount());
        function.insert(1.5, 3.0);
        assertEquals(3, function.getCount());
    }
    @Test
    void testRemoveFromBeginning() {
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {1.0, 4.0, 9.0, 16.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        function.remove(0);

        assertEquals(3, function.getCount());
        assertEquals(2.0, function.leftBound(), 1e-10); // Новая левая граница
        assertEquals(2.0, function.getX(0), 1e-10);
        assertEquals(4.0, function.getY(0), 1e-10);
        assertEquals(3.0, function.getX(1), 1e-10);
        assertEquals(9.0, function.getY(1), 1e-10);
    }
    @Test
    void testRemoveFromEnd() {
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {1.0, 4.0, 9.0, 16.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        function.remove(3);

        assertEquals(3, function.getCount());
        assertEquals(3.0, function.rightBound(), 1e-10); // Новая правая граница
        assertEquals(1.0, function.getX(0), 1e-10);
        assertEquals(1.0, function.getY(0), 1e-10);
        assertEquals(3.0, function.getX(2), 1e-10);
        assertEquals(9.0, function.getY(2), 1e-10);
    }
    @Test
    void testRemoveFromMiddle() {
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {1.0, 4.0, 9.0, 16.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        function.remove(1);

        assertEquals(3, function.getCount());
        assertEquals(1.0, function.getX(0), 1e-10);
        assertEquals(1.0, function.getY(0), 1e-10);
        assertEquals(3.0, function.getX(1), 1e-10); // Элемент сдвинулся
        assertEquals(9.0, function.getY(1), 1e-10);
        assertEquals(4.0, function.getX(2), 1e-10);
        assertEquals(16.0, function.getY(2), 1e-10);
    }
}