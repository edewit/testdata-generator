package ch.nerdin.generators.testdata.inspector;

import java.net.URL;
import javax.annotation.PostConstruct;
import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.CatalogFactory;
import org.apache.commons.chain.config.ConfigParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author edewit
 */
@Component
public class InspectionCatalog {
    private static final Logger logger = LoggerFactory.getLogger(InspectionCatalog.class.getName());
    private ConfigParser parser;

    @PostConstruct
    public void loadCatalog() {
        parser = new ConfigParser();
        reload();
    }

    void reload() {
        CatalogFactory.clear();
        try {
            parser.parse(getConfigUrl());
        } catch (Exception e) {
            logger.error("could parse chain catalog from " + getConfigUrl(), e);
        }
    }

    URL getConfigUrl() {
        return getClass().getResource("/chain-config.xml");
    }

    public Catalog getCatalog() {
        return CatalogFactory.getInstance().getCatalog("InspectionCatalog");
    }
}
