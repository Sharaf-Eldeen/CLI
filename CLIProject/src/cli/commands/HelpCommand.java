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
        helpMessage.append(" - cd: Change the current directory.\n");
        helpMessage.append(" - ls: List files and directories in the current directory.\n");
        helpMessage.append(" - pwd: Print the current working directory.\n");
        helpMessage.append(" - mkdir: Create a new directory.\n");
        helpMessage.append(" - rmdir: Remove an empty directory.\n");
        helpMessage.append(" - touch: Create a new file or update the timestamp of an existing file.\n");
        helpMessage.append(" - cat: Concatenate and display file contents.\n");
        helpMessage.append(" - echo: Display a line of text or variables.\n");
        helpMessage.append(" - mv: Move or rename files and directories.\n");
        helpMessage.append(" - help: Display help information for commands.\n");
        helpMessage.append(" Available Operations\n");
        helpMessage.append(" - >> : Redirect operator used to append output to a file.\n");
        helpMessage.append(" - | : Pipe operator, used to pass the output of one command as input to another.\n");
        helpMessage.append(" - > : Redirect operator, used to send output to a file, overwriting the file if it exists.\n");
        helpMessage.append("\nUse 'exit' to quit the CLI.");
        System.out.println(helpMessage.toString());
        return helpMessage.toString();
    }
}
