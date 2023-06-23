package simple.java.argparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * The Argument class represents a command-line argument.
 * It stores information such as the accepted names, argument values, and associated actions.
 *
 * @author Pedro Marín Sanchis
 */
@SuppressWarnings("unused")
public class Argument {
    /** Arguments that cannot be present together. Example: --client --server */
    private final ArrayList<Argument> mutuallyExclusiveArguments = new ArrayList<>();
    /** Arguments that must be present together. Example: --windowed --width 1920 --height 1080 */
    private final ArrayList<Argument> requiredArguments = new ArrayList<>();
    /** Arguments names. Example: --width --w */
    private final String[] acceptedNames;
    /** Required argument values. Example: --width 250 */
    private final ArgumentValue[] argumentValues;
    /** Action that is performed when the argument is included in the argument list. */
    private ArgumentAction inclusionAction;
    /** Action that is performed when the argument has not appeared in the argument list. */
    private ArgumentAction exclusionAction;

    /**
     * Constructs an Argument object with the specified accepted names and argument values.
     *
     * @param acceptedNames The accepted names for the argument.
     * @param argumentValues The argument values.
     */
    public Argument(String[] acceptedNames, ArgumentValue[] argumentValues) {
        this.acceptedNames = acceptedNames;
        this.argumentValues = argumentValues;
    }

    @Override
    public String toString() {
        return "Argument{" +
                "acceptedNames=" + Arrays.toString(acceptedNames) +
                '}';
    }

    /**
     * Adds a mutually exclusive argument.
     *
     * @param argument The argument that is mutually exclusive with this argument.
     */
    public void addMutuallyExclusiveArgument(Argument argument) {
        if (this.equals(argument)) {return;} // Argument cannot be exclusive of itself...
        if (!mutuallyExclusiveArguments.contains(argument)) {
            mutuallyExclusiveArguments.add(argument);
            argument.addMutuallyExclusiveArgument(this);
        }
    }

    /**
     * Checks if this argument is compatible with the given argument.
     *
     * @param argument The argument to check compatibility with.
     * @return True if the arguments are compatible, false otherwise.
     */
    public boolean isCompatibleWith(Argument argument) {
        return !mutuallyExclusiveArguments.contains(argument);
    }

    /**
     * Checks if the required arguments for this argument are fulfilled.
     *
     * @param arguments The collection of arguments.
     * @return True if the required arguments are fulfilled, false otherwise.
     */
    public boolean hasRequirementsFulfilled(Collection<Argument> arguments) {
        return arguments.containsAll(requiredArguments);
    }

    /**
     * Adds a required argument.
     *
     * @param argument The required argument.
     */
    public void addRequiredArgument(Argument argument) {
        if (this.equals(argument)) {return;} // Why would anyone do this?
        if (!requiredArguments.contains(argument)) {
            requiredArguments.add(argument);
        }
    }

    /**
     * Sets the inclusion action for this argument.
     *
     * @param inclusionAction The inclusion action to be executed when the argument is included.
     */
    public void setInclusionAction(ArgumentAction inclusionAction) {
        this.inclusionAction = inclusionAction;
    }

    /**
     * Sets the exclusion action for this argument.
     *
     * @param exclusionAction The exclusion action to be executed when the argument is excluded.
     */
    public void setExclusionAction(ArgumentAction exclusionAction) {
        this.exclusionAction = exclusionAction;
    }

    /**
     * Executes the inclusion action for this argument.
     *
     * @throws ArgumentException If an error occurs during execution.
     */
    public void executeInclusionAction() throws ArgumentException {
        inclusionAction.execute();
    }

    /**
     * Executes the exclusion action for this argument.
     *
     * @throws ArgumentException If an error occurs during execution.
     */
    public void executeExclusionAction() throws ArgumentException {
        exclusionAction.execute();
    }

    /**
     * Returns the accepted names for this argument.
     *
     * @return The accepted names.
     */
    public String[] getAcceptedNames() {
        return acceptedNames;
    }

    /**
     * Returns the argument values for this argument.
     *
     * @return The argument values.
     */
    public ArgumentValue[] getArgumentValues() {
        return argumentValues;
    }

    /**
     * Returns the value of the argument at the specified index.
     *
     * @param index The index of the argument value.
     * @return The value of the argument.
     */
    public Object getArgumentValue(int index) {
        return argumentValues[index].getValue();
    }
}
