package cli.commands;

import cli.CommandLineInterpreter;

import java.io.File;
import java.util.Arrays;

public class LsCommand implements cli.Command {
    private CommandLineInterpreter cli;

    public LsCommand(CommandLineInterpreter cli) {
        this.cli = cli;
    }

    @Override
    public String execute(String[] args) {
        File dir;
        if (args.length > 1 && !args[1].startsWith("-")) {
            dir = new File(args[1]);
        } else {
            dir = new File(cli.getCurrentDirectory());
        }

        if (!dir.isDirectory()) {
            return "Cannot access directory: " + dir.getPath();
        }

        File[] files = dir.listFiles();
        if (files == null) {
            return "Cannot access directory contents.";
        }



        boolean all = Arrays.asList(args).contains("-a");
        boolean reversed = Arrays.asList(args).contains("-r");

        StringBuilder ret = new StringBuilder();
        Arrays.stream(files)
                .filter(file -> all ||! file.isHidden())
                .sorted((f1, f2) -> reversed ? f2.getName().compareTo(f1.getName()) : f1.getName().compareTo(f2.getName()))
                .forEach(file -> {

                    if (file.isDirectory()) {
                        ret.append("Directory: ");
                    } else {
                        ret.append("File: ");
                    }
                    ret.append(file.getName()).append("\n");
                });

        return ret.toString();
    }
}
