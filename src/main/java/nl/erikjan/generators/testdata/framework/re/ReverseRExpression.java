package nl.erikjan.generators.testdata.framework.re;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author gerard
 */
public class ReverseRExpression {
   /** Range expression */
	public static final short RANGE = 1;

	public static final short ANY = 2;

	public static final short LENGTH = 3;

	public static final short CHAR = 4;

	public static final short GREEDY = 5;
	
	public static final short OR = 6;
	
   public static final short GROUP_START = 7;
   
   public static final short GROUP_END = 8;
	
	public static final ReverseRExpression ANY_EXPR = new ReverseRExpression(ANY);
	
	public static final ReverseRExpression CHAR_EXPR = new ReverseRExpression(CHAR);

	short type;

	Object generationInstruction;

	List<ReverseRExpression> secundaryExpressions = new ArrayList<ReverseRExpression>();

	/**
	 * Constructor ReverseRExpression.
	 *
     * @param type
     * @param generationInstruction
     */
	public ReverseRExpression(short type, Object generationInstruction) {
		this.type = type;
		this.generationInstruction = generationInstruction;
	}

	/**
	 * Constructor ReverseRExpression.
	 * 
	 * @param type
	 */
	ReverseRExpression(short type) {
		this(type, null);
	}

	/**
	 * Returns the type.
	 * 
	 * @return Short
	 */
	public short getType() {
		return type;
	}

	public String toString() {
		StringBuffer r = new StringBuffer(this.getClass().getName());
		r.append(" (type=");
		r.append(getTypeAsString());
		r.append(")");
		if (generationInstruction != null) {
			r.append(System.getProperty("line.separator"));
			r.append("generation instruction = ");
			r.append(generationInstruction);
		}
		r.append(System.getProperty("line.separator"));
		if (secundaryExpressions != null && !secundaryExpressions.isEmpty()) {
			r.append("\tsecundary expressions = ");
			r.append(System.getProperty("line.separator"));
			for (Iterator<ReverseRExpression> iter = secundaryExpressions.iterator(); iter.hasNext();) {
				r.append("\t\t");
				r.append(iter.next().toString());
				if (iter.hasNext()) {
					r.append(System.getProperty("line.separator"));
				}
			}
		} else {
			r.append(" no secundary expressions");
		}
		return r.toString();
	}

	/**
	 * Method getTypeAsString.
	 * 
	 * @return Object
	 */
	private String getTypeAsString() {
		if (getType() == GREEDY) {
			return "greedy expression (e.g. *)";
		}
		if (getType() == RANGE) {
			return "range expression (e.g. [a-Z])";
		}
		if (getType() == CHAR) {
			return "char expression (e.g. g)";
		}
		if (getType() == LENGTH) {
			return "length expression (e.g. {3})";
		}
      if (getType() == GROUP_START) {
         return "group expression '(')";
      }
      if (getType() == GROUP_END) {
         return "group expression ')')";
      }

		return "unknown expression type: " + getType();
	}

	/**
	 *
     * @param type
     * @return
     */
	public static boolean isPrimary(short type) {
      if (type == RANGE || type == CHAR || type == OR || type == GROUP_START || type == GROUP_END || type == ANY) {
			return true;
		}
      if (type == LENGTH || type == GREEDY) {
			return false;
		}
		return false;
	}

	/**
	 * instance is primary
     * @return
     */
	public boolean isPrimary() {
		return isPrimary(getType());
	}

	/**
	 * Method addSecundaryExpression.
	 * 
	 * @param expr
	 */
	public void addSecundaryExpression(ReverseRExpression expr) {
		if (expr == null) {
			throw new IllegalArgumentException(getClass().getName()
					+ ".addSecundaryExpression(ReverseRExpression expr) expr may not be null.");
		}
		this.secundaryExpressions.add(expr);
	}

	/**
	 * Returns the generationInstruction.
	 * 
	 * @return Object
	 */
	public Object getGenerationInstruction() {
		return generationInstruction;
	}

	/**
	 * Returns the secundaryExpressions.
	 * 
	 * @return List
	 */
	public List<ReverseRExpression> getSecundaryExpressions() {
		return secundaryExpressions;
	}

}