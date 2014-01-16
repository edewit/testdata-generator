package ch.nerdin.generators.testdata.framework.re;

public class ReverseGreedyExpression extends ReverseLengthRExpression {

	private static final int MAX_GREEDY_REPEAT_COUNT = 10;
	/**
	 * @param mask
	 */
	public ReverseGreedyExpression(char mask) {
		super(ReverseRExpression.GREEDY);
		switch (mask) {
		case '?':
			//0 or 1 times
			setStartLength(0);
			setEndLength(2);
			break;
		case '+':
			//1 to max
			setStartLength(1);
			setEndLength(MAX_GREEDY_REPEAT_COUNT);
			break;
		case '*':
			//0 to max
			setStartLength(0);
			setEndLength(MAX_GREEDY_REPEAT_COUNT);
			break;
		default:
			throw new IllegalArgumentException(mask + " not a valid greedy mask");
		}
	}
}
