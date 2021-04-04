package ts;

import java.util.List;
import java.util.Map;

/**
 * @author Yoav Berger <yoavbrgr@gmail.com> ID 313268393
 * @since 11/05/2020
 */
public class Num implements Expression {
    //fields
    private double num;

    /**
     * Constructor.
     *
     * @param num a number
     */
    public Num(double num) {
        this.num = num;
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
        return this.num;
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
        return this.num;
    }

    /**
     * Returns a list of the variables in the expression.
     *
     * @return a list of the variables in the expression
     */
    @Override
    public List<String> getVariables() {
        return null;
    }

    /**
     * Returns a nice string representation of the expression.
     *
     * @return a nice string representation of the expression
     */
    public String toString() {
        String str = Double.toString(this.num);
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
        Expression exp = this;
        return exp;
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
        Num zero = new Num(0.0);
        Expression exp = zero;
        return exp;
    }

    /**
     * Returned a simplified version of the current expression.
     *
     * @return a simplified version of the current expression
     */
    @Override
    public Expression simplify() {
        return this;
    }
}
