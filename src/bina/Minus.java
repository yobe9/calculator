package bina;

import ts.Expression;
import ts.Num;
import una.Neg;

import java.util.Map;

/**
 * @author Yoav Berger <yoavbrgr@gmail.com> ID 313268393
 * @since 11/05/2020
 */
public class Minus extends BinaryExpression {
    //fields
    //ts.Expression firstExp;
    //ts.Expression secondExp;

    /**
     * Constructor.
     *
     * @param firstExp  the left expression in the expression
     * @param secondExp the right expression in the expression
     */
    public Minus(Expression firstExp, Expression secondExp) {
        super(firstExp, secondExp);
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
        double result = this.getFirstExp().evaluate(assignment) - this.getSecondExp().evaluate(assignment);
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
        double result = this.getFirstExp().evaluate() - this.getSecondExp().evaluate();
        return result;
    }

    /**
     * Returns a nice string representation of the expression.
     *
     * @return a nice string representation of the expression
     */
    public String toString() {
        String str = "(" + this.getFirstExp().toString() + " - " + this.getSecondExp().toString() + ")";
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
        Expression secondExp = this.getSecondExp().assign(var, expression);
        Minus m = new Minus(firstExp, secondExp);
        Expression nExp = m;
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
        Minus diffExp = new Minus(this.getFirstExp().differentiate(var), this.getSecondExp().differentiate(var));
        Expression nExp = diffExp;
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
        Expression rec2 = this.getSecondExp().simplify();
        try {
            double posRec1 = rec1.evaluate();
            double posRec2 = rec2.evaluate();
            Num possibleNum = new Num(posRec1 - posRec2);
            Expression nExp = possibleNum;
            return nExp;
        } catch (Exception e) {
            //check in case one of them is zero
            double epsilon = Math.pow(10, -11);
            try {
                double possibleFirst = rec1.evaluate();
                if (Math.abs(possibleFirst) < epsilon) {
                    Neg negOfSecond = new Neg(rec2);
                    return negOfSecond;
                }
            } catch (Exception f) {
                //empty
                ;
            }
            try {
                double possibleSecond = rec2.evaluate();
                if (Math.abs(possibleSecond) < epsilon) {
                    return rec1;
                }
            } catch (Exception g) {
                //empty
                ;
            }
            //in case they are equals
            if (rec1.toString().equals(rec2.toString())) {
                Num zero = new Num(0.0);
                Expression zExp = zero;
                return zExp;
            }
        }
        //in case none of the above
        Minus nMinus = new Minus(rec1, rec2);
        Expression nExp = nMinus;
        return nExp;
    }
}
