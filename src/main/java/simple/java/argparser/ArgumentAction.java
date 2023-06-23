package simple.java.argparser;

/**
 * @author Pedro Marín Sanchis
 */
@FunctionalInterface
public interface ArgumentAction {
    void execute() throws ArgumentException;
}
