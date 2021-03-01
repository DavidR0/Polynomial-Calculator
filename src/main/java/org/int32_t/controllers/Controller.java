package org.int32_t.controllers;

        import javafx.fxml.FXML;
        import javafx.scene.control.TextArea;
        import org.int32_t.model.Monomial;
        import org.int32_t.model.Polinomial;
        import java.util.Collections;

public class Controller {
    @FXML private TextArea OpOne;
    @FXML private TextArea OpTwo;
    @FXML private TextArea Result;
    @FXML private TextArea Remainder;


    public void AddBtn() {
        Polinomial pol1 = new Polinomial(OpOne.getText());
        Polinomial pol2 = new Polinomial(OpTwo.getText());
        Result.setText(normalizePolinomial(poliAdd(pol1,pol2)).toString());
    }

    public void SubtractBtn() {
        Polinomial pol1 = new Polinomial(OpOne.getText());
        Polinomial pol2 = new Polinomial(OpTwo.getText());
        Result.setText(normalizePolinomial(poliSubtract(pol1,pol2)).toString());
    }

    public void MultiplyBtn() {
        Polinomial pol1 = new Polinomial(OpOne.getText());
        Polinomial pol2 = new Polinomial(OpTwo.getText());
        Result.setText(normalizePolinomial(poliMultiply(pol1,pol2)).toString());
    }

    public void DivideBtn() {
        Polinomial pol1 = new Polinomial(OpOne.getText());
        Polinomial pol2 = new Polinomial(OpTwo.getText());
        Polinomial[] divRez;
        divRez = poliDivision(pol1,pol2);
        Result.setText(normalizePolinomial(divRez[0]).toString());
        Remainder.setText(normalizePolinomial(divRez[1]).toString());
    }

    public void DifferentiateBtn() {
        Polinomial pol1 = new Polinomial(OpOne.getText());
        Result.setText(normalizePolinomial(poliDifferentiate(pol1)).toString());
    }

    public void IntegrateBtn() {
        Polinomial pol1 = new Polinomial(OpOne.getText());
        Result.setText(normalizePolinomial(poliIntegrate(pol1)).toString());
    }

    private Polinomial poliDifferentiate(Polinomial pol1){
        for(Monomial m : pol1.polinomial){
            if(m.getPower() > 0){//x^*
                Monomial buff = new Monomial(m.getPower() - 1,m.getCoeficiant()*m.getPower());
                pol1.polinomial.set(pol1.polinomial.indexOf(m),buff);
            }else{//constant, so we delete the element
                pol1.polinomial.remove(m);
            }
        }
        return pol1;
    }

    private Polinomial poliAdd(Polinomial pol1, Polinomial pol2){
        for(Monomial m : pol1.polinomial){
            boolean foundEquivalent = false;
            for(Monomial n : pol2.polinomial){
                if(m.getPower() == n.getPower()){
                   foundEquivalent = true;
                   n.setCoeficiant(n.getCoeficiant() + m.getCoeficiant());
                }
            }
            if(!foundEquivalent){
                pol2.polinomial.add(m);
            }
        }
        return  pol2;
    }

    private Polinomial poliSubtract(Polinomial pol1, Polinomial pol2){
        for(Monomial m : pol2.polinomial){
            boolean foundEquivalent = false;
            for(Monomial n : pol1.polinomial){
                if(m.getPower() == n.getPower()){
                    foundEquivalent = true;
                    n.setCoeficiant(n.getCoeficiant() - m.getCoeficiant());
                }
            }
            if(!foundEquivalent){
                pol1.polinomial.add(new Monomial(m.getPower(),-m.getCoeficiant()));
            }
        }
                return  pol1;
    }

    private Polinomial poliMultiply(Polinomial pol1, Polinomial pol2){
        Polinomial pol = new Polinomial();
        for(Monomial m : pol1.polinomial){
            for(Monomial n : pol2.polinomial){
                Monomial monBuff = new Monomial();
                monBuff.setCoeficiant(n.getCoeficiant() * m.getCoeficiant());
                monBuff.setPower(n.getPower() + m.getPower());
                pol.polinomial.add(monBuff);
            }
        }
        return pol;
    }

    private Polinomial poliIntegrate(Polinomial pol){
        for(Monomial m : pol.polinomial){
            m.setPower(m.getPower() + 1);
            m.setCoeficiant(m.getCoeficiant() * 1/m.getPower());
        }
        return pol;
    }
//    2x^4+3x+4x^5  2x^4+2x+2
//    2x^2+3x+4x^4  x^4+3x+4

    private Polinomial normalizePolinomial(Polinomial pol){
        for(Monomial m : pol.polinomial){
            for (Monomial n : pol.polinomial) {
                if (pol.polinomial.indexOf(n) > pol.polinomial.indexOf(m)) {
                    if (m.getPower() < n.getPower()) {
                        Collections.swap(pol.polinomial, pol.polinomial.indexOf(n), pol.polinomial.indexOf(m));
                        m = n;
                    }
                }
                if(m.getPower() == n.getPower() && m != n){
                    m.setCoeficiant(m.getCoeficiant() + n.getCoeficiant());
                    n.setPower(0); n.setCoeficiant(0);
                }
            }
        }
        pol.polinomial.removeIf(m -> m.getCoeficiant() == 0); //Delete null elements
        return pol;
    }
}
