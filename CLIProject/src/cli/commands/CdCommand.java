package cli.commands;

import cli.CommandLineInterpreter;
import cli.Command;
import java.io.File;
//import java.nio.file.Path;
import java.nio.file.Paths;

public class CdCommand implements Command {
    private CommandLineInterpreter cli;
    private static final String home_directory=Paths.get("").toAbsolutePath().toString();
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

        if (path.equals("..")) {
            if((cli.getCurrentDirectory()).equals(home_directory)) return "Already at the root directory.";
            File parentDir = new File(cli.getCurrentDirectory()).getParentFile();
            if (parentDir != null) {
                cli.setCurrentDirectory(parentDir.getAbsolutePath());
                return "Directory changed to: " + cli.getCurrentDirectory();
            }
        }

        if (Paths.get(path).isAbsolute()) {
            newDir = new File(path);
        } else {
            newDir = new File(cli.getCurrentDirectory(), path);
        }

        if (newDir.isDirectory()) {
            cli.setCurrentDirectory(newDir.getAbsolutePath());
            return "Directory changed to: " + cli.getCurrentDirectory();
        } else {
            return "Invalid directory: " + path;
        }
    }
}