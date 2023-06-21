package simple.java.argparser;

@FunctionalInterface
public interface ArgumentAction {
    void execute() throws ArgumentActionException;
}
