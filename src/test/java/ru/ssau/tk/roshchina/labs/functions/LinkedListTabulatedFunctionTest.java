package ru.ssau.tk.roshchina.labs.functions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LinkedListTabulatedFunctionTest {

    @Test
    public void testConstructorFromArrays() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {10.0, 20.0, 30.0};

        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        assertEquals(3, function.getCount());
        assertEquals(1.0, function.getX(0), 0.0001);
        assertEquals(10.0, function.getY(0), 0.0001);
        assertEquals(2.0, function.getX(1), 0.0001);
        assertEquals(20.0, function.getY(1), 0.0001);
    }

    @Test
    public void testConstructorFromMathFunction() {
        MathFunction source = new SqrFunction();
        double xFrom = 0.0;
        double xTo = 2.0;
        int count = 3;

        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(source, xFrom, xTo, count);

        assertEquals(3, function.getCount());
        assertEquals(0.0, function.getX(0), 0.0001);
        assertEquals(0.0, function.getY(0), 0.0001);
        assertEquals(1.0, function.getX(1), 0.0001);
        assertEquals(1.0, function.getY(1), 0.0001);
        assertEquals(2.0, function.getX(2), 0.0001);
        assertEquals(4.0, function.getY(2), 0.0001);
    }

    @Test
    public void testConstructorFromMathFunctionWithXFromGreaterThanXTo() {
        MathFunction source = new IdentityFunction();
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(source, 5.0, 1.0, 3);

        assertEquals(3, function.getCount());
        assertEquals(1.0, function.getX(0), 0.0001);
        assertEquals(5.0, function.getX(2), 0.0001);
    }

    @Test
    public void testConstructorFromMathFunctionWithEqualBounds() {
        MathFunction source = new ConstantFunction(5.0);
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(source, 3.0, 3.0, 4);

        assertEquals(4, function.getCount());
        for (int i = 0; i < 4; i++) {
            assertEquals(3.0, function.getX(i), 0.0001);
            assertEquals(5.0, function.getY(i), 0.0001);
        }
    }

    @Test
    public void testGetCount() {
        double[] xValues = {1.0, 2.0};
        double[] yValues = {1.0, 2.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        assertEquals(2, function.getCount());
    }

    @Test
    public void testLeftAndRightBound() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {10.0, 20.0, 30.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        assertEquals(1.0, function.leftBound(), 0.0001);
        assertEquals(3.0, function.rightBound(), 0.0001);
    }

    @Test
    public void testGetXAndGetY() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {10.0, 20.0, 30.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        assertEquals(1.0, function.getX(0), 0.0001);
        assertEquals(10.0, function.getY(0), 0.0001);
        assertEquals(2.0, function.getX(1), 0.0001);
        assertEquals(20.0, function.getY(1), 0.0001);
    }


    @Test
    public void testSetY() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {10.0, 20.0, 30.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        function.setY(1, 25.0);
        assertEquals(25.0, function.getY(1), 0.0001);
    }

    @Test
    public void testIndexOfX() {
        double[] xValues = {1.0, 2.0, 3.0, 2.0};
        double[] yValues = {10.0, 20.0, 30.0, 40.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        assertEquals(1, function.indexOfX(2.0)); // первое вхождение
        assertEquals(-1, function.indexOfX(5.0)); // не существует
    }

    @Test
    public void testIndexOfY() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {10.0, 20.0, 20.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        assertEquals(1, function.indexOfY(20.0)); // первое вхождение
        assertEquals(-1, function.indexOfY(50.0)); // не существует
    }

    @Test
    public void testFloorIndexOfX() {
        double[] xValues = {1.0, 2.0, 4.0, 5.0};
        double[] yValues = {1.0, 2.0, 4.0, 5.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        assertEquals(0, function.floorIndexOfX(0.5)); // меньше минимального
        assertEquals(0, function.floorIndexOfX(1.0)); // равно первому
        assertEquals(1, function.floorIndexOfX(3.0)); // между 2 и 4
        assertEquals(2, function.floorIndexOfX(4.0)); // равно существующему
        assertEquals(4, function.floorIndexOfX(6.0)); // больше максимального
    }

    @Test
    public void testExtrapolateLeft() {
        // Одна точка
        double[] singlePointX = {2.0};
        double[] singlePointY = {5.0};
        LinkedListTabulatedFunction singlePointFunc = new LinkedListTabulatedFunction(singlePointX, singlePointY);
        assertEquals(5.0, singlePointFunc.apply(1.0), 0.0001);

        // Несколько точек
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 4.0, 9.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        double result = function.apply(0.0); // экстраполяция слева
        assertEquals(-2.0, result, 0.0001); // линейная экстраполяция: 1 + (0-1)*(4-1)/(2-1) = 1 - 3 = -2
    }

    @Test
    public void testExtrapolateRight() {
        // Одна точка
        double[] singlePointX = {2.0};
        double[] singlePointY = {5.0};
        LinkedListTabulatedFunction singlePointFunc = new LinkedListTabulatedFunction(singlePointX, singlePointY);
        assertEquals(5.0, singlePointFunc.apply(3.0), 0.0001);

        // Несколько точек
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 4.0, 9.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        double result = function.apply(4.0); // экстраполяция справа
        assertEquals(14.0, result, 0.0001); // линейная экстраполяция: 9 + (4-3)*(9-4)/(3-2) = 9 + 5 = 14
    }

    @Test
    public void testInterpolate() {
        // Одна точка
        double[] singlePointX = {2.0};
        double[] singlePointY = {5.0};
        LinkedListTabulatedFunction singlePointFunc = new LinkedListTabulatedFunction(singlePointX, singlePointY);
        assertEquals(5.0, singlePointFunc.apply(2.5), 0.0001);

        // Несколько точек
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 4.0, 9.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        // Интерполяция между точками 1 и 2
        double result = function.apply(1.5);
        assertEquals(2.5, result, 0.0001); // линейная интерполяция: 1 + (1.5-1)*(4-1)/(2-1) = 1 + 1.5 = 2.5
    }

    @Test
    public void testInsertIntoEmptyList() {
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(new double[0], new double[0]);
        function.insert(1.0, 10.0);

        assertEquals(1, function.getCount());
        assertEquals(1.0, function.getX(0), 0.0001);
        assertEquals(10.0, function.getY(0), 0.0001);
    }

    @Test
    public void testInsertAtBeginning() {
        double[] xValues = {2.0, 3.0};
        double[] yValues = {20.0, 30.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        function.insert(1.0, 10.0);

        assertEquals(3, function.getCount());
        assertEquals(1.0, function.getX(0), 0.0001);
        assertEquals(10.0, function.getY(0), 0.0001);
        assertEquals(2.0, function.getX(1), 0.0001);
    }

    @Test
    public void testInsertAtEnd() {
        double[] xValues = {1.0, 2.0};
        double[] yValues = {10.0, 20.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        function.insert(3.0, 30.0);

        assertEquals(3, function.getCount());
        assertEquals(3.0, function.getX(2), 0.0001);
        assertEquals(30.0, function.getY(2), 0.0001);
    }

    @Test
    public void testInsertInMiddle() {
        double[] xValues = {1.0, 3.0};
        double[] yValues = {10.0, 30.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        function.insert(2.0, 20.0);

        assertEquals(3, function.getCount());
        assertEquals(2.0, function.getX(1), 0.0001);
        assertEquals(20.0, function.getY(1), 0.0001);
    }

    @Test
    public void testInsertDuplicateX() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {10.0, 20.0, 30.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        function.insert(2.0, 25.0); // обновление существующего Y

        assertEquals(3, function.getCount()); // количество не изменилось
        assertEquals(25.0, function.getY(1), 0.0001); // Y обновился
    }

    @Test
    public void testRemoveOnlyElement() {
        double[] xValues = {1.0};
        double[] yValues = {10.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        function.remove(0);

        assertEquals(0, function.getCount());
    }

    @Test
    public void testRemoveFirstElement() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {10.0, 20.0, 30.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        function.remove(0);

        assertEquals(2, function.getCount());
        assertEquals(2.0, function.getX(0), 0.0001);
        assertEquals(20.0, function.getY(0), 0.0001);
    }

    @Test
    public void testRemoveMiddleElement() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {10.0, 20.0, 30.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        function.remove(1);

        assertEquals(2, function.getCount());
        assertEquals(1.0, function.getX(0), 0.0001);
        assertEquals(3.0, function.getX(1), 0.0001);
    }

    @Test
    public void testRemoveLastElement() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {10.0, 20.0, 30.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        function.remove(2);

        assertEquals(2, function.getCount());
        assertEquals(1.0, function.getX(0), 0.0001);
        assertEquals(2.0, function.getX(1), 0.0001);
    }

    @Test
    public void testApplyWithVariousScenarios() {
        double[] xValues = {1.0, 2.0, 4.0};
        double[] yValues = {1.0, 4.0, 16.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        // Экстраполяция слева
        assertEquals(-2.0, function.apply(0.0), 0.0001);

        // Точно в узле
        assertEquals(4.0, function.apply(2.0), 0.0001);

        // Интерполяция
        assertEquals(10.0, function.apply(3.0), 0.0001);

        // Экстраполяция справа
        assertEquals(22.0, function.apply(5.0), 0.0001);
    }


    @Test
    public void testGetNodeOptimization() {
        double[] xValues = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] yValues = {1.0, 2.0, 3.0, 4.0, 5.0};
        LinkedListTabulatedFunction function = new LinkedListTabulatedFunction(xValues, yValues);

        assertEquals(1.0, function.getX(0), 0.0001); // начало списка
        assertEquals(3.0, function.getX(2), 0.0001); // середина
        assertEquals(5.0, function.getX(4), 0.0001); // конец списка (через backward traversal)
    }
}