package org.int32_t.model;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polinomial {

    public List<Monomial> polinomial;

    public Polinomial(){
        polinomial  = new LinkedList<>();
    }

    public Polinomial(String poli) {
        polinomial  = new LinkedList<>();
        polinomial = regexParser(poli);
    }

    public void setPolinomial(String polinomial) {
        this.polinomial = regexParser(polinomial);
    }

    public int  getDegree(){
        int degree = 0;
        for (Monomial mon : polinomial ) {
            if(mon.getPower() > degree && mon.getCoefficient() != 0){
                degree = mon.getPower();
            }
        }
        return  degree;
    }

    private List<Monomial> regexParser(String poli) {
        List<Monomial> poliBuff = new LinkedList<>();
        String monMatcher = "(?<Mon>(?<t1>(?<s>\\+|-)(?<c>\\d+)?(?<x>x)(?<p>\\^\\d+))|(?<t2>(?<s1>\\+|-)(?<c1>\\d+)?(?<x2>x))|(?<t3>(?<s2>\\+|-)(?<c2>\\d+)))";
        Pattern patternMon = Pattern.compile(monMatcher);
        Matcher matcher = patternMon.matcher(poli);

        while (matcher.find()) {
           Monomial buff = new Monomial();
           if(matcher.group("t1") != null){//+-(nr)x^pow
               if(matcher.group("c") != null){
                   buff.setCoefficient(Integer.parseInt(matcher.group("c")));
               }else{
                   buff.setCoefficient(1);
               }
               if(matcher.group("s").equals("-")) buff.setCoefficient(buff.getCoefficient()*-1);
               buff.setPower(Integer.parseInt(matcher.group("p").replaceAll("\\^","")));
           }else if(matcher.group("t2") != null){//+-(nr)x
               if(matcher.group("c1") != null){
                   buff.setCoefficient(Float.parseFloat(matcher.group("c1")));
               }else{
                   buff.setCoefficient(1);
               }
               if(matcher.group("s1").equals("-")) buff.setCoefficient(buff.getCoefficient()*-1);
               buff.setPower(1);
           }else{
               if(matcher.group("c2") != null){
                   buff.setCoefficient(Integer.parseInt(matcher.group("c2")));
               }else{
                   buff.setCoefficient(1);
               }
               if(matcher.group("s2").equals("-")) buff.setCoefficient(buff.getCoefficient()*-1);
               buff.setPower(0);
           }
           poliBuff.add(buff);
        }
        return poliBuff;
    }

//    private List<Monomial> parse(String poli){
//        List<Monomial>poliBuff = new LinkedList<>();
//        poli = poli.replaceAll(" ",""); //Remove bad spacing
//        poli = poli.replaceAll("-","+-");  //Split the string into Monomials
//        String[] arrOfStr = poli.split("[+]", -2);
//        for (String a : arrOfStr){//Format 3x^2, x^3, x, 3
//            Monomial mon = new Monomial();
//            if(a.length() > 0) {
//                if (a.contains("x^")) {
//                    mon = caseXPow(a);
//                } else if (a.contains("x")) {
//                    mon = caseX(a);
//                } else { //Just a number
//                    mon.setCoefficient(Float.parseFloat(a));
//                    mon.setPower(0);
//                }
//                poliBuff.add(mon);
//            }
//        }
//        return poliBuff;
//    }
//
//    private Monomial caseX(String a){
//        Monomial mon = new Monomial();
//        a = a.replace("x", "");
//        if (a.length() > 0) { //Case where +-3x, or -x
//            if (a.length() == 1 && a.contains("-")) {
//                mon.setCoefficient(-1);
//            } else {
//                mon.setCoefficient(Float.parseFloat(a));
//            }
//        } else { //Case where x
//            mon.setCoefficient(1);
//        }
//        mon.setPower(1);
//        return  mon;
//    }
//
//    private Monomial caseXPow(String a){
//        Monomial mon = new Monomial();
//            String[] buff = a.split("\\^");
//            for (String c : buff) {
//                if (c.contains("x")) { //3x, x, this is the coeficiant part
//                    c = c.replace("x", "");
//                    if (c.length() > 0) {//Case where +-3x, or -x
//                        if (c.length() == 1 && c.contains("-")) {
//                            mon.setCoefficient(-1);
//                        } else {
//                            mon.setCoefficient(Float.parseFloat(c));
//                        }
//                    } else { //Case where x
//                        mon.setCoefficient(1);
//                    }
//                } else {//this is the power
//                    mon.setPower(Integer.parseInt(c));
//                }
//            }
//        return mon;
//    }

    @Override
    public String toString() {
        String polinomial = "";

        for(Monomial x : this.polinomial){
            polinomial += x.toString();
        }
        if(polinomial.isEmpty()){
            polinomial += "0";
        }
        return  polinomial;
    }
}
