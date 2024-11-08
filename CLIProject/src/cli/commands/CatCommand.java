package cli.commands;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import cli.CommandLineInterpreter;

public class CatCommand implements cli.Command {
    private CommandLineInterpreter cli;

    public CatCommand(CommandLineInterpreter cli) {
        this.cli = cli;
    }

    @Override
    public String execute(String[] args) {
    
        if (args.length == 1) {     
            Scanner scanner = new Scanner(System.in);
            return scanner.nextLine();
        }


        StringBuilder result = new StringBuilder();

        for (int i = 1; i < args.length; i++) {
            File file = new File(cli.getCurrentDirectory(), args[i]);

            if (!file.exists()) {
                result.append("File does not exist: ").append(args[i]).append("\n");
                continue;
            }

            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    result.append(scanner.nextLine()).append("\n");
                }
            } catch (FileNotFoundException e) {
                result.append("Error reading file: ").append(args[i]).append("\n");
            }
        }

        return result.toString();
    }
}
