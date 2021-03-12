import org.int32_t.controllers.Controller;
import org.int32_t.model.Polinomial;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {

    private class InputEle{
        public Polinomial firstPol;
        public Polinomial secondPol;
        public Polinomial result;

        public InputEle(Polinomial polinomial1, Polinomial polinomial2, Polinomial result) {
            firstPol = polinomial1;
            secondPol = polinomial2;
            this.result = result;
        }
    }

    @Test
    void poliDifferentiate() {
    }

    @Test
    void poliAdd() {
        Controller ctr = new Controller();

        LinkedList<InputEle> data = new LinkedList<InputEle>();
        data.add(new InputEle(new Polinomial("+2x^2+2x+1"),new Polinomial("+1"),new Polinomial("+2x^2+2x+2")));
        data.add(new InputEle(new Polinomial("+2x^2+2x+1"),new Polinomial("+2x"),new Polinomial("+2x^2+4x+1")));
        data.add(new InputEle(new Polinomial("+2x^2+2x+1"),new Polinomial("+2x^2+3x"),new Polinomial("+4x^2+5x+1")));

        for(InputEle obj : data){
            assertEquals(obj.result.toString(), ctr.normalizePolinomial(ctr.poliAdd(obj.firstPol, obj.secondPol)).toString());
        }
    }

    @Test
    void poliSubtract() {
        Controller ctr = new Controller();

        LinkedList<InputEle> data = new LinkedList<InputEle>();
        data.add(new InputEle(new Polinomial("+2x^2+2x+1"),new Polinomial("+1"),new Polinomial("+2x^2+2x")));
        data.add(new InputEle(new Polinomial("+2x^2+2x+1"),new Polinomial("+2x"),new Polinomial("+2x^2+1")));
        data.add(new InputEle(new Polinomial("+2x^2+2x+1"),new Polinomial("+2x^2+3x"),new Polinomial("-x+1")));

        for(InputEle obj : data){
            assertEquals(obj.result.toString(), ctr.normalizePolinomial(ctr.poliSubtract(obj.firstPol, obj.secondPol)).toString());
        }
    }

    @Test
    void poliMultiply() {
        Controller ctr = new Controller();

        LinkedList<InputEle> data = new LinkedList<InputEle>();
        data.add(new InputEle(new Polinomial("+2x^2+2x+1"),new Polinomial("+1"),new Polinomial("+2x^2+2x+1")));
        data.add(new InputEle(new Polinomial("+2x^2+2x+1"),new Polinomial("+2x"),new Polinomial("+4x^3+4x^2+2x")));
        data.add(new InputEle(new Polinomial("+2x^2+2x+1"),new Polinomial("+2x^2+3x"),new Polinomial("+4x^4+10x^3+8x^2+3x^1")));

        for(InputEle obj : data){
            assertEquals(obj.result.toString(), ctr.normalizePolinomial(ctr.poliMultiply(obj.firstPol, obj.secondPol)).toString());
        }
    }

    @Test
    void poliIntegrate() {
    }

    @Test
    void poliDivision() {
    }
}