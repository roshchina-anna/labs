package ru.ssau.tk.roshchina.labs.functions;

public class CompositeFunction implements MathFunction {
    private final MathFunction firstFunction; //первая функция
    private final MathFunction secondFunction; //вторая функция

    public CompositeFunction (MathFunction firstFunction, MathFunction secondFunction) { //конструктор
        this.firstFunction = firstFunction;
        this.secondFunction = secondFunction;
    }
    @Override
    public double apply(double x) {
        double intermediateRes = firstFunction.apply(x);
        return secondFunction.apply(x);
    }
    //геттеры
    public MathFunction getFirstFunction() {
        return firstFunction;
    }
    public MathFunction getSecondFunction() {
        return secondFunction;
    }
}
