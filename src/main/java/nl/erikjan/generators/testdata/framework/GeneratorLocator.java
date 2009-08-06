package nl.erikjan.generators.testdata.framework;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.lang.StringUtils;

/**
 *
 */
public class GeneratorLocator {

    private List<Class<?>> type;
    private Map<String, Class<? extends AbstractGenerator>> generatorMapping;

    public List<Class<?>> getType() {
        return type;
    }

    public void setType(List<Class<?>> type) {
        this.type = type;
    }

    public Map<String, Class<? extends AbstractGenerator>> getGeneratorMapping() {
        return generatorMapping;
    }

    public void setGeneratorMapping(Map<String, Class<? extends AbstractGenerator>> generatorMapping) {
        this.generatorMapping = generatorMapping;
    }

    public Class<? extends AbstractGenerator> getGenerator(FieldProperty property) {
        JXPathContext context = JXPathContext.newContext(property);
        context.setLenient(true);
        Iterator<Entry<String, Class<? extends AbstractGenerator>>> iter = generatorMapping.entrySet().iterator();
        while(iter.hasNext()) {
            Entry<String, Class<? extends AbstractGenerator>> entry = iter.next();
            String query = entry.getKey();
            if (StringUtils.isEmpty(query) || context.getValue(query) != null) {
                return entry.getValue();
            }
        }

        return null;
    }
}
