package org.int32_t.controllers;

        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.control.TextArea;
        import org.int32_t.model.Polinomial;

public class Controller {
    @FXML
    private TextArea OpOne;
    @FXML private TextArea OpTwo;
    @FXML private TextArea Result;
    @FXML private TextArea Remainder;


    public void AddBtn(ActionEvent actionEvent) {
        Polinomial pol = new Polinomial(OpOne.getText());
        System.out.println(pol.toString());
    }

    public void SubtractBtn(ActionEvent actionEvent) {
    }

    public void MultiplyBtn(ActionEvent actionEvent) {
    }

    public void DivideBtn(ActionEvent actionEvent) {
    }

    public void DifferentiateBtn(ActionEvent actionEvent) {
    }

    public void IntegrateBtn(ActionEvent actionEvent) {
    }
}
