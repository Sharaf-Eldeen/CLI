package cli.commands;
import java.io.File;

import cli.CLIUtils;;

/**
 * RmCommand
 */
public class RmCommand implements cli.Command {
    @Override
    public void execute(String[] args) {
        if(!CLIUtils.validateArguments(args, 2, "Usage: rm <file_name>")) return;
        
        File file = new File(args[1]);
        if(file.isDirectory()) return;
        if (file.delete()) {
            System.out.println("File removed: " + args[1]);
        } else {
            System.out.println("Failed to remove file.");
        }
    }
}