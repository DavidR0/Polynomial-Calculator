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
        public Polinomial remainder;

        public InputEle(Polinomial polinomial1, Polinomial polinomial2, Polinomial result) {
            firstPol = polinomial1;
            secondPol = polinomial2;
            this.result = result;
        }
        public InputEle(Polinomial polinomial1, Polinomial result) {
            firstPol = polinomial1;
            this.result = result;
        }

        public InputEle(Polinomial polinomial1, Polinomial polinomial2, Polinomial result,Polinomial remainder) {
            firstPol = polinomial1;
            secondPol = polinomial2;
            this.result = result;
            this.remainder = remainder;
        }
    }


    @Test
    void poliDifferentiate() {
        Controller ctr = new Controller();

        LinkedList<InputEle> data = new LinkedList<>();
        data.add(new InputEle(new Polinomial("+2x^2+2x+1"),new Polinomial("+4x^1+2x^0")));
        data.add(new InputEle(new Polinomial("+1"),new Polinomial("0")));
        data.add(new InputEle(new Polinomial("+2x+1"),new Polinomial("+2x^0")));

        for(InputEle obj : data){
            assertEquals(obj.result.toString(), ctr.normalizePolinomial(ctr.poliDifferentiate(obj.firstPol)).toString());
        }
    }

    @Test
    void poliAdd() {
        Controller ctr = new Controller();

        LinkedList<InputEle> data = new LinkedList<>();
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

        LinkedList<InputEle> data = new LinkedList<>();
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

        LinkedList<InputEle> data = new LinkedList<>();
        data.add(new InputEle(new Polinomial("+2x^2+2x+1"),new Polinomial("+1"),new Polinomial("+2x^2+2x+1")));
        data.add(new InputEle(new Polinomial("+2x^2+2x+1"),new Polinomial("+2x"),new Polinomial("+4x^3+4x^2+2x")));
        data.add(new InputEle(new Polinomial("+2x^2+2x+1"),new Polinomial("+2x^2+3x"),new Polinomial("+4x^4+10x^3+8x^2+3x^1")));

        for(InputEle obj : data){
            assertEquals(obj.result.toString(), ctr.normalizePolinomial(ctr.poliMultiply(obj.firstPol, obj.secondPol)).toString());
        }
    }

    @Test
    void poliIntegrate() {
        Controller ctr = new Controller();

        LinkedList<InputEle> data = new LinkedList<>();
        data.add(new InputEle(new Polinomial("+2x+1"),new Polinomial("+1x^2+1x^1")));
        data.add(new InputEle(new Polinomial("+1"),new Polinomial("+1x^1")));

        for(InputEle obj : data){
            assertEquals(obj.result.toString(), ctr.normalizePolinomial(ctr.poliIntegrate(obj.firstPol)).toString());
        }

        //This test is separate, because we cannot construct a polynomial with floating numbers!
        assertEquals("+0.67x^3", ctr.normalizePolinomial(ctr.poliIntegrate(new Polinomial("+2x^2"))).toString());
        assertEquals("+0.50x^8+0.67x^3", ctr.normalizePolinomial(ctr.poliIntegrate(new Polinomial("+2x^2+4x^7"))).toString());


    }

    @Test
    void poliDivision() {
        Controller ctr = new Controller();

        LinkedList<InputEle> data = new LinkedList<>();
        data.add(new InputEle(new Polinomial("+2x^2+2x+1"),new Polinomial("+1x"),new Polinomial("+2x^1+2x^0"),new Polinomial("+1x^0")));
        data.add(new InputEle(new Polinomial("+2x^2+2x+1"),new Polinomial("+2x^2+1"),new Polinomial("+1x^0"),new Polinomial("+2x^1")));

        for(InputEle obj : data){
            Polinomial[] divRez;
            divRez = ctr.poliDivision(obj.firstPol, obj.secondPol);
            assertEquals(obj.result.toString(), ctr.normalizePolinomial(divRez[0]).toString());
            assertEquals(obj.remainder.toString(), ctr.normalizePolinomial(divRez[1]).toString());
        }
        //This test is separate, because we cannot construct a polynomial with floating numbers!
        Polinomial[] divRez;
        divRez = ctr.poliDivision(new Polinomial("+2x^2+2x+1"),new Polinomial("+3x^2+3x"));
        assertEquals("+0.67x^0", ctr.normalizePolinomial(divRez[0]).toString());
        assertEquals("+1x^0", ctr.normalizePolinomial(divRez[1]).toString());


    }
}