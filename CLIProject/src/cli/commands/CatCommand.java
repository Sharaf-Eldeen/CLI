package cli.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import cli.CLIUtils;

public class CatCommand implements cli.Command {

    @Override
    public String execute(String[] args) {
        if (!CLIUtils.validateArguments(args, 2, "You should provide at least one file name")) {
            return "You should provide at least one file name";
        }

        StringBuilder result = new StringBuilder();

        for (int i = 1; i < args.length; i++) {
            File file = new File(args[i]);

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
