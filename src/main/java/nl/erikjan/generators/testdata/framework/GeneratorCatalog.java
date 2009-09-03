package nl.erikjan.generators.testdata.framework;

import java.net.MalformedURLException;
import java.net.URL;
import javax.annotation.PostConstruct;
import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.CatalogFactory;
import org.apache.commons.chain.config.ConfigParser;
import org.springframework.stereotype.Component;

/**
 *
 * @author edewit
 */
@Component
public class GeneratorCatalog {

    private ConfigParser parser;

    @PostConstruct
    public void loadCatalog() throws Exception {
        parser = new ConfigParser();
        reload();
    }

    public void reload() throws Exception {
        CatalogFactory.clear();
        parser.parse(getConfigUrl());
    }

    public URL getConfigUrl() throws MalformedURLException {
        return getClass().getResource("/chain-config.xml");
    }

    public Catalog getCatalog() {
        return CatalogFactory.getInstance().getCatalog("GeneratorCatalog");
    }
}
