package cli.commands;

import cli.IOperation;
import cli.Command;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class PipeOperation implements IOperation {
    private Map<String, Command> commands;

   
    private static final Set<String> allowedCommands = new HashSet<>(Set.of(
            "echo", "cat"
    ));

    public PipeOperation(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public void execute(String content, String target) {
        if (!allowedCommands.contains(target)) {
            System.out.println("Command not allowed in pipe operation: " + target);
            return;
        }

        Command nextCommand = commands.get(target);
        if (nextCommand != null) {
            String[] nextArgs = new String[]{target, content};
            String nextResult = nextCommand.execute(nextArgs);
            if (nextResult != null && !nextResult.isEmpty()) {
                System.out.println(nextResult);
            }
        } else {
            System.out.println("Unknown Command: " + target);
        }
    }
}
