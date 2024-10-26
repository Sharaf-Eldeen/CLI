package cli.commands;
import java.io.File;

import cli.CLIUtils;;


public class RmCommand implements cli.Command {
    @Override
    public String execute(String[] args) {
        if(!CLIUtils.validateArguments(args, 2, "Usage: rm <file_name>")) return "Usage: rm <file_name>";
        
        File file = new File(args[1]);
        if(file.isDirectory()) return "";
        if (file.delete()) {
            return("File removed: " + args[1]);
        } else {
            return("Failed to remove file.");
        }
    }
}