package simple.java.argparser;

/**
 * The ArgumentValue class represents a value associated with an argument.
 *
 * @author Pedro Marín Sanchis
 */
public class ArgumentValue {
    private Object value = null;
    private final ArgumentValueType valueType;

    /**
     * Constructs an ArgumentValue object with the specified value type.
     *
     * @param valueType The value type of the argument value.
     */
    public ArgumentValue(ArgumentValueType valueType) {
        this.valueType = valueType;
    }

    /**
     * Sets the value of the argument from the given string value.
     *
     * @param value The string value to set.
     */
    public void setValueFromString(String value) {
        this.value = valueType.parseString(value);
    }

    /**
     * Returns the value of the argument.
     *
     * @return The value of the argument.
     */
    public Object getValue() {
        return value;
    }
}
