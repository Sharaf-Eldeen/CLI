package cli.commands;
import java.util.Map;

import cli.Command;

public class HelpCommand implements cli.Command {
    private Map<String, Command> commands;
    
    public HelpCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public void execute(String[] args) {
        System.out.println("Available commands:");
        for(String command : commands.keySet()) {
            System.out.println(" - " + command);
        }
        System.out.println("Use 'exit' to quit the CLI.");
    }
    
}
