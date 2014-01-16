package ch.nerdin.generators.testdata.framework;

/**
 *
 * @author edewit
 */
public interface Generator<E> {

    E generate(FieldProperty property);

    boolean canGenerate(FieldProperty property);
}
