package ru.ssau.tk.roshchina.labs.functions;

import java.util.Arrays;

public class ArrayTabulatedFunction extends AbstractTabulatedFunction {
    private final double[] xArray;
    private final double[] yArray;
    private final int count;

    public ArrayTabulatedFunction(double[] xArray, double[] yArray) {
        this.count = xArray.length;
        this.xArray = Arrays.copyOf(xArray, count);
        this.yArray = Arrays.copyOf(yArray, count);
    }
    public ArrayTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        if (count < 2) {
            throw new IllegalArgumentException("Count must be at least 2");
        }
        this.count = count;
        this.xArray = new double[count];
        this.yArray = new double[count];
        if (xFrom > xTo) {
            double temp = xFrom;
            xFrom = xTo;
            xTo = temp;
        }
        if (xFrom == xTo) {
            Arrays.fill(xArray, xFrom);
            double yArrays = source.apply(xFrom);
            Arrays.fill(yArray, yArrays);
        } else {
            double step = (xTo - xFrom) / (count - 1);
            for (int i = 0; i < count; i++) {
                xArray[i] = xFrom + i * step;
                yArray[i] = source.apply(xArray[i]);
            }
        }
    }
    @Override
    public int getCount() {
        return count;
    }
    @Override
    public double getX(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        return xArray[index];
    }
    @Override
    public double getY(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        return yArray[index];
    }
    @Override
    public void setY(int index, double value) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        yArray[index] = value;
    }
    @Override
    public int indexOfX(double x) {
        for (int i = 0; i < count; i++) {
            if (Math.abs(xArray[i] - x) < 1e-12) {
                return i;
            }
        }
        return -1;
    }
    @Override
    public int indexOfY(double y) {
        for (int i = 0; i < count; i++) {
            if (Math.abs(yArray[i] - y) < 1e-12) {
                return i;
            }
        }
        return -1;
    }
    @Override
    public double leftBound() {
        return xArray[0];
    }
    @Override
    public double rightBound() {
        return xArray[count - 1];
    }
    // Реализация абстрактных методов
    @Override
    protected int indexMaxX(double x) {
        if (x < xArray[0]) {
            return 0;
        }
        if (x > xArray[count - 1]) {
            return count;
        }
        for (int i = 0; i < count - 1; i++) {
            if (x >= xArray[i] && x < xArray[i + 1]) {
                return i;
            }
        }
        return count - 1;
    }
    @Override
    protected double extrapolateLeft(double x) {
        if (count == 1) {
            return yArray[0];
        }
        return interpolate(x, xArray[0], xArray[1], yArray[0], yArray[1]);
    }
    @Override
    protected double extrapolateRight(double x) {
        if (count == 1) {
            return yArray[0];
        }
        return interpolate(x, xArray[count - 2], xArray[count - 1], yArray[count - 2], yArray[count - 1]);
    }
    @Override
    protected double interpolate(double x, int floorIndex) {
        if (count == 1) {
            return yArray[0];
        }
        return interpolate(x, xArray[floorIndex], xArray[floorIndex + 1],
                yArray[floorIndex], yArray[floorIndex + 1]);
    }
}
