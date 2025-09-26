package ru.ssau.tk.roshchina.labs.functions;

public class SplineFunction implements MathFunction {
    private final double[] xArray;
    private final double[] yArray;
    public SplineFunction(double[] xArray, double[] yArray) {
        if (xArray.length != yArray.length || xArray.length < 2) {
            throw new IllegalArgumentException("Invalid arrays");
        }
        for (int i = 1; i < xArray.length; i++) {
            if (xArray[i] <= xArray[i - 1]) {
                throw new IllegalArgumentException("xArray must be strictly increasing");
            }
        }
        this.xArray = xArray.clone();
        this.yArray = yArray.clone();
    }
    @Override
    public double apply(double x) {
        for (int i = 0; i < xArray.length - 1; i++) {
            if (x <= xArray[i + 1]) {
                if (x < xArray[i] && i == 0) {
                    return interpolate(xArray[0], xArray[1], yArray[0], yArray[1], x);
                }
                return interpolate(xArray[i], xArray[i + 1], yArray[i], yArray[i + 1], x);
            }
        }
        return interpolate(
                xArray[xArray.length - 2],
                xArray[xArray.length - 1],
                yArray[yArray.length - 2],
                yArray[yArray.length - 1],
                x
        );
    }
    private double interpolate(double x1, double x2, double y1, double y2, double x) {
        return y1 + (y2 - y1) * (x - x1) / (x2 - x1);
    }
}
