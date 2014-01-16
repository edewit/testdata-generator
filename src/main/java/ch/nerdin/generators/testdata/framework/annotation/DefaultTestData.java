package ch.nerdin.generators.testdata.framework.annotation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
* @author edewit
*/
public class DefaultTestData implements CreateTestData {

    public int min() {
        return 50;
    }

    public int max() {
        return 50;
    }

    public Class<? extends Collection> collectionType() {
        return ArrayList.class;
    }

    public Class<? extends Map> mapType() {
        return HashMap.class;
    }

    public Class<? extends Annotation> annotationType() {
        return CreateTestData.class;
    }
}
