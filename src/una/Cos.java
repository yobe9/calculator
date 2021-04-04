package una;

import bina.Mult;
import ts.Expression;
import ts.Num;

import java.util.Map;

/**
 * @author Yoav Berger <yoavbrgr@gmail.com> ID 313268393
 * @since 11/05/2020
 */
public class Cos extends UnaryExpression {

    /**
     * Constructor.
     *
     * @param firstExp the expression
     */
    public Cos(Expression firstExp) {
        super(firstExp);
    }

    /**
     * Evaluate the expression using the variable values provided
     * in the assignment, and return the result.  If the expression
     * contains a variable which is not in the assignment, an exception
     * is thrown.
     *
     * @param assignment a map of the variables of the expression
     * @return the result of the expression
     * @throws Exception e if something went wrong
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        double radExp = Math.toRadians(this.getFirstExp().evaluate(assignment));
        double result = Math.cos(radExp);
        return result;
    }

    /**
     * A convenience method. Like the `evaluate(assignment)` method above,
     * but uses an empty assignment.
     *
     * @return the result of the expression
     * @throws Exception e if something went wrong
     */
    @Override
    public double evaluate() throws Exception {
        double radExp = Math.toRadians(this.getFirstExp().evaluate());
        double result = Math.cos(radExp);
        return result;
    }

    /**
     * Returns a nice string representation of the expression.
     *
     * @return a nice string representation of the expression
     */
    @Override
    public String toString() {
        String str = "cos(" + this.getFirstExp().toString() + ")";
        return str;
    }

    /**
     * Returns a new expression in which all occurrences of the variable
     * var are replaced with the provided expression (Does not modify the
     * current expression).
     *
     * @param var        a variable from the user
     * @param expression expression from the user
     * @return the new expression after replacing the var with the expression
     */
    @Override
    public Expression assign(String var, Expression expression) {
        Expression firstExp = this.getFirstExp().assign(var, expression);
        Cos c = new Cos(firstExp);
        Expression nExp = c;
        return nExp;
    }

    /**
     * Returns the expression tree resulting from differentiating
     * the current expression relative to variable `var`.
     *
     * @param var a variable from the user
     * @return the new expression after differentiate according to var
     */
    @Override
    public Expression differentiate(String var) {
        //(cos(f(x))' = -sin(f(x)) * f'(x)
        Sin sinus = new Sin(this.getFirstExp());
        //creating negative sinus
        Neg negSin = new Neg(sinus);
        //creating the multiply
        Mult nMult = new Mult(negSin, this.getFirstExp().differentiate(var));
        Expression nExp = nMult;
        return nExp;
    }

    /**
     * Returned a simplified version of the current expression.
     *
     * @return a simplified version of the current expression
     */
    @Override
    public Expression simplify() {
        Expression rec1 = this.getFirstExp().simplify();
        try {
            Cos cosAfterSimp = new Cos(rec1);
            double possibleDouble = cosAfterSimp.evaluate();
            Num possibleNum = new Num(possibleDouble);
            Expression nExp = possibleNum;
            return nExp;
        } catch (Exception e) {
            //there is nothing to change
            ;
        }
        //in case none of the above
        Cos nCos = new Cos(rec1);
        Expression nExp = nCos;
        return nExp;
    }
}
