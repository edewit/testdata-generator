package nl.erikjan.generators.testdata.framework.re;

/**
 * @author gerard Represents a length RE e.g. {12}
 */
public class ReverseLengthRExpression extends ReverseRExpression {
	private long startLength;

	private long endLength;

	public ReverseLengthRExpression(long startLength, long endLength) {
		super(LENGTH);
		this.startLength = startLength;
		this.endLength = endLength;
	}
	
	protected ReverseLengthRExpression(short type) {
		super(type);
	}

	/**
	 * @return the endLength
	 */
	public long getEndLength() {
		return endLength;
	}

	/**
	 * @param endLength the endLength to set
	 */
	public void setEndLength(long endLength) {
		this.endLength = endLength;
	}

	/**
	 * @return the startLength
	 */
	public long getStartLength() {
		return startLength;
	}

	/**
	 * @param startLength the startLength to set
	 */
	public void setStartLength(long startLength) {
		this.startLength = startLength;
	}

	/**
	 * 
	 */
	public String toString() {
		StringBuffer r = new StringBuffer(super.toString());
		r.append(System.getProperty("line.separator"));
        r.append("start length = ").append(startLength);
		r.append(System.getProperty("line.separator"));
        r.append("end length = ").append(endLength);
		return r.toString();
	}
}