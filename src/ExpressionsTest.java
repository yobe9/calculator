import bina.Mult;
import bina.Plus;
import bina.Pow;
import ts.Expression;
import ts.Num;
import ts.Var;
import una.Sin;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Yoav Berger <yoavbrgr@gmail.com> ID 313268393
 * @since 11/05/2020
 */
public class ExpressionsTest {
    /**
     * Main function.
     * Create expression and runs it
     *
     * @param args main
     */
    public static void main(String[] args) {
        Expression e = new Plus(new Plus(new Mult(new Num(2.0), new Var("x"))
                , new Sin(new Mult(new Num(4.0), new Var("y")))), new Pow(new Var("e"), new Var("x")));
        //printing the expression
        System.out.println(e);
        //creating the map and the variables values
        Map<String, Double> m = new TreeMap<String, Double>();
        m.put("x", 2.0);
        m.put("y", 0.25);
        m.put("e", Math.E);
        try {
            double result = e.evaluate(m);
            System.out.println(result);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        //printing differentiated
        System.out.println(e.differentiate("x"));
        //printing the result of differentiate
        try {
            double result = e.differentiate("x").evaluate(m);
            System.out.println(result);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        //printing the simplified
        System.out.println(e.differentiate("x").simplify());

    }
}