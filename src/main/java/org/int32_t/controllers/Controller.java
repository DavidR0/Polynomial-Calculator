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

    public void AddBtn() {
        clearFields();
        Polinomial pol1 = new Polinomial(OpOne.getText());
        Polinomial pol2 = new Polinomial(OpTwo.getText());
        if(isGoodInput(OpOne.getText()) && isGoodInput(OpTwo.getText())) Result.setText(normalizePolinomial(poliAdd(pol1,pol2)).toString());
    }

    public void SubtractBtn() {
        clearFields();
        Polinomial pol1 = new Polinomial(OpOne.getText());
        Polinomial pol2 = new Polinomial(OpTwo.getText());
        if(isGoodInput(OpOne.getText()) && isGoodInput(OpTwo.getText())) Result.setText(normalizePolinomial(poliSubtract(pol1,pol2)).toString());
    }

    public void MultiplyBtn() {
        clearFields();
        Polinomial pol1 = new Polinomial(OpOne.getText());
        Polinomial pol2 = new Polinomial(OpTwo.getText());
        if(isGoodInput(OpOne.getText()) && isGoodInput(OpTwo.getText())) Result.setText(normalizePolinomial(poliMultiply(pol1,pol2)).toString());
    }

    public void DivideBtn() {
        clearFields();
        if(isGoodInput(OpOne.getText()) && isGoodInput(OpTwo.getText())) {
            Polinomial pol1 = new Polinomial(OpOne.getText());
            Polinomial pol2 = new Polinomial(OpTwo.getText());
            Polinomial[] divRez;
            divRez = poliDivision(pol1,pol2);
            Result.setText(normalizePolinomial(divRez[0]).toString());
            Remainder.setText(normalizePolinomial(divRez[1]).toString());
        }
    }

    public void DifferentiateBtn() {
        clearFields();
        Polinomial pol1 = new Polinomial(OpOne.getText());
        if(isGoodInput(OpOne.getText())) Result.setText(normalizePolinomial(poliDifferentiate(pol1)).toString());
    }

    public void IntegrateBtn() {
        clearFields();
        Polinomial pol1 = new Polinomial(OpOne.getText());
        if(isGoodInput(OpOne.getText())) Result.setText(normalizePolinomial(poliIntegrate(pol1)).toString());
    }

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

    public Polinomial poliAdd(Polinomial pol1, Polinomial pol2){
        for(Monomial m : pol1.polinomial){
            boolean foundEquivalent = false;
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

    public Polinomial poliSubtract(Polinomial pol1, Polinomial pol2){
        for(Monomial m : pol2.polinomial){
            boolean foundEquivalent = false;
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

    public Polinomial poliIntegrate(Polinomial pol){
        for(Monomial m : pol.polinomial){
            m.setPower(m.getPower() + 1);
            m.setCoefficient(m.getCoefficient() * 1/m.getPower());
        }
        return pol;
    }

    public Polinomial[] poliDivision(Polinomial dividend, Polinomial divisor){
        Polinomial result = new Polinomial();
        while(dividend.getDegree() >= divisor.getDegree()){
            normalizePolinomial(dividend);
            Monomial mon = new Monomial(dividend.polinomial.get(0).getPower() - divisor.polinomial.get(0).getPower(),
                    dividend.polinomial.get(0).getCoefficient() / divisor.polinomial.get(0).getCoefficient());
                result.polinomial.add(mon);
                Polinomial buff = new Polinomial();
                buff.polinomial.add(mon);
            poliSubtract(dividend, poliMultiply(buff, divisor));
        }
        return new Polinomial[] {result, dividend};
    }

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


    private void clearFields(){
        Result.setText("");
        Remainder.setText("");
    }

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
