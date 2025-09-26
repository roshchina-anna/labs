package ru.ssau.tk.roshchina.labs.functions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ArrayTabulatedFunctionTest {
    @Test
    void testConstructorWithArraysValidData() {
        double[] xArray = {1.0, 2.0, 3.0};
        double[] yArray = {10.0, 20.0, 30.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xArray, yArray);
        assertEquals(3, function.getCount());
        assertEquals(1.0, function.leftBound(), 0.000000000001);
        assertEquals(3.0, function.rightBound(), 0.000000000001);
    }
    @Test
    void testConstructorWithArraysDifferentLength() {
        double[] xArray = {1.0, 2.0};
        double[] yArray = {10.0, 20.0, 30.0};
        assertThrows(IllegalArgumentException.class, () -> {
            new ArrayTabulatedFunction(xArray, yArray);
        });
    }
    @Test
    void testConstructorWithArraysMinLength() {
        double[] xArray = {1.0};
        double[] yArray = {10.0};
        assertThrows(IllegalArgumentException.class, () -> {
            new ArrayTabulatedFunction(xArray, yArray);
        });
    }
    @Test
    void testConstructorWithArraysNotStrictlyIncreasing() {
        double[] xArray = {1.0, 1.5, 1.5, 2.0};
        double[] yArray = {1.0, 2.0, 3.0, 4.0};
        assertThrows(IllegalArgumentException.class, () -> {
            new ArrayTabulatedFunction(xArray, yArray);
        });
    }
    @Test
    void testConstructorWithArraysDecreasing() {
        double[] xArray = {3.0, 2.0, 1.0};
        double[] yArray = {3.0, 2.0, 1.0};
        assertThrows(IllegalArgumentException.class, () -> {
            new ArrayTabulatedFunction(xArray, yArray);
        });
    }
    @Test
    void testConstructorWithFunctionNormalCase() {
        MathFunction source = new SqrFunction();
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(source, 0.0, 4.0, 5);
        assertEquals(5, function.getCount());
        assertEquals(0.0, function.getX(0), 0.000000000001);
        assertEquals(1.0, function.getX(1), 0.000000000001);
        assertEquals(2.0, function.getX(2), 0.000000000001);
        assertEquals(3.0, function.getX(3), 0.000000000001);
        assertEquals(4.0, function.getX(4), 0.000000000001);
        assertEquals(0.0, function.getY(0), 0.000000000001);
        assertEquals(1.0, function.getY(1), 0.000000000001);
        assertEquals(4.0, function.getY(2), 0.000000000001);
        assertEquals(9.0, function.getY(3), 0.000000000001);
        assertEquals(16.0, function.getY(4), 0.000000000001);
    }
    @Test
    void testConstructorWithFunctionReversedBounds() {
        MathFunction source = new IdentityFunction();
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(source, 10.0, 0.0, 6);
        assertEquals(0.0, function.leftBound(), 0.000000000001);
        assertEquals(10.0, function.rightBound(), 0.000000000001);
        assertEquals(0.0, function.getX(0), 0.000000000001);
        assertEquals(10.0, function.getX(5), 0.000000000001);
    }
    @Test
    void testConstructorWithFunctionEqualBounds() {
        MathFunction source = new ConstantFunction(7.5);
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(source, 5.0, 5.0, 3);
        assertEquals(3, function.getCount());
        for (int i = 0; i < 3; i++) {
            assertEquals(5.0, function.getX(i), 0.000000000001);
            assertEquals(7.5, function.getY(i), 0.000000000001);
        }
    }
    @Test
    void testConstructorWithFunctionInvalidCount() {
        MathFunction source = new IdentityFunction();
        assertThrows(IllegalArgumentException.class, () -> {
            new ArrayTabulatedFunction(source, 0.0, 1.0, 1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new ArrayTabulatedFunction(source, 0.0, 1.0, 0);
        });
    }
    @Test
    void testGetCount() {
        double[] xArray = {1.0, 2.0, 3.0, 4.0};
        double[] yArray = {1.0, 4.0, 9.0, 16.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xArray, yArray);
        assertEquals(4, function.getCount());
    }
    @Test
    void testGetX() {
        double[] xArray = {-5.0, -2.5, 0.0, 2.5, 5.0};
        double[] yArray = {25.0, 6.25, 0.0, 6.25, 25.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xArray, yArray);
        assertEquals(-5.0, function.getX(0), 0.000000000001);
        assertEquals(0.0, function.getX(2), 0.000000000001);
        assertEquals(5.0, function.getX(4), 0.000000000001);
    }
    @Test
    void testGetXInvalidIndex() {
        double[] xArray = {1.0, 2.0};
        double[] yArray = {1.0, 4.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xArray, yArray);
        assertThrows(IndexOutOfBoundsException.class, () -> function.getX(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> function.getX(2));
        assertThrows(IndexOutOfBoundsException.class, () -> function.getX(100));
    }
    @Test
    void testGetY() {
        double[] xArray = {1.0, 2.0, 3.0};
        double[] yArray = {10.0, 20.0, 30.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xArray, yArray);
        assertEquals(10.0, function.getY(0), 0.000000000001);
        assertEquals(20.0, function.getY(1), 0.000000000001);
        assertEquals(30.0, function.getY(2), 0.000000000001);
    }
    @Test
    void testGetYInvalidIndex() {
        double[] xArray = {1.0, 2.0};
        double[] yArray = {1.0, 4.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xArray, yArray);
        assertThrows(IndexOutOfBoundsException.class, () -> function.getY(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> function.getY(2));
    }
    @Test
    void testSetY() {
        double[] xArray = {1.0, 2.0, 3.0};
        double[] yArray = {1.0, 4.0, 9.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xArray, yArray);
        function.setY(0, 100.0);
        assertEquals(100.0, function.getY(0), 0.000000000001);
        function.setY(1, 200.0);
        assertEquals(200.0, function.getY(1), 0.000000000001);
        function.setY(2, 300.0);
        assertEquals(300.0, function.getY(2), 0.000000000001);
    }
    @Test
    void testSetYInvalidIndex() {
        double[] xArray = {1.0, 2.0};
        double[] yArray = {1.0, 4.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xArray, yArray);
        assertThrows(IndexOutOfBoundsException.class, () -> function.setY(-1, 10.0));
        assertThrows(IndexOutOfBoundsException.class, () -> function.setY(2, 10.0));
    }
    @Test
    void testIndexOfX() {
        double[] xArray = {-3.0, -1.0, 1.0, 3.0};
        double[] yArray = {9.0, 1.0, 1.0, 9.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xArray, yArray);
        assertEquals(0, function.indexOfX(-3.0));
        assertEquals(1, function.indexOfX(-1.0));
        assertEquals(2, function.indexOfX(1.0));
        assertEquals(3, function.indexOfX(3.0));
        assertEquals(-1, function.indexOfX(0.0));
        assertEquals(-1, function.indexOfX(5.0));
        assertEquals(-1, function.indexOfX(-5.0));
    }
    @Test
    void testIndexOfXPrecision() {
        double[] xArray = {1.0, 2.0, 3.0};
        double[] yArray = {1.0, 4.0, 9.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xArray, yArray);
        assertEquals(1, function.indexOfX(2.0 + 1e-13));
        assertEquals(1, function.indexOfX(2.0 - 1e-13));
    }
    @Test
    void testIndexOfY() {
        double[] xArray = {1.0, 2.0, 3.0};
        double[] yArray = {5.0, 10.0, 15.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xArray, yArray);
        assertEquals(0, function.indexOfY(5.0));
        assertEquals(1, function.indexOfY(10.0));
        assertEquals(2, function.indexOfY(15.0));
        assertEquals(-1, function.indexOfY(0.0));
        assertEquals(-1, function.indexOfY(7.5));
    }
    @Test
    void testIndexOfYWithDuplicates() {
        double[] xArray = {1.0, 2.0, 3.0, 4.0};
        double[] yArray = {5.0, 10.0, 5.0, 10.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xArray, yArray);
        assertEquals(0, function.indexOfY(5.0));
        assertEquals(1, function.indexOfY(10.0));
    }
    @Test
    void testApplyExactValues() {
        double[] xArray = {1.0, 2.0, 3.0};
        double[] yArray = {1.0, 4.0, 9.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xArray, yArray);
        assertEquals(1.0, function.apply(1.0), 0.000000000001);
        assertEquals(4.0, function.apply(2.0), 0.000000000001);
        assertEquals(9.0, function.apply(3.0), 0.000000000001);
    }
    @Test
    void testApplyInterpolation() {
        double[] xArray = {0.0, 1.0, 2.0};
        double[] yArray = {0.0, 1.0, 4.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xArray, yArray);
        assertEquals(0.5, function.apply(0.5), 0.000000000001);
        assertEquals(2.25, function.apply(1.5), 0.000000000001);
    }
    @Test
    void testApplyExtrapolationLeft() {
        double[] xArray = {2.0, 3.0, 4.0};
        double[] yArray = {4.0, 9.0, 16.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xArray, yArray);
        assertEquals(-1.0, function.apply(1.0), 0.000000000001);
    }
    @Test
    void testApplyExtrapolationRight() {
        double[] xArray = {1.0, 2.0, 3.0};
        double[] yArray = {1.0, 4.0, 9.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xArray, yArray);
        assertEquals(14.0, function.apply(4.0), 0.000000000001);
    }
    @Test
    void testApplySinglePointFunction() {
        MathFunction source = new ConstantFunction(5.0);
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(source, 2.0, 2.0, 1);
        assertEquals(5.0, function.apply(0.0), 0.000000000001);
        assertEquals(5.0, function.apply(2.0), 0.000000000001);
        assertEquals(5.0, function.apply(10.0), 0.000000000001);
    }

}