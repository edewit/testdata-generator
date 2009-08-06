package nl.erikjan.generators.testdata.framework.re;

import java.util.List;

public class ReverseGroupExpression extends ReverseRExpression {
   private List<ReverseRExpression> expressions;

   private ReverseGroupExpression groupEnd;

   ReverseGroupExpression(short type) {
      super(type);
   }

   public void setGroup(List<ReverseRExpression> expressions) {
      this.expressions = expressions;
   }

   public List<ReverseRExpression> getGroup() {
      return expressions;
   }

   public void setGroupEnd(ReverseGroupExpression groupEnd) {
      this.groupEnd = groupEnd;
   }

   public ReverseGroupExpression getGroupEnd() {
      return groupEnd;
   }

   @Override
   public String toString() {
      StringBuffer sb = new StringBuffer(super.toString());
      if (type == GROUP_START) {
         sb.append("\nGrouped expression (");

         if (expressions == null || expressions.isEmpty()) {
            sb.append("'Empty group'");
         } else {
            for (int i = 0; i < expressions.size(); i++) {
               sb.append("'").append(expressions.get(i)).append("'");
            }
         }

         sb.append(")");
      }

      return sb.toString();
   }
}
