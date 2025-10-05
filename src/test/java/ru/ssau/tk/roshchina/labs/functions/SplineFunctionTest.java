package ru.ssau.tk.roshchina.labs.functions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SplineFunctionTest {

    @Test
    public void testConstructorValidInput() {
        double[] xArray = {1.0, 2.0, 3.0, 4.0};
        double[] yArray = {1.0, 4.0, 9.0, 16.0};

        SplineFunction spline = new SplineFunction(xArray, yArray);
        assertNotNull(spline);
    }

    @Test
    public void testConstructorDifferentArrayLengths() {
        double[] xArray = {1.0, 2.0, 3.0};
        double[] yArray = {1.0, 4.0};

        assertThrows(IllegalArgumentException.class, () -> new SplineFunction(xArray, yArray));
    }

    @Test
    public void testConstructorSmallArray() {
        double[] xArray = {1.0};
        double[] yArray = {1.0};

        assertThrows(IllegalArgumentException.class, () -> new SplineFunction(xArray, yArray));
    }

    @Test
    public void testConstructorNotStrictlyIncreasingX() {
        double[] xArray = {1.0, 3.0, 2.0, 4.0};
        double[] yArray = {1.0, 9.0, 4.0, 16.0};

        assertThrows(IllegalArgumentException.class, () -> new SplineFunction(xArray, yArray));
    }

    @Test
    public void testConstructorEqualXValues() {
        double[] xArray = {1.0, 2.0, 2.0, 4.0};
        double[] yArray = {1.0, 4.0, 9.0, 16.0};

        assertThrows(IllegalArgumentException.class, () -> new SplineFunction(xArray, yArray));
    }

    @Test
    public void testApplyInterpolationInsideFirstInterval() {
        double[] xArray = {0.0, 1.0, 2.0, 3.0};
        double[] yArray = {0.0, 1.0, 4.0, 9.0};
        SplineFunction spline = new SplineFunction(xArray, yArray);

        assertEquals(0.5, spline.apply(0.5), 1e-10);

        assertEquals(0.0, spline.apply(0.0), 1e-10);
        assertEquals(1.0, spline.apply(1.0), 1e-10);
    }

    @Test
    public void testApplyInterpolationInsideMiddleInterval() {
        double[] xArray = {0.0, 1.0, 2.0, 3.0};
        double[] yArray = {0.0, 1.0, 4.0, 9.0};
        SplineFunction spline = new SplineFunction(xArray, yArray);

        assertEquals(2.5, spline.apply(1.5), 1e-10);

        assertEquals(1.0, spline.apply(1.0), 1e-10);
        assertEquals(4.0, spline.apply(2.0), 1e-10);
    }

    @Test
    public void testApplyInterpolationInsideLastInterval() {
        double[] xArray = {0.0, 1.0, 2.0, 3.0};
        double[] yArray = {0.0, 1.0, 4.0, 9.0};
        SplineFunction spline = new SplineFunction(xArray, yArray);

        assertEquals(6.5, spline.apply(2.5), 1e-10);

        assertEquals(4.0, spline.apply(2.0), 1e-10);
        assertEquals(9.0, spline.apply(3.0), 1e-10);
    }

    @Test
    public void testApplyExtrapolationLeft() {
        double[] xArray = {1.0, 2.0, 3.0, 4.0};
        double[] yArray = {1.0, 4.0, 9.0, 16.0};
        SplineFunction spline = new SplineFunction(xArray, yArray);

        assertEquals(-2.0, spline.apply(0.0), 1e-10);
        assertEquals(-5.0, spline.apply(-1.0), 1e-10);
    }

    @Test
    public void testApplyExtrapolationRight() {
        double[] xArray = {1.0, 2.0, 3.0, 4.0};
        double[] yArray = {1.0, 4.0, 9.0, 16.0};
        SplineFunction spline = new SplineFunction(xArray, yArray);

        assertEquals(23.0, spline.apply(5.0), 1e-10);
        assertEquals(30.0, spline.apply(6.0), 1e-10);
    }

    @Test
    public void testApplyExactNodeValues() {
        double[] xArray = {0.0, 1.0, 2.0, 3.0};
        double[] yArray = {10.0, 20.0, 30.0, 40.0};
        SplineFunction spline = new SplineFunction(xArray, yArray);

        assertEquals(10.0, spline.apply(0.0), 1e-10);
        assertEquals(20.0, spline.apply(1.0), 1e-10);
        assertEquals(30.0, spline.apply(2.0), 1e-10);
        assertEquals(40.0, spline.apply(3.0), 1e-10);
    }

    @Test
    public void testApplyLinearFunction() {
        double[] xArray = {0.0, 1.0, 2.0};
        double[] yArray = {0.0, 1.0, 2.0};
        SplineFunction spline = new SplineFunction(xArray, yArray);

        assertEquals(0.5, spline.apply(0.5), 1e-10);
        assertEquals(1.5, spline.apply(1.5), 1e-10);
        assertEquals(-0.5, spline.apply(-0.5), 1e-10);
        assertEquals(2.5, spline.apply(2.5), 1e-10);
    }

    @Test
    public void testApplyNonUniformGrid() {
        double[] xArray = {0.0, 1.0, 4.0, 9.0};
        double[] yArray = {0.0, 1.0, 2.0, 3.0};
        SplineFunction spline = new SplineFunction(xArray, yArray);

        assertEquals(0.5, spline.apply(0.5), 1e-10); // [0,1]
        assertEquals(1.3333333333333333, spline.apply(2.0), 1e-10); // [1,4]
        assertEquals(2.2, spline.apply(6.0), 1e-10); // [4,9]

        assertEquals(-1.0, spline.apply(-1.0), 1e-10);
        assertEquals(3.2, spline.apply(10.0), 1e-10);
    }

    @Test
    public void testArraysAreCloned() {
        double[] originalX = {1.0, 2.0, 3.0};
        double[] originalY = {1.0, 2.0, 3.0};

        SplineFunction spline = new SplineFunction(originalX, originalY);

        originalX[0] = 100.0;
        originalY[0] = 100.0;

        assertEquals(1.0, spline.apply(1.0), 1e-10);
        assertEquals(2.0, spline.apply(2.0), 1e-10);
    }

    @Test
    public void testMathFunctionInterface() {
        double[] xArray = {0.0, 1.0, 2.0};
        double[] yArray = {0.0, 1.0, 4.0};
        MathFunction function = new SplineFunction(xArray, yArray);
        assertInstanceOf(MathFunction.class, function);

        assertEquals(0.5, function.apply(0.5), 1e-10);
        assertEquals(2.5, function.apply(1.5), 1e-10);
    }
}
