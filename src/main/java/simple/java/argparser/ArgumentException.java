package simple.java.argparser;

/**
 * The ArgumentException class represents an exception that is thrown when an error occurs during argument parsing.
 *
 * @author Pedro Marín Sanchis
 */
public class ArgumentException extends RuntimeException {
    /**
     * Constructs a new ArgumentException with the specified error message.
     *
     * @param message The error message.
     */
    public ArgumentException(String message) {
        super(message);
    }
}
