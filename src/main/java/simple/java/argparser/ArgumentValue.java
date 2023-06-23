package simple.java.argparser;

/**
 * @author Pedro Marín Sanchis
 */
public class ArgumentValue {
    private Object value = null;
    private ArgumentValueType valueType = ArgumentValueType.STRING;

    public ArgumentValue(ArgumentValueType valueType) {
        this.valueType = valueType;
    }

    public void setValueFromString(String value) {
        this.value = valueType.parseString(value);
    }

    public Object getValue() {
        return value;
    }
}
