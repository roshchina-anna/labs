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
        assertThrows(IllegalArgumentException.class, () -> new ArrayTabulatedFunction(xArray, yArray));
    }
    @Test
    void testConstructorWithArraysMinLength() {
        double[] xArray = {1.0};
        double[] yArray = {10.0};
        assertThrows(IllegalArgumentException.class, () -> new ArrayTabulatedFunction(xArray, yArray));
    }
    @Test
    void testConstructorWithArraysNotStrictlyIncreasing() {
        double[] xArray = {1.0, 1.5, 1.5, 2.0};
        double[] yArray = {1.0, 2.0, 3.0, 4.0};
        assertThrows(IllegalArgumentException.class, () -> new ArrayTabulatedFunction(xArray, yArray));
    }
    @Test
    void testConstructorWithArraysDecreasing() {
        double[] xArray = {3.0, 2.0, 1.0};
        double[] yArray = {3.0, 2.0, 1.0};
        assertThrows(IllegalArgumentException.class, () -> new ArrayTabulatedFunction(xArray, yArray));
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
        assertThrows(IllegalArgumentException.class, () -> new ArrayTabulatedFunction(source, 0.0, 1.0, 1));
        assertThrows(IllegalArgumentException.class, () -> new ArrayTabulatedFunction(source, 0.0, 1.0, 0));
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
    @Test
    public void testTabulatedFunctionWithSimpleMathFunction() {
        MathFunction sqrFunction = x -> x * x;
        ArrayTabulatedFunction tabulatedSqr = new ArrayTabulatedFunction(sqrFunction, 0, 3, 4);
        assertEquals(0.0,tabulatedSqr.getY(0));
        assertEquals(1.0, tabulatedSqr.getY(1));
        assertEquals(4.0, tabulatedSqr.getY(2));
        assertEquals(9.0,tabulatedSqr.getY(3));
    }
    @Test
    public void testMultipleTabulatedFunctions() {
        double[] xValues1 = {1.0, 2.0, 3.0};
        double[] yValues1 = {2.0, 4.0, 6.0};
        ArrayTabulatedFunction func1 = new ArrayTabulatedFunction(xValues1, yValues1);
        double[] xValues2 = {1.0, 2.0, 3.0};
        double[] yValues2 = {1.0, 4.0, 9.0};
        ArrayTabulatedFunction func2 = new ArrayTabulatedFunction(xValues2, yValues2);
        assertEquals(4.0,func1.getY(1));
        assertEquals(4.0,func2.getY(1));
        assertEquals(6.0, func1.getY(2));
        assertEquals(9.0, func2.getY(2));
    }
    @Test
    public void testTabulatedFunctionAsMathFunction() {
        double[] xValues = {0.0, 1.0, 2.0, 3.0};
        double[] yValues = {0.0, 1.0, 4.0, 9.0};
        ArrayTabulatedFunction tabulatedFunc = new ArrayTabulatedFunction(xValues, yValues);
        ArrayTabulatedFunction newTabulated = new ArrayTabulatedFunction(tabulatedFunc, -1, 4, 6);
        assertEquals(6, newTabulated.getCount());
        assertTrue(newTabulated.getY(0) >= 0);
        assertTrue(newTabulated.getY(5) >= 0);
    }
    @Test
    public void testComplexTabulatedCombination() {
        double[] xValues1 = {0.0, 1.0, 2.0, 3.0};
        double[] yValues1 = {0.0, 2.0, 4.0, 6.0};
        ArrayTabulatedFunction doubleFunction = new ArrayTabulatedFunction(xValues1, yValues1);
        MathFunction plusOne = x -> x + 1;
        MathFunction composite = x -> doubleFunction.apply(plusOne.apply(x));
        ArrayTabulatedFunction tabulatedComposite = new ArrayTabulatedFunction(composite, 0, 2, 3);
        assertEquals(2.0,tabulatedComposite.getY(0));
        assertEquals(4.0,tabulatedComposite.getY(1));
        assertEquals(6.0,tabulatedComposite.getY(2));
    }
    @Test
    public void testSumOfTabulatedAndMathFunction() {
        double[] xValues = {0.0, 1.0, 2.0, 3.0};
        double[] yValues = {0.0, 1.0, 2.0, 3.0};
        ArrayTabulatedFunction identityTabulated = new ArrayTabulatedFunction(xValues, yValues);
        MathFunction sqrFunction = x -> x * x;
        MathFunction sumFunction = x -> identityTabulated.apply(x) + sqrFunction.apply(x);
        ArrayTabulatedFunction tabulatedSum = new ArrayTabulatedFunction(sumFunction, 0, 3, 4);
        assertEquals(0.0,tabulatedSum.getY(0));
        assertEquals(2.0,tabulatedSum.getY(1));
        assertEquals(6.0,tabulatedSum.getY(2));
        assertEquals(12.0,tabulatedSum.getY(3));
    }
    @Test
    public void testProductOfFunctions() {
        double[] xValues1 = {0.0, 1.0, 2.0, 3.0};
        double[] yValues1 = {0.0, 2.0, 4.0, 6.0};
        ArrayTabulatedFunction doubleFunction = new ArrayTabulatedFunction(xValues1, yValues1);
        MathFunction tripleFunction = x -> 3 * x;
        MathFunction productFunction = x -> doubleFunction.apply(x) * tripleFunction.apply(x);
        ArrayTabulatedFunction tabulatedProduct = new ArrayTabulatedFunction(productFunction, 0, 2, 3);

        assertEquals(0.0,tabulatedProduct.getY(0));
        assertEquals(6.0,tabulatedProduct.getY(1));
        assertEquals(24.0,tabulatedProduct.getY(2));
    }
    @Test
    public void testEdgeCasesWithComplexFunctions() {
        double[] singleX = {1.0};
        double[] singleY = {5.0};
        ArrayTabulatedFunction singlePoint = new ArrayTabulatedFunction(singleX, singleY);
        MathFunction square = x -> x * x;
        MathFunction complex = x -> singlePoint.apply(x) + square.apply(x);
        ArrayTabulatedFunction result = new ArrayTabulatedFunction(complex, 0, 2, 3);
        assertEquals(6.0,result.getY(1));
    }
    @Test
    public void testDifferentStepSizes() {
        double[] xValues1 = {0.0, 5.0};
        double[] yValues1 = {0.0, 25.0};
        ArrayTabulatedFunction coarse = new ArrayTabulatedFunction(xValues1, yValues1);
        MathFunction fineFunction = x -> coarse.apply(x) + x;
        ArrayTabulatedFunction fine = new ArrayTabulatedFunction(fineFunction, 0, 5, 10);
        assertEquals(10,fine.getCount());
        assertEquals(0.0,fine.getY(0));
        assertEquals(30.0,fine.getY(5));
    }
    @Test
    void testInsertUpdateExistingValue() {
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {1.0, 4.0, 9.0, 16.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);
        function.insert(2.0, 8.0);

        assertEquals(4, function.getCount()); // Количество не должно измениться
        assertEquals(8.0, function.getY(1), 1e-10); // Значение обновилось
        assertEquals(1.0, function.getY(0), 1e-10); // Другие значения остались прежними
        assertEquals(9.0, function.getY(2), 1e-10);
        assertEquals(16.0, function.getY(3), 1e-10);
    }
    @Test
    void testInsertAtBeginning() {
        double[] xValues = {2.0, 3.0, 4.0};
        double[] yValues = {4.0, 9.0, 16.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        function.insert(1.0, 1.0);

        assertEquals(4, function.getCount());
        assertEquals(1.0, function.leftBound(), 1e-10); // Левая граница изменилась
        assertEquals(1.0, function.getX(0), 1e-10); // Новый элемент на позиции 0
        assertEquals(1.0, function.getY(0), 1e-10);
        assertEquals(2.0, function.getX(1), 1e-10); // Старые элементы сдвинулись
        assertEquals(4.0, function.getY(1), 1e-10);
    }
    @Test
    void testInsertAtEnd() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 4.0, 9.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        function.insert(4.0, 16.0);

        assertEquals(4, function.getCount());
        assertEquals(4.0, function.rightBound(), 1e-10); // Правая граница изменилась
        assertEquals(4.0, function.getX(3), 1e-10); // Новый элемент на последней позиции
        assertEquals(16.0, function.getY(3), 1e-10);
        assertEquals(3.0, function.getX(2), 1e-10); // Предыдущий последний элемент сдвинулся
        assertEquals(9.0, function.getY(2), 1e-10);
    }
    @Test
    void testInsertInMiddle() {
        double[] xValues = {1.0, 3.0, 5.0};
        double[] yValues = {1.0, 9.0, 25.0};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        function.insert(2.0, 4.0);
        function.insert(4.0, 16.0);

        assertEquals(5, function.getCount());

        assertEquals(1.0, function.getX(0), 1e-10);
        assertEquals(2.0, function.getX(1), 1e-10);
        assertEquals(3.0, function.getX(2), 1e-10);
        assertEquals(4.0, function.getX(3), 1e-10);
        assertEquals(5.0, function.getX(4), 1e-10);

        assertEquals(1.0, function.getY(0), 1e-10);
        assertEquals(4.0, function.getY(1), 1e-10);
        assertEquals(9.0, function.getY(2), 1e-10);
        assertEquals(16.0, function.getY(3), 1e-10);
        assertEquals(25.0, function.getY(4), 1e-10);
    }
}