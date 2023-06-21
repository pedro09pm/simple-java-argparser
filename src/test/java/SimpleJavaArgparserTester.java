import org.junit.Test;
import simple.java.argparser.*;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class SimpleJavaArgparserTester {
    @Test
    public void testArgumentExecution() {
        Argument argument = new Argument(new String[]{"--rectangle"}, new ArgumentValue[]{new ArgumentValue(ArgumentValueType.INTEGER)});
        argument.setAction(() -> {
            System.out.println(argument);
            Arrays.stream(argument.getArgumentValues()).forEach((e) -> System.out.println(e.getValue()));
        });

        Argument argument2 = new Argument(new String[]{"--triangle"}, new ArgumentValue[]{new ArgumentValue(ArgumentValueType.STRING)});
        argument2.setAction(() -> {
            System.out.println(argument2);
            Arrays.stream(argument2.getArgumentValues()).forEach((e) -> System.out.println(e.getValue()));
        });


        ArgumentParser.addArgument(argument);
        ArgumentParser.addArgument(argument2);

        ArgumentParser.parseArgs(new String[]{"--triangle", "SUSPICIOUS", "--rectangle", "50"});
        assertEquals(1,1);
    }

    public static void method() {
        System.out.println("I'm a method :)");
    }

}
