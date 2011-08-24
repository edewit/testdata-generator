package nl.erikjan.generators.testdata;

/**
* @author edewit
*/
public class FinalFieldModel {
    private final String fieldName;
    private final String fName;

    public FinalFieldModel(String fieldName, String inName) {
        this.fieldName = fieldName;
        this.fName = inName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getName() {
        return fName;
    }
}
