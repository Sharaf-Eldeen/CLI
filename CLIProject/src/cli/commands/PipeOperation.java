package cli.commands;
import cli.IOperation;
import cli.Command;
import java.util.Map;

public class PipeOperation implements IOperation {
    private Map<String, Command> commands;

    public PipeOperation(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public void execute(String content, String target) {
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
