package org.int32_t.controllers;

        import javafx.fxml.FXML;
        import javafx.scene.control.TextArea;
        import org.int32_t.model.Monomial;
        import org.int32_t.model.Polinomial;

        import java.util.Collections;
        import java.util.regex.Matcher;
        import java.util.regex.Pattern;

public class Controller {
    @FXML private TextArea OpOne;
    @FXML private TextArea OpTwo;
    @FXML private TextArea Result;
    @FXML private TextArea Remainder;

    //This is called when the Add button is pressed
    public void AddBtn() {
        clearFields();
        Polinomial pol1 = new Polinomial(OpOne.getText());
        Polinomial pol2 = new Polinomial(OpTwo.getText());
        if(isGoodInput(OpOne.getText()) && isGoodInput(OpTwo.getText())) Result.setText(normalizePolinomial(poliAdd(pol1,pol2)).toString());
    }
    //This is called when the Subtract button is pressed
    public void SubtractBtn() {
        clearFields();
        Polinomial pol1 = new Polinomial(OpOne.getText());
        Polinomial pol2 = new Polinomial(OpTwo.getText());
        if(isGoodInput(OpOne.getText()) && isGoodInput(OpTwo.getText())) Result.setText(normalizePolinomial(poliSubtract(pol1,pol2)).toString());
    }
    //This is called when the Multiply button is pressed
    public void MultiplyBtn() {
        clearFields();
        Polinomial pol1 = new Polinomial(OpOne.getText());
        Polinomial pol2 = new Polinomial(OpTwo.getText());
        if(isGoodInput(OpOne.getText()) && isGoodInput(OpTwo.getText())) Result.setText(normalizePolinomial(poliMultiply(pol1,pol2)).toString());
    }
    //This is called when the Divide button is pressed
    public void DivideBtn() {
        clearFields();
        if(isGoodInput(OpOne.getText()) && isGoodInput(OpTwo.getText())) { //Check if the input matches the format wanted
            Polinomial pol1 = new Polinomial(OpOne.getText());
            Polinomial pol2 = new Polinomial(OpTwo.getText());
            if(pol2.isNUll()){ //Check if we are trying to divide by 0
                Result.setText("Bad input, division by 0!");

            } else { //Not dividing my 0, so we can calculate the result
                Polinomial[] divRez;
                divRez = poliDivision(normalizePolinomial(pol1),normalizePolinomial(pol2)); // Get the result
                Result.setText(normalizePolinomial(divRez[0]).toString());
                Remainder.setText(normalizePolinomial(divRez[1]).toString());
            }
        }else{
            Result.setText("Bad input");
        }
    }

    //This is called when the Differentiate button is pressed
    public void DifferentiateBtn() {
        clearFields();
        Polinomial pol1 = new Polinomial(OpOne.getText());
        if(isGoodInput(OpOne.getText())) Result.setText(normalizePolinomial(poliDifferentiate(pol1)).toString());
    }

    //This is called when the Integrate button is pressed
    public void IntegrateBtn() {
        clearFields();
        Polinomial pol1 = new Polinomial(OpOne.getText());
        if(isGoodInput(OpOne.getText())) Result.setText(normalizePolinomial(poliIntegrate(pol1)).toString());
    }
    //The algorithm used for the differentiation
    public Polinomial poliDifferentiate(Polinomial pol1){
        for(Monomial m : pol1.polinomial){
            if(m.getPower() > 0){//x^*
                Monomial buff = new Monomial(m.getPower() - 1,m.getCoefficient()*m.getPower());
                pol1.polinomial.set(pol1.polinomial.indexOf(m),buff);
            }else{//constant, so we delete the element
                pol1.polinomial.remove(m);
            }
        }
        return pol1;
    }

    //The algorithm used for the addition
    public Polinomial poliAdd(Polinomial pol1, Polinomial pol2){
        for(Monomial m : pol1.polinomial){
            boolean foundEquivalent = false;//Used to find monomials with matching powers
            for(Monomial n : pol2.polinomial){
                if(m.getPower() == n.getPower()){
                   foundEquivalent = true;
                   n.setCoefficient(n.getCoefficient() + m.getCoefficient());
                }
            }
            if(!foundEquivalent){
                pol2.polinomial.add(m);
            }
        }
        return  pol2;
    }
    //The algorithm used for the subtraction
    public Polinomial poliSubtract(Polinomial pol1, Polinomial pol2){
        for(Monomial m : pol2.polinomial){
            boolean foundEquivalent = false; //Used to find monomials with matching powers
            for(Monomial n : pol1.polinomial){
                if(m.getPower() == n.getPower()){
                    foundEquivalent = true;
                    n.setCoefficient(n.getCoefficient() - m.getCoefficient());
                }
            }
            if(!foundEquivalent){
                pol1.polinomial.add(new Monomial(m.getPower(),-m.getCoefficient()));
            }
        }
                return  pol1;
    }
    //The algorithm used for the multiplication
    public Polinomial poliMultiply(Polinomial pol1, Polinomial pol2){
        Polinomial pol = new Polinomial();
        for(Monomial m : pol1.polinomial){
            for(Monomial n : pol2.polinomial){
                Monomial monBuff = new Monomial();
                monBuff.setCoefficient(n.getCoefficient() * m.getCoefficient());
                monBuff.setPower(n.getPower() + m.getPower());
                pol.polinomial.add(monBuff);
            }
        }
        return pol;
    }

    //The algorithm used for the integration
    public Polinomial poliIntegrate(Polinomial pol){
        for(Monomial m : pol.polinomial){
            m.setPower(m.getPower() + 1);
            m.setCoefficient(m.getCoefficient() * 1/m.getPower());
        }
        return pol;
    }

    //The algorithm used for the division (long division algo. used)
    public Polinomial[] poliDivision(Polinomial dividend, Polinomial divisor){
        Polinomial result = new Polinomial();
        while(dividend.getDegree() >= divisor.getDegree() && dividend.polinomial.size() > 0){
            dividend = normalizePolinomial(dividend);
            Monomial mon = new Monomial(dividend.polinomial.get(0).getPower() - divisor.polinomial.get(0).getPower(),
                    dividend.polinomial.get(0).getCoefficient() / divisor.polinomial.get(0).getCoefficient());
                result.polinomial.add(mon);
                Polinomial buff = new Polinomial();
                buff.polinomial.add(mon);
            poliSubtract(dividend, poliMultiply(buff, divisor));
            dividend = normalizePolinomial(dividend);
        }
        return new Polinomial[] {result, dividend};
    }
    //This methode is used to eliminate null elements (0x^2) and to sort the monomials in non-decreasing order in function of their powers
    public Polinomial normalizePolinomial(Polinomial pol){
        for(Monomial m : pol.polinomial){
            for (Monomial n : pol.polinomial) {
                if (pol.polinomial.indexOf(n) > pol.polinomial.indexOf(m)) {
                    if (m.getPower() < n.getPower()) {
                        Collections.swap(pol.polinomial, pol.polinomial.indexOf(n), pol.polinomial.indexOf(m));
                        m = n;
                    }
                }
                if(m.getPower() == n.getPower() && m != n){
                    m.setCoefficient(m.getCoefficient() + n.getCoefficient());
                    n.setPower(0); n.setCoefficient(0);
                }
            }
        }
        pol.polinomial.removeIf(m -> m.getCoefficient() == 0); //Delete null elements
        return pol;
    }

    //Helper method toi clear fields in the UI
    private void clearFields(){
        Result.setText("");
        Remainder.setText("");
    }
    // Checks weather the input obeys the rules for a valid polynomial
    private boolean isGoodInput(String input){
        String monMatcherFull = "(?<Mon>((?<sign>\\+|-)(?<coef>\\d+)?(?<x>x)(?<pow>\\^\\d+))|((?<sign2>\\+|-)(?<coef2>\\d+)?(?<x2>x))|(?<sign3>\\+|-)(?<coef3>\\d+))+";
        Pattern patternTester = Pattern.compile(monMatcherFull);
        Matcher matcherTester = patternTester.matcher(input);
        if(!matcherTester.matches()){
            Result.setText("Bad input!");
            return false;
        }else {
            return true;
        }
    }
}
