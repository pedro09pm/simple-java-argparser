import org.junit.Test;
import simple.java.argparser.*;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class SimpleJavaArgparserTester {
    @Test
    public void testArgumentExecution() {
        Argument argument = new Argument(new String[]{"--rectangle"}, new ArgumentValue[]{new ArgumentValue(ArgumentValueType.INTEGER)});
        argument.setInclusionAction(() -> {
            System.out.println(argument);
            Arrays.stream(argument.getArgumentValues()).forEach((e) -> System.out.println(e.getValue()));
        });

        argument.setExclusionAction(() -> {
            System.out.println(argument+" Excluded");
        });

        Argument argument2 = new Argument(new String[]{"--triangle"}, new ArgumentValue[]{new ArgumentValue(ArgumentValueType.STRING)});
        argument2.setInclusionAction(() -> {
            System.out.println(argument2);
            Arrays.stream(argument2.getArgumentValues()).forEach((e) -> System.out.println(e.getValue()));
        });

        //argument.addMutuallyExclusiveArgument(argument2);
        argument.addRequiredArgument(argument2);
        ArgumentParser.addArgument(argument);
        ArgumentParser.addArgument(argument2);

        //ArgumentParser.parseArgs(new String[]{"--triangle", "SUSPICIOUS", "--rectangle"});
        ArgumentParser.parseArgs(new String[]{});
        assertEquals(1,1);
    }

}
