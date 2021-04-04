package ts;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Yoav Berger <yoavbrgr@gmail.com> ID 313268393
 * @since 11/05/2020
 */
public class Var implements Expression {
    //fields
    private String varField;

    /**
     * Constructor.
     *
     * @param var variable
     */
    public Var(String var) {
        this.varField = var;
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
        if (assignment.get(this.varField) == null) {
            throw new Exception("The variable " + this.varField.toString() + "is not configure");
        }
        double result = assignment.get(this.varField);
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
        throw new Exception("Can't evaluate variable" + this.varField.toString() + "without it's number value");
    }

    /**
     * Returns a list of the variables in the expression.
     *
     * @return a list of the variables in the expression
     */
    @Override
    public List<String> getVariables() {
        List<String> lst = new LinkedList<String>();
        lst.add(this.varField);
        return lst;
    }

    /**
     * Returns a nice string representation of the expression.
     *
     * @return a nice string representation of the expression
     */
    @Override
    public String toString() {
        return this.varField;
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
        if (var.equals(this.varField)) {
            return expression;
        } else {
            Expression exp = this;
            return exp;
        }
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
        if (this.varField == var) {
            Num one = new Num(1.0);
            Expression exp = one;
            return exp;
        } else {
            Num zero = new Num(0.0);
            Expression exp = zero;
            return exp;
        }
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
