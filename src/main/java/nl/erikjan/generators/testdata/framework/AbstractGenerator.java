package nl.erikjan.generators.testdata.framework;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

/**
 *
 * @author edewit
 */
public abstract class AbstractGenerator <E> implements Command {

    public abstract E generate(FieldProperty property);

    public abstract boolean canGenerate(FieldProperty property);

    public boolean execute(Context context) throws Exception {
       FieldContext fieldContext = (FieldContext) context;
       if (fieldContext.getFieldProperty() != null && canGenerate(fieldContext.getFieldProperty())) {
          fieldContext.put("value", generate(fieldContext.getFieldProperty()));
          return true;
       }

       return false;
    }
}
