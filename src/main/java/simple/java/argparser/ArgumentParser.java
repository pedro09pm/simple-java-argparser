package simple.java.argparser;

import java.util.ArrayList;

public class ArgumentParser {
    private static ArrayList<Argument> arguments = new ArrayList<Argument>();
    private static String helpMessage = "A manual/help entry has not been set.";

    public static void setHelpMessage(String helpMessage) {
        ArgumentParser.helpMessage = helpMessage;
    }

    public static void addArgument(Argument argument) {
        arguments.add(argument);
    }

    public static void parseArgs(String[] args) {
        for (Argument argument: arguments) {
            try {
                argument.executeAction();
            } catch (ArgumentActionException e) {
                System.out.println(helpMessage);
                System.exit(1);
            }
        }
    }
}
