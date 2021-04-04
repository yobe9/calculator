package bina;

import ts.Expression;
import oth.BaseExpression;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Yoav Berger <yoavbrgr@gmail.com> ID 313268393
 * @since 11/05/2020
 */
public abstract class BinaryExpression extends BaseExpression {
    //fields
    private Expression firstExp;
    private Expression secondExp;

    /**
     * Constructor.
     *
     * @param firstExp  the left expression in the expression
     * @param secondExp the right expression in the expression
     */
    public BinaryExpression(Expression firstExp, Expression secondExp) {
        this.firstExp = firstExp;
        this.secondExp = secondExp;
    }

    /**
     * Returns a list of the variables in the expression.
     *
     * @return a list of the variables in the expression
     */
    public List<String> getVariables() {
        List<String> lst = new LinkedList<String>();
        if (this.firstExp.getVariables() != null) {
            lst.addAll(this.firstExp.getVariables());
        }
        if (this.secondExp.getVariables() != null) {
            //check there are no repetition
            for (String i : this.secondExp.getVariables()) {
                if (!lst.contains(i)) {
                    lst.add(i);
                }
            }
        }
        //in case it was 2 empty lists
        if (lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    /**
     * Returns the first variables in the expression.
     *
     * @return the first variables in the expression
     */
    public Expression getFirstExp() {
        return this.firstExp;
    }

    /**
     * Returns the second variables in the expression.
     *
     * @return the second variables in the expression
     */
    public Expression getSecondExp() {
        return this.secondExp;
    }
}
