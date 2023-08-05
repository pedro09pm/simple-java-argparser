package simple.java.argparser;

import java.util.*;

/**
 * @author Pedro Marín Sanchis
 * The ArgumentParser class provides functionality for parsing command-line arguments and executing corresponding actions.
 * It allows defining and adding arguments, specifying their accepted names and values, and executing actions based on the provided arguments.
 */
@SuppressWarnings("unused")
public class ArgumentParser {
    private static final ArrayList<Argument> arguments = new ArrayList<>();
    private static String helpMessage = "";

    /**
     * Sets the help message that will be displayed when an error occurs during argument parsing.
     *
     * @param helpMessage The help message to be displayed.
     */
    public static void setHelpMessage(String helpMessage) {
        ArgumentParser.helpMessage = helpMessage;
    }

    /**
     * Adds an argument to the argument list.
     *
     * @param argument The argument to be added.
     */
    public static void addArgument(Argument argument) {
        arguments.add(argument);
    }

    /**
     * Parses the command-line arguments and executes corresponding actions.
     *
     * @param args The command-line arguments to be parsed.
     */
    public static void parseArgs(String[] args) {
        try {
            Collection<Argument> specifiedArguments = getSpecifiedArguments(args);
            specifiedArguments.forEach(argument -> {
                setValues(argument, getArgumentStringValues(argument, args));
                argument.executeInclusionAction();
            });
            arguments.forEach((argument -> {
                if (!specifiedArguments.contains(argument)) {
                    argument.executeExclusionAction();
                }
            }));
        } catch (ArgumentException e) {
            System.out.println("[Error] "+e.getMessage());
            System.out.println(helpMessage);
            System.exit(1);
        }
    }

    /**
     * Retrieves the specified arguments from the command-line arguments.
     *
     * @param args The command-line arguments.
     * @return A collection of specified arguments.
     * @throws ArgumentException If arguments are incompatible or there are unfulfilled required arguments.
     */
    private static Collection<Argument> getSpecifiedArguments(String[] args) throws ArgumentException {
        Set<Argument> specifiedArguments = new HashSet<>();
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

    /**
     * Checks if the combination of arguments is compatible.
     *
     * @param arguments The collection of arguments.
     * @return True if the combination of arguments is compatible, false otherwise.
     */
    private static boolean isArgumentCombinationCompatible(Collection<Argument> arguments) {
        if (arguments.stream().allMatch(Objects::isNull)) {
            return true;
        }
        for (Argument argument: arguments) {
            if (arguments.stream().allMatch(argument::isCompatibleWith)
                && argument.hasRequirementsFulfilled(arguments)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets values for the specified argument based on the provided string values.
     *
     * @param argument     The argument for which values are to be set.
     * @param stringValues The string values to be set as argument values.
     * @throws ArgumentException If the number of provided string values does not match the number of argument values.
     */
    private static void setValues(Argument argument, String[] stringValues) throws ArgumentException {
        if (stringValues.length != argument.getArgumentValues().length) {
            throw new ArgumentException("Not enough argument values were provided.");
        }

        for (int i = 0; i<argument.getArgumentValues().length; i++) {
            argument.getArgumentValues()[i].setValueFromString(stringValues[i]);
        }
    }

    /**
     * Retrieves the string values associated with the specified argument from the command-line arguments.
     *
     * @param argument The argument for which string values are to be retrieved.
     * @param args     The command-line arguments.
     * @return An array of string values associated with the argument.
     */
    private static String[] getArgumentStringValues(Argument argument, String[] args) {
        ArrayList<String> stringValues = new ArrayList<>();
        Iterator<String> argsIterator = Arrays.stream(args).iterator();
        int argumentsLeft = argument.getArgumentValues().length;

        while (argsIterator.hasNext()) {
            String value = argsIterator.next();
            if (Arrays.stream(argument.getAcceptedNames()).anyMatch(value::equalsIgnoreCase)) {
                while (argsIterator.hasNext() && argumentsLeft > 0) {
                    argumentsLeft--;
                    stringValues.add(argsIterator.next());
                }
                break;
            }
        }
        return stringValues.toArray(new String[]{});
    }

}
