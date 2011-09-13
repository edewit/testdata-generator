package ch.nerdin.generators.testdata.framework.re;

/**
 * Represents an or expression, current implemention is wrong. 
 * As an or expression can have more than just 2 expressions.
 * @author Erik Jan de Wit
 */
public class ReverseOrExpression extends ReverseRExpression {

    private ReverseRExpression left;
    private ReverseRExpression right;

    ReverseOrExpression(ReverseRExpression left, ReverseRExpression right) {
        super(ReverseRExpression.OR);
        this.right = right;
        this.left = left;
    }

    ReverseOrExpression(short type) {
        super(type);
    }

    /**
     * @return the left
     */
    public ReverseRExpression getLeft() {
        return left;
    }

    /**
     * @param left the left to set
     */
    public void setLeft(ReverseRExpression left) {
        this.left = left;
    }

    /**
     * @return the right
     */
    public ReverseRExpression getRight() {
        return right;
    }

    /**
     * @param right the right to set
     */
    public void setRight(ReverseRExpression right) {
        this.right = right;
    }
}
