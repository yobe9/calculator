package una;

import ts.Expression;
import ts.Num;
import ts.Var;

import java.util.Map;

/**
 * @author Yoav Berger <yoavbrgr@gmail.com> ID 313268393
 * @since 11/05/2020
 */
public class Neg extends UnaryExpression {

    /**
     * Constructor.
     *
     * @param firstExp the expression
     */
    public Neg(Expression firstExp) {
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
        double result = this.getFirstExp().evaluate(assignment) * (-1);
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
        double result = this.getFirstExp().evaluate() * (-1);
        return result;
    }

    /**
     * Returns a nice string representation of the expression.
     *
     * @return a nice string representation of the expression
     */
    @Override
    public String toString() {
        //checks if the expression starts with minus
        int minusIndex = this.getFirstExp().toString().indexOf("-");
        if (minusIndex == 1) {
            int stringLength = this.getFirstExp().toString().length();
            //cutting the parenthesis and the minus
            String str = this.getFirstExp().toString().substring(2, stringLength - 1);
            return str;

        }
        //regular print
        String str = "(-" + this.getFirstExp().toString() + ")";
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
        Neg n = new Neg(firstExp);
        Expression nExp = n;
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
        //(-f(x))' = -f'(x)
        //creating the negative
        Neg nNeg = new Neg(this.getFirstExp().differentiate(var));
        Expression nExp = nNeg;
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
            double possibleDouble = rec1.evaluate();
            Num possibleNum = new Num(possibleDouble * -1.0);
            Expression nExp = possibleNum;
            return nExp;
        } catch (Exception e) {
            //check if the var is already negative
            //finding if there is a minus in the first index and in the second
            int firstMinusIndex = rec1.toString().indexOf("-");
            //if there are two minuses
            if (firstMinusIndex == 0) {
                int stringLength = rec1.toString().length();
                //cutting the parenthesis and the minuses
                String nStr = rec1.toString().substring(1, stringLength);
                Var nVar = new Var(nStr);
                Expression nExp = nVar;
                return nExp;
            }

        }
        //in case none of the above
        Neg nNeg = new Neg(rec1);
        Expression nExp = nNeg;
        return nExp;
    }
}
