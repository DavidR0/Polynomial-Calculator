package org.int32_t.model;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polinomial {

    public List<Monomial> polinomial; //This is the polynomial its self

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

    //Because in the constructor we expect a string that represents the polynomial, we have to parse it and create the monomials. This is done using regex
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

    //Returns a boolean that tells us if a polynomial is null
    public boolean isNUll(){
        boolean isNull = true;

        for(Monomial mon : this.polinomial){
            if(mon.getCoefficient() != 0){
                isNull = false;
            }

        }
        return isNull;
    }

    //Returns they polynomial in string format
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
