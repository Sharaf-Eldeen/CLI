package cli.commands;
// import cli.*;
import java.io.File;

import cli.CLIUtils;;

/**
 * MkdirCommand
 */
public class MkdirCommand implements cli.Command {
    @Override
    public void execute(String[] args) {
        if(!CLIUtils.validateArguments(args, 2, "You should provide the name of directory")) return;
    
        File directory = new File(args[1]);
        boolean isCreated = directory.mkdir();
        if(isCreated) {
            System.out.println("Directory created: " + args[1]);
        }else {
            System.out.println("Failed to create directory.");
        }
    }
}