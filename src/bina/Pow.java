package bina;

import ts.Expression;
import ts.Num;

import java.util.Map;

/**
 * @author Yoav Berger <yoavbrgr@gmail.com> ID 313268393
 * @since 11/05/2020
 */
public class Pow extends BinaryExpression {

    /**
     * Constructor.
     *
     * @param firstExp  the left expression in the expression
     * @param secondExp the right expression in the expression
     */
    public Pow(Expression firstExp, Expression secondExp) {
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
        //checking wrong power use
        if ((this.getSecondExp().evaluate(assignment) > -1) && (this.getSecondExp().evaluate(assignment) < 1)
                && (this.getFirstExp().evaluate(assignment) < 0)) {
            throw new Exception("Wrong power use");
        }
        double result = Math.pow(this.getFirstExp().evaluate(assignment), this.getSecondExp().evaluate(assignment));
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
        //checking wrong power use
        if ((this.getSecondExp().evaluate() > -1) && (this.getSecondExp().evaluate() < 1)
                && (this.getFirstExp().evaluate() < 0)) {
            throw new Exception("Wrong power use");
        }
        double result = Math.pow(this.getFirstExp().evaluate(), this.getSecondExp().evaluate());
        return result;
    }

    /**
     * Returns a nice string representation of the expression.
     *
     * @return a nice string representation of the expression
     */
    @Override
    public String toString() {
        String str = "(" + this.getFirstExp().toString() + "^" + this.getSecondExp().toString() + ")";
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
        Pow p = new Pow(firstExp, secondExp);
        Expression nExp = p;
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
        //(f(x)^g(x))' = f(x)^g(x) * (f'(x)*(g(x)/f(x)) + (g'(x) * log(e, f(x))
        //left expression
        Pow fPowG = new Pow(this.getFirstExp(), this.getSecondExp());
        //div expression
        Div gDivF = new Div(this.getSecondExp(), this.getFirstExp());
        //creating e and the log
        Num logE = new Num(Math.E);
        Log eLogF = new Log(logE, this.getFirstExp());
        //right parenthesis
        Mult rightMult = new Mult(this.getSecondExp().differentiate(var), eLogF);
        //left parenthesis
        Mult leftMult = new Mult(this.getFirstExp().differentiate(var), gDivF);
        //plus all the parenthesis
        Plus pAll = new Plus(leftMult, rightMult);
        //multyply the whole expression
        Mult mAll = new Mult(fPowG, pAll);
        //creating new expression and return
        Expression nExp = mAll;
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
            Pow posPow = new Pow(rec1, rec2);
            double evalPosPow = posPow.evaluate();
            Num possibleNum = new Num(evalPosPow);
            Expression nExp = possibleNum;
            return nExp;
        } catch (Exception e) {
            double epsilon = Math.pow(10, -11);
            //check if the first is zero
            try {
                double possibleFirst = Math.abs(rec1.evaluate());
                //check in case the base is zero
                if (possibleFirst < epsilon) {
                    Num zero = new Num(0.0);
                    Expression nExp = zero;
                    return nExp;
                }
            } catch (Exception f) {
                //empty
                ;
            }
            try {
                double possibleSecond = Math.abs(rec2.evaluate());
                //check in case the exponent is zero
                if (possibleSecond < epsilon) {
                    Num one = new Num(1.0);
                    Expression nExp = one;
                    return nExp;
                }
                //check in case the exponent is 1
                if (possibleSecond - 1.0 < epsilon) {
                    Expression nExp = rec1;
                    return nExp;
                }
            } catch (Exception g) {
                //empty
                ;
            }

        }
        //in case none of the above
        Pow nPow = new Pow(rec1, rec2);
        Expression nExp = nPow;
        return nExp;
    }
}
