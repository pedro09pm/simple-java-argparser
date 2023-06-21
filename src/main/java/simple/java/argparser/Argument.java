package simple.java.argparser;

import java.util.ArrayList;

public class Argument{
    private final ArrayList<Argument> mutuallyExclusiveArguments = new ArrayList<Argument>();
    private String[] acceptedNames;
    private int expectedArgumentValueCount;
    private String[] argumentValues;
    private ArgumentAction action;

    public Argument(String[] acceptedNames, int expectedArgumentCount) {
        this.acceptedNames = acceptedNames;
        this.expectedArgumentValueCount = expectedArgumentCount;
    }

    public void addMutuallyExclusiveArgument(Argument argument) {
        if (this.equals(argument)) {return;} // Argument cannot be exclusive of itself...
        if (!mutuallyExclusiveArguments.contains(argument)) {
            mutuallyExclusiveArguments.add(argument);
            argument.addMutuallyExclusiveArgument(this);
        }
    }

    public void setAction(ArgumentAction action) {
        this.action = action;
    }

    public void executeAction() throws ArgumentActionException {
        action.execute();
    }

    public void setArgumentValues(String[] argumentValues) {
        this.argumentValues = argumentValues;
    }

    public String[] getAcceptedNames() {
        return acceptedNames;
    }

    public int getExpectedArgumentValueCount() {
        return expectedArgumentValueCount;
    }

    public String[] getArgumentValues() {
        return argumentValues;
    }
}
