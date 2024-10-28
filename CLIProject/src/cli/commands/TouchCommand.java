package cli.commands;

import cli.CommandLineInterpreter;
import cli.Command;
import java.io.File;
import java.io.IOException;

public class TouchCommand implements Command {
    private CommandLineInterpreter cli;

    public TouchCommand(CommandLineInterpreter cli) {
        this.cli = cli;
    }

    @Override
    public String execute(String[] args) {
        if (args.length < 2) {
            return "Usage: touch <filename>";
        }

        String fileName = args[1];
        File file;
        file = new File(cli.getCurrentDirectory(), fileName);
        try {
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                if (!parentDir.mkdirs()) {
                    return "Failed to create directories: " + parentDir.getPath();
                }
            }

            if (file.createNewFile()) {
                return "File created: " + file.getPath();
            } else {
                return "File already exists.";
            }
        } catch (IOException e) {
            return "An error occurred: " + e.getMessage();
        }
    }
}

