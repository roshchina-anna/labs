package ru.ssau.tk.roshchina.labs.functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AbstractTabulatedFunctionTest {
    static class ExactValueFunction extends AbstractTabulatedFunction {
        private final double[] xValues = {0.0, 1.0, 2.0};
        private final double[] yValues = {0.0, 1.0, 4.0};

        @Override public int getCount() { return 3; }
        @Override public double getX(int index) { return xValues[index]; }
        @Override public double getY(int index) { return yValues[index]; }
        @Override public void setY(int index, double value) { yValues[index] = value; }
        @Override public int indexOfX(double x) {
            if (x == 0.0) return 0;
            if (x == 1.0) return 1;
            if (x == 2.0) return 2;
            return -1;
        }
        @Override public int indexOfY(double y) {
            if (y == 0.0) return 0;
            if (y == 1.0) return 1;
            if (y == 4.0) return 2;
            return -1;
        }
        @Override public double leftBound() { return 0.0; }
        @Override public double rightBound() { return 2.0; }
        @Override protected int indexMaxX(double x) {
            if (x <= 1.0) return 0;
            return 1;
        }
        @Override protected double extrapolateLeft(double x) { return x; }
        @Override protected double extrapolateRight(double x) { return 2*x + 1; }
        @Override protected double interpolate(double x, int indexLeft) {
            return interpolate(x, getX(indexLeft), getX(indexLeft+1), getY(indexLeft), getY(indexLeft+1));
        }
    }
    static class InterpolationFunction extends AbstractTabulatedFunction {
        private final double[] xValues = {0.0, 2.0};
        private final double[] yValues = {0.0, 4.0};
        @Override public int getCount() { return 2; }
        @Override public double getX(int index) { return xValues[index]; }
        @Override public double getY(int index) { return yValues[index]; }
        @Override public void setY(int index, double value) { yValues[index] = value; }
        @Override public int indexOfX(double x) { return -1; }
        @Override public int indexOfY(double y) { return -1; }
        @Override public double leftBound() { return 0.0; }
        @Override public double rightBound() { return 2.0; }
        @Override protected int indexMaxX(double x) { return 0; }
        @Override protected double extrapolateLeft(double x) { return x; }
        @Override protected double extrapolateRight(double x) { return 2*x; }
        @Override protected double interpolate(double x, int indexLeft) {
            return 2*x;
        }
    }
    @Test
    void applyZeroTest() {
        ExactValueFunction function = new ExactValueFunction();
        double result = function.apply(0.0);
        Assertions.assertEquals(0.0, result);
    }
    @Test
    void applyInterpolationTest() {
        InterpolationFunction function = new InterpolationFunction();
        double result = function.apply(1.0);
        Assertions.assertEquals(2.0, result);
    }
    @Test
    void applyExtrapolationLeftTest() {
        ExactValueFunction function = new ExactValueFunction();
        double result = function.apply(-1.0);
        Assertions.assertEquals(-1.0, result);
    }
    @Test
    void applyExtrapolationRightTest() {
        ExactValueFunction function = new ExactValueFunction();
        double result = function.apply(3.0);
        Assertions.assertEquals(7.0, result);
    }
    @Test
    void indexMaxXForSmallValueTest() {
        ExactValueFunction function = new ExactValueFunction();
        int result = function.indexMaxX(-5.0);
        Assertions.assertEquals(0, result);
    }
    @Test
    void indexMaxXForLargeValueTest() {
        ExactValueFunction function = new ExactValueFunction();
        int result = function.indexMaxX(5.0);
        Assertions.assertEquals(2, result);
    }
}
