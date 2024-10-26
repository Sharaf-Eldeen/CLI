package cli.commands;

import java.util.Map;
import cli.Command;
import cli.IOperation;

public class HelpCommand implements Command {
    private Map<String, Command> commands;
    private Map<String, IOperation> operations;

    public HelpCommand(Map<String, Command> commands, Map<String, IOperation> operations) {
        this.commands = commands;
        this.operations = operations;
    }

    @Override
    public String execute(String[] args) {
        StringBuilder helpMessage = new StringBuilder("Available Commands:\n");
        for (String command : commands.keySet()) {
            helpMessage.append(" - ").append(command).append("\n");
        }
        helpMessage.append("\nAvailable Operations:\n");
        for (String operation : operations.keySet()) {
            helpMessage.append(" - ").append(operation).append("\n");
        }
        helpMessage.append("\nUse 'exit' to quit the CLI.");
        System.out.println(helpMessage.toString());
        return helpMessage.toString();
    }
}
