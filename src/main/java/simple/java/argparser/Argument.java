package simple.java.argparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Argument {
    /**
     * Arguments that cannot be present together. Example: --client --server
     */
    private final ArrayList<Argument> mutuallyExclusiveArguments = new ArrayList<Argument>();
    /**
     * Arguments that must be present together. Example: --windowed --width 1920 --height 1080
     */
    private final ArrayList<Argument> requiredArguments = new ArrayList<Argument>();
    /**
     * Arguments names. Example: --width --w
     */
    private final String[] acceptedNames;
    private final ArgumentValue[] argumentValues;
    private ArgumentAction action;

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

    public boolean hasRequirementsFullfilled(Collection<Argument> arguments) {
        return arguments.containsAll(requiredArguments);
    }

    public void addRequiredArgument(Argument argument) {
        if (this.equals(argument)) {return;} // Why would anyone do this?
        if (!requiredArguments.contains(argument)) {
            requiredArguments.add(argument);
        }
    }

    public void setAction(ArgumentAction action) {
        this.action = action;
    }

    public void executeAction() throws ArgumentException {
        action.execute();
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
