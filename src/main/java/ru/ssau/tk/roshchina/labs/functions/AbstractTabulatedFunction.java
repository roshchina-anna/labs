package ru.ssau.tk.roshchina.labs.functions;

public abstract class AbstractTabulatedFunction implements TabulatedFunction {
    protected abstract int  floorIndexOfX(double x); //индекс левой границы интервала для х
    protected abstract double extrapolateLeft(double x);
    protected abstract double extrapolateRight(double x);
    protected abstract double interpolate(double x, int indexLeft);
    //интерполяция между двумя точками
    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY) {
        if (Math.abs (leftX - rightX) < 1e-12){
            throw new IllegalArgumentException ("The left and right boundaries of the interval cannot be the same");
        }
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
        int index =  floorIndexOfX(x);
        if (index != -1) {
            return getY(index);
        } else {
            int indexLeft =  floorIndexOfX(x); //поиск левой границы
            return interpolate(x, indexLeft);
        }
    }
}