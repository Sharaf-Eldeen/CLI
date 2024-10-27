package cli;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import cli.commands.*;

public class CommandLineInterpreter {
    private Map<String, Command> commands = new HashMap<>();
    private Map<String, IOperation> operations = new HashMap<>();

    private void registerCommands() {
        commands.put("mkdir", new MkdirCommand());
        commands.put("mv", new MvCommand());
        commands.put("rm", new RmCommand());
        commands.put("rmdir", new RmdirCommand());
        commands.put("cat", new CatCommand());
        commands.put("echo", new EchoCommand());
        commands.put("ls", new LsCommand());
     
        commands.put("help", new HelpCommand(commands, operations));
    }

    private void registerOperations() {
        operations.put(">", new WriteOperation());
        operations.put(">>", new AppendOperation());
        operations.put("|", new PipeOperation(commands));
    }


    private void executeCommand(String[] args) {
        if (args.length >= 3) {
            String operator = args[args.length - 2];
            if (operator.equals(">") || operator.equals(">>") || operator.equals("|")) {
                HandleRequest(args);
                return;
            }
        }
    
        String commandName = args[0];
        Command command = commands.get(commandName);
    
        if (command != null) {
            String[] commandArgs = new String[args.length];
            System.arraycopy(args, 0, commandArgs, 0, args.length);
            String result = command.execute(commandArgs);
            if (result != null && !result.isEmpty()) {
                System.out.println(result);
            }
        } else {
            System.out.println("Unknown Command: " + commandName);
        }
    }
    
   
    

    private void HandleRequest(String[] args) {
        String commandName = args[0];
        Command command = commands.get(commandName);
        if (command == null) {
            System.out.println("Unknown Command: " + commandName);
            return;
        }
    
        String[] commandArgs = new String[args.length - 2];
        System.arraycopy(args, 0, commandArgs, 0, args.length - 2);
    
        String result = command.execute(commandArgs);
        if (result == null || result.isEmpty()) {
            return;
        }
    
        String operator = args[args.length - 2];
        String target = args[args.length - 1];
    
        IOperation operation = operations.get(operator);
        if (operation != null) {
            operation.execute(result, target);
        } else {
            System.out.println("Invalid operator: " + operator);
        }
    }
    
    
 
    

    public CommandLineInterpreter() {
         registerOperations();
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
