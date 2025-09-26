package ru.ssau.tk.roshchina.labs.functions;

public class NewtonMethod implements MathFunction{
    private final MathFunction function;
    private final MathFunction derivative;
    private final double accuracy;
    private final int maxIterations;

    public NewtonMethod(MathFunction function, MathFunction derivative,
                        double accuracy, int maxIterations) {
        this.function = function;
        this.derivative = derivative;
        this.accuracy = accuracy;
        this.maxIterations = maxIterations;
    }

    public NewtonMethod(MathFunction function, MathFunction derivative) {
        this(function, derivative, 0.000001, 100);
    }

    @Override
    public double apply(double x) {
        return findRoot(x);
    }

    public double findRoot(double initialApproximation) {
        double x = initialApproximation;
        for (int i = 0; i < maxIterations; i++) {
            double fx = function.apply(x);
            double fpx = derivative.apply(x);

            if (Math.abs(fpx) < 0.000001) {
                throw new ArithmeticException("The derivative is close to zero. Newton's method does not converge.");
            }
            double xNew = x - fx / fpx;

            if (Math.abs(xNew - x) < accuracy) {
                return xNew;
            }
            x = xNew;
        }
        throw new RuntimeException("Newton's method did not converge after " + maxIterations + " iterations");
    }
}
