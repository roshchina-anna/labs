package ru.ssau.tk.roshchina.labs.functions;

public class IdentityFunction implements MathFunction {
    @Override
    public double apply(double x) {
        return x;
    }
}
