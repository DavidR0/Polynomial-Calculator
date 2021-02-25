package org.int32_t.model;

import java.util.LinkedList;
import java.util.List;

public class Polinomial {

    public List<Monomial> polinomial;

    public Polinomial(){
        polinomial  = new LinkedList<>();
    }

    public Polinomial(String poli) {
        polinomial  = new LinkedList<>();
        polinomial = parse(poli);
    }

    public void setPolinomial(String polinomial) {
        this.polinomial = parse(polinomial);
    }

    private List<Monomial> parse(String poli){
        List<Monomial>poliBuff = new LinkedList<>();
        poli = poli.replaceAll(" ",""); //Remove bad spacing
        poli = poli.replaceAll("-","+-");  //Split the string into Monomials
        String[] arrOfStr = poli.split("[+]", -2);
        for (String a : arrOfStr){//Format 3x^2, x^3, x, 3
            Monomial mon = new Monomial();
            if(a.length() > 0) {
                if (a.contains("x^")) {
                    mon = caseXPow(a);
                } else if (a.contains("x")) {
                    mon = caseX(a);
                } else { //Just a number
                    mon.setCoeficiant(Float.parseFloat(a));
                    mon.setPower(0);
                }
                poliBuff.add(mon);
            }
        }
        return poliBuff;
    }

    private Monomial caseX(String a){
        Monomial mon = new Monomial();
        a = a.replace("x", "");
        if (a.length() > 0) { //Case where +-3x, or -x
            if (a.length() == 1 && a.contains("-")) {
                mon.setCoeficiant(-1);
            } else {
                mon.setCoeficiant(Float.parseFloat(a));
            }
        } else { //Case where x
            mon.setCoeficiant(1);
        }
        mon.setPower(1);
        return  mon;
    }

    private Monomial caseXPow(String a){
        Monomial mon = new Monomial();
            String[] buff = a.split("\\^");
            for (String c : buff) {
                if (c.contains("x")) { //3x, x, this is the coeficiant part
                    c = c.replace("x", "");
                    if (c.length() > 0) {//Case where +-3x, or -x
                        if (c.length() == 1 && c.contains("-")) {
                            mon.setCoeficiant(-1);
                        } else {
                            mon.setCoeficiant(Float.parseFloat(c));
                        }
                    } else { //Case where x
                        mon.setCoeficiant(1);
                    }
                } else {//this is the power
                    mon.setPower(Integer.parseInt(c));
                }
            }
        return mon;
    }

    @Override
    public String toString() {
        String polinomial = "";

        for(Monomial x : this.polinomial){
            polinomial += x.toString();
        }
        return  polinomial;
    }
}
