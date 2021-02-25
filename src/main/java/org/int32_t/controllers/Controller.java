package org.int32_t.controllers;

        import javafx.event.ActionEvent;
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


    public void AddBtn(ActionEvent actionEvent) {
        Polinomial pol1 = new Polinomial(OpOne.getText());
        Polinomial pol2 = new Polinomial(OpTwo.getText());
        Result.setText(normalizePolinomial(poliAdd(pol1,pol2)).toString());
    }

    public void SubtractBtn(ActionEvent actionEvent) {
        Polinomial pol1 = new Polinomial(OpOne.getText());
        Polinomial pol2 = new Polinomial(OpTwo.getText());
        Result.setText(normalizePolinomial(poliSubtract(pol1,pol2)).toString());

    }

    public void MultiplyBtn(ActionEvent actionEvent) {
    }

    public void DivideBtn(ActionEvent actionEvent) {
    }

    public void DifferentiateBtn(ActionEvent actionEvent) {
    }

    public void IntegrateBtn(ActionEvent actionEvent) {
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

    private Polinomial normalizePolinomial(Polinomial pol){
        for(Monomial m : pol.polinomial){
            for (Monomial n : pol.polinomial) {
                if (pol.polinomial.indexOf(n) > pol.polinomial.indexOf(m)) {
                    if (m.getPower() < n.getPower()) {
                        Collections.swap(pol.polinomial, pol.polinomial.indexOf(n), pol.polinomial.indexOf(m));
                        m = n;
                    }
                }
            }
        }
        pol.polinomial.removeIf(m -> m.getCoeficiant() == 0); //Delete null elements
        return pol;
    }
}
