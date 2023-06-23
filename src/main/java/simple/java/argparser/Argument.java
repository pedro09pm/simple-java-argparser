package simple.java.argparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author Pedro Marín Sanchis
 */
public class Argument {
    /** Arguments that cannot be present together. Example: --client --server */
    private final ArrayList<Argument> mutuallyExclusiveArguments = new ArrayList<Argument>();
    /** Arguments that must be present together. Example: --windowed --width 1920 --height 1080 */
    private final ArrayList<Argument> requiredArguments = new ArrayList<Argument>();
    /** Arguments names. Example: --width --w */
    private final String[] acceptedNames;
    /** Required argument values. Example: --width 250 */
    private final ArgumentValue[] argumentValues;
    /** Action that is performed when the argument is included in the argument list. */
    private ArgumentAction inclusionAction;
    /** Action that is performed when the argument has not appeared in the argument list. */
    private ArgumentAction exclusionAction;

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

    public void addMutuallyExclusiveArgument(Argument argument) {
        if (this.equals(argument)) {return;} // Argument cannot be exclusive of itself...
        if (!mutuallyExclusiveArguments.contains(argument)) {
            mutuallyExclusiveArguments.add(argument);
            argument.addMutuallyExclusiveArgument(this);
        }
    }

    public boolean isCompatibleWith(Argument argument) {
        return !mutuallyExclusiveArguments.contains(argument);
    }

    public boolean hasRequirementsFulfilled(Collection<Argument> arguments) {
        return arguments.containsAll(requiredArguments);
    }

    public void addRequiredArgument(Argument argument) {
        if (this.equals(argument)) {return;} // Why would anyone do this?
        if (!requiredArguments.contains(argument)) {
            requiredArguments.add(argument);
        }
    }

    public void setInclusionAction(ArgumentAction inclusionAction) {
        this.inclusionAction = inclusionAction;
    }

    public void setExclusionAction(ArgumentAction exclusionAction) {
        this.exclusionAction = exclusionAction;
    }

    public void executeStandardAction() throws ArgumentException {
        inclusionAction.execute();
    }

    public void executeNonExecutionAction() throws ArgumentException {
        exclusionAction.execute();
    }

    public String[] getAcceptedNames() {
        return acceptedNames;
    }

    public ArgumentValue[] getArgumentValues() {
        return argumentValues;
    }

    public Object getArgumentValue(int index) {
        return argumentValues[index].getValue();
    }
}
