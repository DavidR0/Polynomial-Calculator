package org.int32_t.model;

public class Monomial {
    private int power;
    private Number coefficient;

    public Monomial(){

    }

    public Monomial(int power, float coefficient) {
        this.power = power;
        this.coefficient = coefficient;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public float getCoefficient() {
        return coefficient.floatValue();
    }

    public void setCoefficient(float coefficient) {
        this.coefficient = coefficient;
    }

    @Override
    public String toString() {
        if (coefficient.floatValue() - coefficient.intValue() != 0) {
            if(coefficient.floatValue() > 0){
                return "+" + String.format("%.2f", coefficient.floatValue()) + "x^" + power;
            }
            return String.format("%.2f", coefficient.floatValue()) + "x^" + power;
        }else{
            if(coefficient.floatValue() > 0) {
                return ("+" + coefficient.intValue() + "x^" + power);
            }
            return (coefficient.intValue() + "x^" + power);
        }
    }

    public void printMonomial(){
        System.out.println(coefficient + "x^" + power + "\n");
    }
}
