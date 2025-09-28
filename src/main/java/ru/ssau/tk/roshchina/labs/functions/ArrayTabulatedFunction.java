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
        return xArray[index];
    }
    @Override
    public double getY(int index) {
        return yArray[index];
    }
    @Override
    public void setY(int index, double value) {
        yArray[index] = value;
    }
    @Override
    public int indexOfX(double x) {
        for (int i = 0; i < count; i++) {
            if (Math.abs(getX(i) - x) < 1e-12) {
                return i;
            }
        }
        return -1;
    }
    @Override
    public int indexOfY(double y) {
        for (int i = 0; i < count; i++) {
            if (Math.abs(getY(i) - y) < 1e-12) {
                return i;
            }
        }
        return -1;
    }
    @Override
    public double leftBound() {
        return getX(0);
    }
    @Override
    public double rightBound() {
        return getX(count - 1);
    }
    @Override
    protected int floorIndexOfX(double x) {
        if (x < getX(0)) return 0;
        if (x > getX(count - 1))return count;
        int left = 0;
        int right = count - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (getX(mid) == x) {
                return mid;
            } else if (getX(mid) < x){
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return right;
    }
    @Override
    protected double extrapolateLeft(double x) {
        if (count == 1) {
            return getY(0);
        }
        return interpolate(x, getX(0), getX(1), getY(0), getY(1));
    }
    @Override
    protected double extrapolateRight(double x) {
        if (count == 1) {
            return getY(0);
        }
        return interpolate(x, getX(count - 2), getX(count - 1), getY(count - 2), getY(count - 1));
    }
    @Override
    protected double interpolate(double x, int floorIndex) {
        if (count == 1) {
            return getY(0);
        }
        return interpolate(x, getX(floorIndex), getX(floorIndex+1), getY(floorIndex), getY(floorIndex+1));
    }

}
