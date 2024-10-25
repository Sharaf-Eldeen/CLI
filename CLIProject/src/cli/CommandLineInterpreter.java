package cli;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import cli.commands.*;

public class CommandLineInterpreter {
    private Map<String, Command> commands = new HashMap<>();

    private void registerCommands() {
        commands.put("mkdir", new MkdirCommand());
        commands.put("mv", new MvCommand());
        commands.put("rm", new RmCommand());
        commands.put("rmdir", new RmdirCommand());
        // register the remaining commands here Commands
        commands.put("help", new HelpCommand(commands));

    };

    private void executeCommand(String[] args) {
        String commandName = args[0];
        Command command = commands.get(commandName);
        if(command != null) {
            command.execute(args);
        }else{
            System.out.println("Unknown Command: " + commandName);
        }
    }
    public CommandLineInterpreter() {
        registerCommands();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the CLI. Type 'help' to see available commands.");

        while (true) {
            System.out.print(">");
            String input = scanner.nextLine();
            String[] commandParts = input.trim().split("\\s+");

            if (commandParts[0].equalsIgnoreCase("exit")) {
                System.out.println("Exiting CLI...");
                break;
            }

            executeCommand(commandParts);
        }
        scanner.close();
    }

    public static void main(String[] args) {
        CommandLineInterpreter cli = new CommandLineInterpreter();
        cli.start();
    }

}