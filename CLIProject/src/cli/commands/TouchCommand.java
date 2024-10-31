package cli.commands;

import cli.CommandLineInterpreter;
import cli.Command;
import java.io.File;
import java.io.IOException;

public class TouchCommand implements Command {
    private CommandLineInterpreter cli;
    // assign cli to the this atribute so i can keep track of the current opened dir over all the calls of the function
    public TouchCommand(CommandLineInterpreter cli) {
        this.cli = cli;
    }

    @Override
    public String execute(String[] args) {
        // checking for the arg to know if all needed args are given or there is missing(here all needed if file name or a path)
        if (args.length < 2) {
            return "Usage: touch <filename>";
        }


        // geting the path of the given file name starting from the current dir
        String del = args[1];
        File delfile;
        delfile = new File(cli.getCurrentDirectory(), del);
        try {
            File parDir = delfile.getParentFile();
            // checking if the path contains dirs not exist if there is any we will create them and check if the creation done or not
            if (parDir != null && !parDir.exists()) {
                if (!parDir.mkdirs()) {
                    return "Failed to create directories: " + parDir.getPath();
                }
            }
            // here we check if we can create the file or not ( we can create it if there is no file in the same dir have the same name else we return that the file is already exist)
            if (delfile.createNewFile()) {
                return "File created: " + delfile.getPath();
            } else {
                return "File already exists.";
            }
            // here handling the error that might happen
        } catch (IOException e) {
            return "An error occurred: " + e.getMessage();
        }
    }
}

