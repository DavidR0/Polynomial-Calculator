package org.int32_t.model;

public class Monomial {
    private int power;
    private float coeficiant;

    public Monomial(){

    }

    public Monomial(int power, float coeficiant) {
        this.power = power;
        this.coeficiant = coeficiant;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public float getCoeficiant() {
        return coeficiant;
    }

    public void setCoeficiant(float coeficiant) {
        this.coeficiant = coeficiant;
    }

    @Override
    public String toString() {
        if (coeficiant >= 0) {
            return "+" + coeficiant + "x^" + power;
        } else{
            return coeficiant + "x^" + power;
        }
    }

    public void printMonomial(){
        System.out.println(coeficiant + "x^" + power + "\n");
    }
}
