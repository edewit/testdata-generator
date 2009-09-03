package nl.erikjan.generators.testdata.framework;

import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.Command;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author edewit
 */
public class GeneratorCatalogTest {
    private GeneratorCatalog generatorCatalog;

    @Before
    public void setup() throws Exception {
        generatorCatalog = new GeneratorCatalog();
        generatorCatalog.loadCatalog();
    }

    @Test
    public void testGetCatalog() throws Exception {
        Catalog catalog = generatorCatalog.getCatalog();
        assertNotNull(catalog);
        Command generatorChain = catalog.getCommand("GeneratorChain");
        FieldContext context = new FieldContext();
        FieldProperty property = new FieldProperty();
        context.setFieldProperty(property);
        property.setRegex("[a-z]{2,6}");
        generatorChain.execute(context);
        assertNotNull(context.get("value"));
    }

}