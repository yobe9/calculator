package una;

import bina.Mult;
import ts.Expression;
import ts.Num;

import java.util.Map;

/**
 * @author Yoav Berger <yoavbrgr@gmail.com> ID 313268393
 * @since 11/05/2020
 */
public class Sin extends UnaryExpression {

    /**
     * Constructor.
     *
     * @param firstExp the expression
     */
    public Sin(Expression firstExp) {
        super(firstExp);
    }

    /**
     * Evaluate the expression using the variable values provided
     * in the assignment, and return the result.  If the expression
     * contains a variable which is not in the assignment, an exception
     * is thrown.
     *
     * @throws Exception e if something went wrong
     * @param assignment a map of the variables of the expression
     * @return the result of the expression
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        double radExp = Math.toRadians(this.getFirstExp().evaluate(assignment));
        double result = Math.sin(radExp);
        return result;
    }

    /**
     * A convenience method. Like the `evaluate(assignment)` method above,
     * but uses an empty assignment.
     *
     * @throws Exception e if something went wrong
     * @return the result of the expression
     */
    @Override
    public double evaluate() throws Exception {
        double radExp = Math.toRadians(this.getFirstExp().evaluate());
        double result = Math.sin(radExp);
        return result;
    }

    /**
     * Returns a nice string representation of the expression.
     *
     * @return a nice string representation of the expression
     */
    @Override
    public String toString() {
        String str = "sin(" + this.getFirstExp().toString() + ")";
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
        Sin s = new Sin(firstExp);
        Expression nExp = s;
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
        //(sin(f(x))' = cos(f(x)) * f'(x)
        //creating the cos
        Cos nCos = new Cos(this.getFirstExp());
        //creating multiply
        Mult nMult = new Mult(nCos, this.getFirstExp().differentiate(var));
        //creating expression and returning
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
            Sin sinAfterSimp = new Sin(rec1);
            double possibleDouble = sinAfterSimp.evaluate();
            Num possibleNum = new Num(possibleDouble);
            Expression nExp = possibleNum;
            return nExp;
        } catch (Exception e) {
            //there is nothing to change
            ;
        }
        //in case none of the above
        Sin nSin = new Sin(rec1);
        Expression nExp = nSin;
        return nExp;
    }
}
