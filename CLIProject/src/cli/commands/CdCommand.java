package cli.commands;

import cli.CommandLineInterpreter;
import cli.Command;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CdCommand implements Command {
    private CommandLineInterpreter cli;

    public CdCommand(CommandLineInterpreter cli) {
        this.cli = cli;
    }

    @Override
    public String execute(String[] args) {
        if (args.length < 2) {
            return "Home directory is required: " + cli.getCurrentDirectory();
        }

        String path = args[1];
        File newDir;

        // Handle ".." to go up one directory
        if (path.equals("..")) {
            File parentDir = new File(cli.getCurrentDirectory()).getParentFile();
            if (parentDir != null) {
                cli.setCurrentDirectory(parentDir.getAbsolutePath());
                return "Directory changed to: " + cli.getCurrentDirectory();
            } else {
                return "Already at the root directory.";
            }
        }

        // Determine if the path is absolute or relative
        if (Paths.get(path).isAbsolute()) {
            newDir = new File(path);
        } else {
            newDir = new File(cli.getCurrentDirectory(), path);
        }

        // Check if the directory exists
        if (newDir.isDirectory()) {
            cli.setCurrentDirectory(newDir.getAbsolutePath());
            return "Directory changed to: " + cli.getCurrentDirectory();
        } else {
            return "Invalid directory: " + path;
        }
    }
}
