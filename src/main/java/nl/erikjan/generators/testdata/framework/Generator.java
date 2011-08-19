package nl.erikjan.generators.testdata.framework;

import nl.erikjan.generators.testdata.inspector.FieldContext;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

/**
 *
 * @author edewit
 */
public interface Generator<E> {

    E generate(FieldProperty property);

    boolean canGenerate(FieldProperty property);
}
