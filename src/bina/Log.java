package bina;

import ts.Expression;
import ts.Num;
import ts.Var;

import java.util.Map;

/**
 * @author Yoav Berger <yoavbrgr@gmail.com> ID 313268393
 * @since 11/05/2020
 */
public class Log extends BinaryExpression {

    /**
     * Constructor.
     *
     * @param firstExp  the left expression in the expression
     * @param secondExp the right expression in the expression
     */
    public Log(Expression firstExp, Expression secondExp) {
        super(firstExp, secondExp);
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
        //checking log rules
        double epsilon = Math.pow(10, -11);
        if ((this.getFirstExp().evaluate(assignment) <= 0) || (this.getFirstExp().evaluate(assignment) == 1)
                || (this.getSecondExp().evaluate(assignment) <= 0)
                || Math.abs(Math.log(this.getFirstExp().evaluate(assignment))) < epsilon) {
            throw new Exception("Illegal log rules");
        }
        //check if the log are equals in case of real log and fake log
        //epsilon for case of given numbers with 2 digit
        double epsilon2 = Math.pow(10, -7);
        if (Math.abs(Math.log(this.getSecondExp().evaluate(assignment))
                - Math.log(this.getFirstExp().evaluate(assignment))) < epsilon2) {
            return 1.0;
        }
        //regular log
        double result = Math.log(this.getSecondExp().evaluate(assignment))
                / Math.log(this.getFirstExp().evaluate(assignment));
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
        //checking log rules
        double epsilon = Math.pow(10, -7);
        if ((this.getFirstExp().evaluate() <= 0) || (this.getFirstExp().evaluate() == 1)
                || (this.getSecondExp().evaluate() <= 0)
                || Math.abs(Math.log(this.getFirstExp().evaluate())) < epsilon) {
            throw new Exception("Illegal log rules");
        }
        double result = Math.log(this.getSecondExp().evaluate()) / Math.log(this.getFirstExp().evaluate());
        return result;
    }

    /**
     * Returns a nice string representation of the expression.
     *
     * @return a nice string representation of the expression
     */
    @Override
    public String toString() {
        //checking if the expression inside the log is E
        double epsilon = Math.pow(10, -11);
        String numberE = String.valueOf(Math.E);
        if (this.getFirstExp().toString().equals(numberE)) {
            String str = "log(e" + ", " + this.getSecondExp().toString() + ")";
            return str;
        }
        /**if (Math.abs(Double.parseDouble(this.getFirstExp().toString()) - Math.E) < epsilon) {
         String str = "log(e" + ", " + this.getSecondExp().toString() + ")";
         return str;
         }**/
        String str = "log(" + this.getFirstExp().toString() + ", " + this.getSecondExp().toString() + ")";
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
        Log l = new Log(firstExp, secondExp);
        Expression nExp = l;
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
        //(log(f(x), g(x)))' = {[(log(e, f(x)) * g'(x)) /g(x)] - [(log(e, g(x)) * f'(x)) / f(x)]} / log(e, f(x))^2
        //creating e and the log
        Num logE = new Num(Math.E);
        Log eLogF = new Log(logE, this.getFirstExp());
        //creating first parenthesis
        Mult logMultGDiff = new Mult(eLogF, this.getSecondExp().differentiate(var));
        Div moneLeft = new Div(logMultGDiff, this.getSecondExp());
        //creating second parenthesis
        Log eLogG = new Log(logE, this.getSecondExp());
        Mult logMultFDiff = new Mult(eLogG, this.getFirstExp().differentiate(var));
        Div moneRight = new Div(logMultFDiff, this.getFirstExp());
        //bina.Minus mone
        Minus mone = new Minus(moneLeft, moneRight);
        //dividing with mechane
        Num two = new Num(2.0);
        Pow mechane = new Pow(eLogF, two);
        Div res = new Div(mone, mechane);
        //creating expression and returning it
        Expression nExp = res;
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
            //make sure log is alright
            Log posLog = new Log(rec1, rec2);
            double evalPosLog = posLog.evaluate();
            Num possibleNum = new Num(evalPosLog);
            Expression nExp = possibleNum;
            return nExp;
        } catch (Exception e) {
            //check in case the second expression is one than always zero, or the second is E
            double epsilon = Math.pow(10, -11);
            try {
                double secondPossible = Math.abs(rec2.evaluate());
                if (secondPossible - 1.0 < epsilon) {
                    Num zero = new Num(0.0);
                    Expression nExp = zero;
                    return nExp;
                }
                //check if the second is e
                if (secondPossible - Math.E < epsilon) {
                    Var numberE = new Var("e");
                    Expression eExp = numberE;
                    rec2 = eExp;
                }
            } catch (Exception f) {
                //empty
                ;
            }
            try {
                double firstPossible = Math.abs(rec1.evaluate());
                if (firstPossible - Math.E < epsilon) {
                    Var numberE = new Var("e");
                    Expression eExp = numberE;
                    rec1 = eExp;
                }
            } catch (Exception g) {
                //empty
                ;
            }
            //check if both expressions are the same
            if (rec1.toString().equals(rec2.toString())) {
                Num one = new Num(1.0);
                Expression nExp = one;
                return nExp;
            }
        }
        //in case none of the above
        Log nLog = new Log(rec1, rec2);
        Expression nExp = nLog;
        return nExp;
    }
}
