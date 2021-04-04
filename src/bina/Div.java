package bina;

import ts.Expression;
import ts.Num;

import java.util.Map;

/**
 * @author Yoav Berger <yoavbrgr@gmail.com> ID 313268393
 * @since 11/05/2020
 */
public class Div extends BinaryExpression {

    /**
     * Constructor.
     *
     * @param firstExp  the left expression in the expression
     * @param secondExp the right expression in the expression
     */
    public Div(Expression firstExp, Expression secondExp) {
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
        //checking division by zero
        double epsilon = Math.pow(10, -7);
        if (Math.abs(this.getSecondExp().evaluate(assignment)) < epsilon) {
            throw new Exception("Division by zero");
        }
        double result = this.getFirstExp().evaluate(assignment) / this.getSecondExp().evaluate(assignment);
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
        //checking division by zero
        double epsilon = Math.pow(10, -11);
        if (Math.abs(this.getSecondExp().evaluate()) < epsilon) {
            throw new Exception("Division by zero");
        }
        double result = this.getFirstExp().evaluate() / this.getSecondExp().evaluate();
        return result;
    }

    /**
     * Returns a nice string representation of the expression.
     *
     * @return a nice string representation of the expression
     */
    public String toString() {
        String str = "(" + this.getFirstExp().toString() + " / " + this.getSecondExp().toString() + ")";
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
        Div d = new Div(firstExp, secondExp);
        Expression nExp = d;
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
        //(f(x)/g(x))' = (f'(x)*g(x) - f(x)*g'(x)) / g(x)^2
        Mult firstDiffDiv = new Mult(this.getFirstExp().differentiate(var), this.getSecondExp());
        Mult secondDiffDiv = new Mult(this.getFirstExp(), this.getSecondExp().differentiate(var));
        //bottom power two
        Num two = new Num(2.0);
        Pow thirdDiffDiv = new Pow(this.getSecondExp(), two);
        //builds mone
        Minus diffMone = new Minus(firstDiffDiv, secondDiffDiv);
        //builds expression
        Div d = new Div(diffMone, thirdDiffDiv);
        Expression nExp = d;
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
            //make sure no zeroes
            Div posDiv = new Div(rec1, rec2);
            double evalPosDiv = posDiv.evaluate();
            Num possibleNum = new Num(evalPosDiv);
            Expression nExp = possibleNum;
            return nExp;
        } catch (Exception e) {
            //check in case the second expression is one
            double epsilon = Math.pow(10, -11);
            try {
                double secondPossible = Math.abs(rec2.evaluate());
                if (secondPossible - 1 < epsilon) {
                    return rec1;
                }
            } catch (Exception f) {
                //empty
                ;
            }
            //check if both expressions are the same
            if (rec1.toString().equals(rec2.toString())) {
                double one = 1.0;
                Num oneNum = new Num(one);
                Expression nExp = oneNum;
                return nExp;
            }
        }
        //in case none of the above
        Div nDiv = new Div(rec1, rec2);
        Expression nExp = nDiv;
        return nExp;
    }
}
