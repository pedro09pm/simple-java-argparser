import org.junit.Test;
import simple.java.argparser.Argument;
import simple.java.argparser.ArgumentActionException;
import simple.java.argparser.ArgumentParser;

import static org.junit.Assert.assertEquals;

public class SimpleJavaArgparserTester {
    @Test
    public void testArgumentExecution() {
        Argument argument = new Argument(new String[]{"--client"}, new String[]{"One", "Two"});
        argument.setAction(() -> {
            for (String s: argument.getArgumentValues()) {
                System.out.println(s);
            }
            throw new ArgumentActionException();
        });
        ArgumentParser.addArgument(argument);
        ArgumentParser.parseArgs(new String[]{"Amongus"});
        assertEquals(1,1);
    }

    public static void method() {
        System.out.println("I'm a method :)");
    }

}
