package ch.nerdin.generators.testdata.framework;

import ch.nerdin.generators.testdata.framework.integration.Manager;
import ch.nerdin.generators.testdata.inspector.FieldContext;
import ch.nerdin.generators.testdata.inspector.InspectionCatalog;
import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.Command;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 *
 * @author edewit
 */
public class GeneratorCatalogTest {
    private InspectionCatalog generatorCatalog;

    @Before
    public void setup() throws Exception {
        generatorCatalog = new InspectionCatalog();
        generatorCatalog.loadCatalog();
    }

    @Test
    public void testGetCatalog() throws Exception {
        Catalog catalog = generatorCatalog.getCatalog();
        assertNotNull(catalog);
        Command generatorChain = catalog.getCommand("InspectorChain");
        FieldContext context = new FieldContext(new HashMap<String, FieldProperty>(), Manager.class);
        generatorChain.execute(context);
        assertFalse(context.getInspectedFields().isEmpty());
    }

}