package simple.java.argparser;

/**
 * The ArgumentAction functional interface represents an action to be executed for an argument.
 *
 * @author Pedro Marín Sanchis
 */
@FunctionalInterface
public interface ArgumentAction {
    /**
     * Executes the implemented action.
     *
     * @throws ArgumentException If an error occurs during the execution of the action.
     */
    void execute() throws ArgumentException;
}
