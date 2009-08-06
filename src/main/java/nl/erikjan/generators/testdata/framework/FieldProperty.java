package nl.erikjan.generators.testdata.framework;

/**
 *
 * @author Erik Jan de Wit
 */
public class FieldProperty {

    private Class<?> type;
    private long maxLength;
    private long minLength;
    private String regex;
    private boolean past;
    private boolean future;
    private boolean lob;

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }
    
    public long getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(long maxLength) {
        this.maxLength = maxLength;
    }

    public long getMinLength() {
        return minLength;
    }

    public void setMinLength(long minLength) {
        this.minLength = minLength;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public boolean isFuture() {
        return future;
    }

    public void setFuture(boolean future) {
        this.future = future;
    }

    public boolean isPast() {
        return past;
    }

    public void setPast(boolean past) {
        this.past = past;
    }

    public void setLob(boolean lob) {
        this.lob = lob;
    }

    public boolean isLob() {
        return lob;
    }
}
