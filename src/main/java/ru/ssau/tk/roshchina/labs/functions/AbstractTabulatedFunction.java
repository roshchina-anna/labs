package ru.ssau.tk.roshchina.labs.functions;

public abstract class AbstractTabulatedFunction implements TabulatedFunction {

    protected abstract int indexMaxX(double x);
    protected abstract double extrapolateLeft(double x);
    protected abstract double extrapolateRight(double x);
    protected abstract double interpolate(double x, int indexLeft);
    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY) {
        return leftY + (rightY - leftY) * (x - leftX) / (rightX - leftX);
    }
    @Override
    public double apply(double x) {
        if (x < leftBound()) {
            return extrapolateLeft(x);
        }
        if (x > rightBound()) {
            return extrapolateRight(x);
        }
        int index = indexMaxX(x);
        if (index != -1) {
            return getY(index);
        } else {
            int indexLeft = indexMaxX(x);
            return interpolate(x, indexLeft);
        }
    }
}