package ru.ssau.tk.roshchina.labs.functions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NewtonMethodTest {
    @Test
    void testLinearEquation() {
        MathFunction linear = x -> 2 * x + 3;
        MathFunction derivative = x -> 2.0;

        NewtonMethod newton = new NewtonMethod(linear, derivative);

        double root = newton.findRoot(0.0);
        assertEquals(-1.5, root, 0.00000001);
    }

    @Test
    void testQuadraticEquation() {
        // f(x) = x² - 4 = 0 → x = 2 или x = -2
        MathFunction quadratic = x -> x * x - 4;
        MathFunction derivative = x -> 2 * x;

        NewtonMethod newton = new NewtonMethod(quadratic, derivative);

        double positiveRoot = newton.findRoot(5.0);
        assertEquals(2.0, positiveRoot, 0.00000001);

        double negativeRoot = newton.findRoot(-5.0);
        assertEquals(-2.0, negativeRoot, 0.00000001);
    }

    @Test
    void testSquareRoot() {
        MathFunction square = x -> x * x - 25;
        MathFunction derivative = x -> 2 * x;

        NewtonMethod newton = new NewtonMethod(square, derivative);

        double sqrt25 = newton.findRoot(10.0);
        assertEquals(5.0, sqrt25, 0.00000001);
    }

    @Test
    void testCubeRoot() {
        MathFunction cube = x -> x * x * x - 27;
        MathFunction derivative = x -> 3 * x * x;

        NewtonMethod newton = new NewtonMethod(cube, derivative);

        double cubeRoot = newton.findRoot(5.0);
        assertEquals(3.0, cubeRoot, 0.00000001);
    }

    @Test
    void testTrigonometricEquation() {
        // Решаем sin(x) = 0 → x = 0, π, 2π, ...
        MathFunction sinFunction = Math::sin;
        MathFunction derivative = Math::cos;

        NewtonMethod newton = new NewtonMethod(sinFunction, derivative);

        double root = newton.findRoot(3.0); // Начальное приближение near π
        assertEquals(Math.PI, root, 0.00000001);
    }

    @Test
    void testExponentialEquation() {
        MathFunction expFunction = x -> Math.exp(x) - 2;
        MathFunction derivative = Math::exp;

        NewtonMethod newton = new NewtonMethod(expFunction, derivative);

        double root = newton.findRoot(1.0);
        assertEquals(Math.log(2), root, 0.00000001);
    }

    @Test
    void testZeroDerivativeThrowsException() {
        MathFunction constant = x -> 5.0;
        MathFunction zeroDerivative = x -> 0.0;

        NewtonMethod newton = new NewtonMethod(constant, zeroDerivative);

        assertThrows(ArithmeticException.class, () -> newton.findRoot(1.0));
    }

    @Test
    void testApplyMethod() {
        MathFunction quadratic = x -> x * x - 4;
        MathFunction derivative = x -> 2 * x;

        NewtonMethod newton = new NewtonMethod(quadratic, derivative);

        double root1 = newton.findRoot(5.0);
        double root2 = newton.apply(5.0);

        assertEquals(root1, root2, 0.00000001);
    }
}