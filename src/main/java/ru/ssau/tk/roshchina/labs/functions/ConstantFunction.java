package ru.ssau.tk.roshchina.labs.functions;

public class ConstantFunction implements MathFunction {
    private final double constant;
    public ConstantFunction(double constant) {
        this.constant = constant;
    }
    @Override
    public double apply(double x) {
        return constant; //возвращает константу, игнорирует x
    }
    //геттер для получения значения константы
    public double getConstant() {
        return constant; //возвращает сохраненное значение
    }
}