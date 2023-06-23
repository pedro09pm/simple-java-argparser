package simple.java.argparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Pedro Marín Sanchis
 */
public class ArgumentParser {
    private static final ArrayList<Argument> arguments = new ArrayList<Argument>();
    private static String helpMessage = "";

    public static void setHelpMessage(String helpMessage) {
        ArgumentParser.helpMessage = helpMessage;
    }

    public static void addArgument(Argument argument) {
        arguments.add(argument);
    }

    public static void parseArgs(String[] args) {
        try {
            Collection<Argument> specifiedArguments = getSpecifiedArguments(args);
            specifiedArguments.forEach(argument -> {
                setValues(argument, getArgumentStringValues(argument, args));
                argument.executeStandardAction();
            });
            arguments.forEach((argument -> {
                if (!specifiedArguments.contains(argument)) {
                    argument.executeNonExecutionAction();
                }
            }));
        } catch (ArgumentException e) {
            System.out.println("[Error] "+e.getMessage());
            System.out.println(helpMessage);
            System.exit(1);
        }
    }

    private static Collection<Argument> getSpecifiedArguments(String[] args) throws ArgumentException {
        Set<Argument> specifiedArguments = new HashSet<Argument>();
        for (String s: args) {
            for (Argument argument: arguments) {
                if (Arrays.stream(argument.getAcceptedNames()).anyMatch(s::equalsIgnoreCase)) {
                    specifiedArguments.add(argument);
                }
            }
        }
        if (!isArgumentCombinationCompatible(specifiedArguments)) {
            throw new ArgumentException("Arguments are incompatible or there are unfulfilled required arguments.");
        }
        return specifiedArguments;
    }

    private static boolean isArgumentCombinationCompatible(Collection<Argument> arguments) {
        for (Argument argument: arguments) {
            if (arguments.stream().allMatch(argument::isCompatibleWith)
                && argument.hasRequirementsFulfilled(arguments)) {
                return true;
            }
        }
        return false;
    }

    private static void setValues(Argument argument, String[] stringValues) throws ArgumentException {
        if (stringValues.length != argument.getArgumentValues().length) {
            throw new ArgumentException("Not enough argument values were provided.");
        }

        for (int i = 0; i<argument.getArgumentValues().length; i++) {
            argument.getArgumentValues()[i].setValueFromString(stringValues[i]);
        }
    }

    private static String[] getArgumentStringValues(Argument argument, String[] args) {
        ArrayList<String> stringValues = new ArrayList<String>();
        Iterator<String> argsIterator = Arrays.stream(args).iterator();
        int argumentsLeft = argument.getArgumentValues().length;

        while (argsIterator.hasNext()) {
            String value = (String) argsIterator.next();
            if (Arrays.stream(argument.getAcceptedNames()).anyMatch(value::equalsIgnoreCase)) {
                while (argsIterator.hasNext() && argumentsLeft > 0) {
                    argumentsLeft--;
                    stringValues.add((String) argsIterator.next());
                }
                break;
            }
        }
        return stringValues.toArray(new String[]{});
    }

}
