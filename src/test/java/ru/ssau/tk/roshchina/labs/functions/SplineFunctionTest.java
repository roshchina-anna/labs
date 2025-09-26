package ru.ssau.tk.roshchina.labs.functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

 class SplineFunctionTest {
    @Test
    public void testLinearInterpolation() {
        double[] xArray = {0.0, 1.0, 2.0, 3.0};
        double[] yArray = {0.0, 1.0, 2.0, 3.0};
        SplineFunction spline = new SplineFunction(xArray, yArray);
        assertEquals(0.0, spline.apply(0.0), 0.000000001);
        assertEquals(1.0, spline.apply(1.0), 0.000000001);
        assertEquals(2.0, spline.apply(2.0), 0.000000001);
        assertEquals(3.0, spline.apply(3.0), 0.000000001);

        assertEquals(0.5, spline.apply(0.5), 0.000000001);
        assertEquals(1.5, spline.apply(1.5), 0.000000001);
        assertEquals(2.7, spline.apply(2.7), 0.000000001);
    }
    @Test
    public void testLeftExtrapolation() {
        double[] xArray = {1.0, 2.0, 3.0};
        double[] yArray = {1.0, 2.0, 3.0};
        SplineFunction spline = new SplineFunction(xArray, yArray);

        assertEquals(0.0, spline.apply(0.0), 0.000000001);
        assertEquals(-1.0, spline.apply(-1.0), 0.000000001);
    }
    @Test
    public void testRightExtrapolation() {
        double[] xArray = {0.0, 1.0, 2.0};
        double[] yArray = {0.0, 1.0, 4.0};
        SplineFunction spline = new SplineFunction(xArray, yArray);

        assertEquals(7.0, spline.apply(3.0), 0.000000001);
        assertEquals(10.0, spline.apply(4.0), 0.000000001);
    }
    @Test
    public void testTwoPoints() {
        double[] xArray = {0.0, 1.0};
        double[] yArray = {0.0, 2.0};
        SplineFunction spline = new SplineFunction(xArray, yArray);

        assertEquals(0.0, spline.apply(0.0), 0.000000001);
        assertEquals(1.0, spline.apply(0.5), 0.000000001);
        assertEquals(2.0, spline.apply(1.0), 0.000000001);
        assertEquals(4.0, spline.apply(2.0), 0.000000001);
    }
    @Test
    public void testNegativeValues() {
        double[] xArray = {-2.0, -1.0, 0.0, 1.0};
        double[] yArray = {4.0, 1.0, 0.0, 1.0};
        SplineFunction spline = new SplineFunction(xArray, yArray);

        assertEquals(4.0, spline.apply(-2.0), 0.000000001);
        assertEquals(2.5, spline.apply(-1.5), 0.000000001);
        assertEquals(0.5, spline.apply(-0.5), 0.000000001);
    }
    @Test
    public void testFractionalValues() {
        double[] xArray = {0.0, 0.5, 1.0, 1.5};
        double[] yArray = {0.0, 0.25, 1.0, 2.25};
        SplineFunction spline = new SplineFunction(xArray, yArray);

        assertEquals(0.25, spline.apply(0.5), 0.000000001);
        assertEquals(0.0625, spline.apply(0.25), 0.000000001);
        assertEquals(1.5625, spline.apply(1.25), 0.000000001);
    }
    @Test
    public void testDifferentArrayLengths() {
        double[] xArray = {0.0, 1.0, 2.0};
        double[] yArray = {0.0, 1.0};
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new SplineFunction(xArray, yArray));
        assertTrue(exception.getMessage().contains("Invalid arrays"));
    }
    @Test
    public void testNotEnoughPoints() {
        double[] xArray = {0.0};
        double[] yArray = {1.0};
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new SplineFunction(xArray, yArray));
        assertTrue(exception.getMessage().contains("Invalid arrays"));
    }
    @Test
    public void testNonStrictlyIncreasingX() {
        double[] xArray = {0.0, 1.0, 1.0, 2.0};
        double[] yArray = {0.0, 1.0, 2.0, 3.0};
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new SplineFunction(xArray, yArray));
        assertTrue(exception.getMessage().contains("xArray must be strictly increasing"));
    }
    @Test
    public void testDecreasingX() {
        double[] xArray = {2.0, 1.0, 3.0};
        double[] yArray = {2.0, 1.0, 3.0};
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new SplineFunction(xArray, yArray));
        assertTrue(exception.getMessage().contains("xArray must be strictly increasing"));
    }
    @Test
    public void testClosePoints() {
        double[] xArray = {0.0, 0.0001, 1.0};
        double[] yArray = {0.0, 0.0002, 1.0};
        SplineFunction spline = new SplineFunction(xArray, yArray);
        assertEquals(0.0001, spline.apply(0.00005), 0.000000001);
    }
    @Test
    public void testInterpolateMethod() {
        double[] xArray = {0.0, 10.0};
        double[] yArray = {0.0, 100.0};
        SplineFunction spline = new SplineFunction(xArray, yArray);
        assertEquals(0.0, spline.apply(0.0), 0.000000001);
        assertEquals(50.0, spline.apply(5.0), 0.000000001);
        assertEquals(100.0, spline.apply(10.0), 0.000000001);
        assertEquals(150.0, spline.apply(15.0), 0.000000001);
    }
}