package una;

import oth.BaseExpression;
import ts.Expression;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Yoav Berger <yoavbrgr@gmail.com> ID 313268393
 * @since 11/05/2020
 */
public abstract class UnaryExpression extends BaseExpression {
    //fields
    private Expression firstExp;

    /**
     * Constructor.
     *
     * @param firstExp the expression
     */
    public UnaryExpression(Expression firstExp) {
        this.firstExp = firstExp;
    }

    /**
     * Returns a list of the variables in the expression.
     *
     * @return a list of the variables in the expression
     */
    public List<String> getVariables() {
        List<String> lst = new LinkedList<String>();
        if (this.firstExp.getVariables() != null) {
            //check there are no repetition
            for (String i : this.firstExp.getVariables()) {
                if (!lst.contains(i)) {
                    lst.add(i);
                }
            }
        }
        //in case it was empty list
        if (lst.isEmpty()) {
            return null;
        }
        return lst;
    }

    /**
     * Returns the variables in the expression.
     *
     * @return the variables in the expression
     */
    public Expression getFirstExp() {
        return this.firstExp;
    }
}
