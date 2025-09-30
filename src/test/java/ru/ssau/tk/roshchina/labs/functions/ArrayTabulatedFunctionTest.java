package ru.ssau.tk.roshchina.labs.functions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ArrayTabulatedFunctionTest {

    private final double[] xValues = {1.0, 2.0, 3.0, 4.0, 5.0};
    private final double[] yValues = {2.0, 4.0, 6.0, 8.0, 10.0};
    private final double[] singlePointX = {1.0};
    private final double[] singlePointY = {5.0};
    private final double[] emptyArray = {};

    @Test
    void testConstructorWithMathFunction() {
        MathFunction source = x -> x * x;
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(source, 0.0, 4.0, 5);

        assertEquals(5, function.getCount());
        assertEquals(0.0, function.getX(0), 1e-12);
        assertEquals(4.0, function.getX(4), 1e-12);
        assertEquals(0.0, function.getY(0), 1e-12);
        assertEquals(16.0, function.getY(4), 1e-12);

        ArrayTabulatedFunction reversedFunction = new ArrayTabulatedFunction(source, 4.0, 0.0, 5);
        assertEquals(0.0, reversedFunction.getX(0), 1e-12);
        assertEquals(4.0, reversedFunction.getX(4), 1e-12);

        ArrayTabulatedFunction singlePointFunction = new ArrayTabulatedFunction(source, 2.0, 2.0, 3);
        assertEquals(3, singlePointFunction.getCount());
        assertEquals(2.0, singlePointFunction.getX(0), 1e-12);
        assertEquals(2.0, singlePointFunction.getX(1), 1e-12);
        assertEquals(2.0, singlePointFunction.getX(2), 1e-12);
        assertEquals(4.0, singlePointFunction.getY(0), 1e-12);
        assertEquals(4.0, singlePointFunction.getY(1), 1e-12);
        assertEquals(4.0, singlePointFunction.getY(2), 1e-12);
    }

    @Test
    void testGetCount() {
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);
        assertEquals(5, function.getCount());

        ArrayTabulatedFunction singlePointFunction = new ArrayTabulatedFunction(singlePointX, singlePointY);
        assertEquals(1, singlePointFunction.getCount());
    }

    @Test
    void testGetX() {
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        assertEquals(1.0, function.getX(0), 1e-12);
        assertEquals(3.0, function.getX(2), 1e-12);
        assertEquals(5.0, function.getX(4), 1e-12);

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> function.getX(-1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> function.getX(5));
    }

    @Test
    void testGetY() {
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        assertEquals(2.0, function.getY(0), 1e-12);
        assertEquals(6.0, function.getY(2), 1e-12);
        assertEquals(10.0, function.getY(4), 1e-12);

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> function.getY(-1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> function.getY(5));
    }

    @Test
    void testSetY() {
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        function.setY(2, 15.0);
        assertEquals(15.0, function.getY(2), 1e-12);

        function.setY(0, -5.0);
        assertEquals(-5.0, function.getY(0), 1e-12);

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> function.setY(-1, 0.0));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> function.setY(5, 0.0));
    }

    @Test
    void testIndexOfX() {
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        assertEquals(0, function.indexOfX(1.0));
        assertEquals(2, function.indexOfX(3.0));
        assertEquals(4, function.indexOfX(5.0));
        assertEquals(-1, function.indexOfX(0.0));
        assertEquals(-1, function.indexOfX(6.0));

        assertEquals(1, function.indexOfX(2.0 + 1e-13));
        assertEquals(-1, function.indexOfX(2.0 + 1e-10)); // За пределами погрешности
    }

    @Test
    void testIndexOfY() {
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        assertEquals(0, function.indexOfY(2.0));
        assertEquals(2, function.indexOfY(6.0));
        assertEquals(4, function.indexOfY(10.0));
        assertEquals(-1, function.indexOfY(0.0));
        assertEquals(-1, function.indexOfY(15.0));

        assertEquals(1, function.indexOfY(4.0 + 1e-13));
        assertEquals(-1, function.indexOfY(4.0 + 1e-10));
    }

    @Test
    void testLeftBound() {
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);
        assertEquals(1.0, function.leftBound(), 1e-12);

        ArrayTabulatedFunction singlePointFunction = new ArrayTabulatedFunction(singlePointX, singlePointY);
        assertEquals(1.0, singlePointFunction.leftBound(), 1e-12);
    }

    @Test
    void testRightBound() {
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);
        assertEquals(5.0, function.rightBound(), 1e-12);

        ArrayTabulatedFunction singlePointFunction = new ArrayTabulatedFunction(singlePointX, singlePointY);
        assertEquals(1.0, singlePointFunction.rightBound(), 1e-12);
    }

    @Test
    void testFloorIndexOfX() {
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        assertEquals(0, function.floorIndexOfX(1.0));
        assertEquals(2, function.floorIndexOfX(3.0));
        assertEquals(4, function.floorIndexOfX(5.0));

        assertEquals(0, function.floorIndexOfX(1.5));
        assertEquals(1, function.floorIndexOfX(2.5));
        assertEquals(3, function.floorIndexOfX(4.5));


        assertEquals(0, function.floorIndexOfX(0.5));
        assertEquals(5, function.floorIndexOfX(5.5));
        assertEquals(5, function.floorIndexOfX(10.0));


        ArrayTabulatedFunction singlePointFunction = new ArrayTabulatedFunction(singlePointX, singlePointY);
        assertEquals(0, singlePointFunction.floorIndexOfX(1.0));
        assertEquals(0, singlePointFunction.floorIndexOfX(0.5));
        assertEquals(1, singlePointFunction.floorIndexOfX(1.5));
    }

    @Test
    void testExtrapolateLeft() {
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);


        double result = function.extrapolateLeft(0.0);
        double expected = 0.0;
        assertEquals(expected, result, 1e-12);


        ArrayTabulatedFunction singlePointFunction = new ArrayTabulatedFunction(singlePointX, singlePointY);
        assertEquals(5.0, singlePointFunction.extrapolateLeft(0.0), 1e-12);
        assertEquals(5.0, singlePointFunction.extrapolateLeft(10.0), 1e-12);
    }

    @Test
    void testExtrapolateRight() {
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        double result = function.extrapolateRight(6.0);
        double expected = 12.0; // Линейная экстраполяция: y = 2x
        assertEquals(expected, result, 1e-12);

        ArrayTabulatedFunction singlePointFunction = new ArrayTabulatedFunction(singlePointX, singlePointY);
        assertEquals(5.0, singlePointFunction.extrapolateRight(10.0), 1e-12);
        assertEquals(5.0, singlePointFunction.extrapolateRight(0.0), 1e-12);
    }

    @Test
    void testInterpolate() {
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        double result = function.interpolate(2.5, 1);
        double expected = 5.0;
        assertEquals(expected, result, 1e-12);

        double result2 = function.interpolate(1.5, 0);
        double expected2 = 3.0;
        assertEquals(expected2, result2, 1e-12);

        ArrayTabulatedFunction singlePointFunction = new ArrayTabulatedFunction(singlePointX, singlePointY);
        assertEquals(5.0, singlePointFunction.interpolate(1.5, 0), 1e-12);
    }

    @Test
    void testApply() {
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        assertEquals(2.0, function.apply(1.0), 1e-12);
        assertEquals(6.0, function.apply(3.0), 1e-12);
        assertEquals(10.0, function.apply(5.0), 1e-12);

        assertEquals(5.0, function.apply(2.5), 1e-12);
        assertEquals(7.0, function.apply(3.5), 1e-12);

        assertEquals(0.0, function.apply(0.0), 1e-12); // Слева
        assertEquals(12.0, function.apply(6.0), 1e-12); // Справа

        ArrayTabulatedFunction singlePointFunction = new ArrayTabulatedFunction(singlePointX, singlePointY);
        assertEquals(5.0, singlePointFunction.apply(1.0), 1e-12);
        assertEquals(5.0, singlePointFunction.apply(0.0), 1e-12);
        assertEquals(5.0, singlePointFunction.apply(10.0), 1e-12);
    }

    @Test
    void testInsert() {
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        function.insert(0.5, 1.0);
        assertEquals(6, function.getCount());
        assertEquals(0.5, function.getX(0), 1e-12);
        assertEquals(1.0, function.getY(0), 1e-12);

        function.insert(6.0, 12.0);
        assertEquals(7, function.getCount());
        assertEquals(6.0, function.getX(6), 1e-12);
        assertEquals(12.0, function.getY(6), 1e-12);

        function.insert(2.5, 5.0);
        assertEquals(8, function.getCount());
        assertEquals(2.5, function.getX(3), 1e-12);
        assertEquals(5.0, function.getY(3), 1e-12);

        function.insert(2.5, 7.0);
        assertEquals(8, function.getCount());
        assertEquals(2.5, function.getX(3), 1e-12);
        assertEquals(7.0, function.getY(3), 1e-12);

        ArrayTabulatedFunction singlePointFunction = new ArrayTabulatedFunction(singlePointX, singlePointY);
        singlePointFunction.insert(0.5, 3.0);
        assertEquals(2, singlePointFunction.getCount());
        assertEquals(0.5, singlePointFunction.getX(0), 1e-12);
        assertEquals(3.0, singlePointFunction.getY(0), 1e-12);
    }

    @Test
    void testRemove() {
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        function.remove(0);
        assertEquals(4, function.getCount());
        assertEquals(2.0, function.getX(0), 1e-12);
        assertEquals(4.0, function.getY(0), 1e-12);

        function.remove(function.getCount() - 1);
        assertEquals(3, function.getCount());
        assertEquals(4.0, function.getX(function.getCount() - 1), 1e-12);
        assertEquals(8.0, function.getY(function.getCount() - 1), 1e-12);


        function.remove(1);
        assertEquals(2, function.getCount());
        assertEquals(2.0, function.getX(0), 1e-12);
        assertEquals(4.0, function.getX(1), 1e-12);
        assertEquals(4.0, function.getY(0), 1e-12);
        assertEquals(8.0, function.getY(1), 1e-12);

        ArrayTabulatedFunction smallFunction = new ArrayTabulatedFunction(xValues, yValues);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> smallFunction.remove(-1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> smallFunction.remove(5));
    }

    @Test
    void testToString() {
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);
        String result = function.toString();

        assertNotNull(result);
        assertTrue(result.contains("ArrayTabulatedFunction"));
        assertTrue(result.contains("count = 5"));
    }
}