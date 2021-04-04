package bina;

import ts.Expression;
import ts.Num;

import java.util.Map;

/**
 * @author Yoav Berger <yoavbrgr@gmail.com> ID 313268393
 * @since 11/05/2020
 */
public class Mult extends BinaryExpression {

    /**
     * Constructor.
     *
     * @param firstExp  the left expression in the expression
     * @param secondExp the right expression in the expression
     */
    public Mult(Expression firstExp, Expression secondExp) {
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
        double result = this.getFirstExp().evaluate(assignment) * this.getSecondExp().evaluate(assignment);
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
        double result = this.getFirstExp().evaluate() * this.getSecondExp().evaluate();
        return result;
    }

    /**
     * Returns a nice string representation of the expression.
     *
     * @return a nice string representation of the expression
     */
    public String toString() {
        /**
         //in case one of the expressions is number and the other is variable
         boolean isFirstExpDigit = false;
         boolean isSecondExpDigit = false;
         //check if the first expression is number
         //getting the string of the first, inserting to array and checking if digit
         String firstStr = this.getFirstExp().toString();
         char[] charArr = firstStr.toCharArray();
         char firstChar = charArr[0];
         if (Character.isDigit(firstChar)) {
         isFirstExpDigit = true;
         }
         //check if the second expression is number
         //getting the string of the second, inserting to array and checking if digit
         String secondStr = this.getSecondExp().toString();
         char[] secondCharArr = secondStr.toCharArray();
         char firstCharOfSecondExp = secondCharArr[0];
         if (Character.isDigit(firstCharOfSecondExp)) {
         isSecondExpDigit = true;
         }
         //checking in which case we are and printing accordingly
         if (isFirstExpDigit == true && isSecondExpDigit == false) {
         String str = this.getFirstExp().toString() + this.getSecondExp().toString();
         return str;
         }
         if (isFirstExpDigit == false && isSecondExpDigit == true) {
         String str = this.getSecondExp().toString() + this.getFirstExp().toString();
         return str;
         }**/
        //printing if they both numbers or variables
        String str = "(" + this.getFirstExp().toString() + " * " + this.getSecondExp().toString() + ")";
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
        Mult m = new Mult(firstExp, secondExp);
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
        //(f(x)*g(x))' = f'(x)*g(x) + f(x)*g'(x)
        Mult firstDiffMult = new Mult(this.getFirstExp().differentiate(var), this.getSecondExp());
        Mult secondDiffMult = new Mult(this.getFirstExp(), this.getSecondExp().differentiate(var));
        Plus diffExp = new Plus(firstDiffMult, secondDiffMult);
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
            Num possibleNum = new Num(posRec1 * posRec2);
            Expression nExp = possibleNum;
            return nExp;
        } catch (Exception e) {
            double epsilon = Math.pow(10, -11);
            try {
                double firstPossible = rec1.evaluate();
                //check in case one of them is zero or one
                if (Math.abs(firstPossible - 1.0) < epsilon) {
                    return rec2;
                }
                //zeroes
                if (Math.abs(firstPossible) < epsilon) {
                    double zero = 0.0;
                    Num nZero = new Num(zero);
                    Expression nZ = nZero;
                    return nZ;
                }
            } catch (Exception f) {
                //empty
                ;
            }
            try {
                double secondPossible = rec2.evaluate();
                //check in case one of them is zero or one
                if (Math.abs(secondPossible - 1) < epsilon) {
                    return rec1;
                }
                //zeroes
                if (Math.abs(secondPossible) < epsilon) {
                    double zero = 0.0;
                    Num nZero = new Num(zero);
                    Expression nZ = nZero;
                    return nZ;
                }
            } catch (Exception g) {
                //empty
                ;
            }
        }
        //in case none of the above
        Mult nMult = new Mult(rec1, rec2);
        Expression nExp = nMult;
        return nExp;
    }
}
