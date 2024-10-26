package cli.commands;
import java.io.File;
import cli.CLIUtils;;

public class MvCommand implements cli.Command{
    @Override
    public String execute(String[] args) {
        if(!CLIUtils.validateArguments(args, 3, "Usage: mv <source> <destination>")) return "Usage: mv <source> <destination>";

        File src = new File(args[1]);
        File dest = new File(args[2]);

        if (!src.exists()) {
            return("Source file " + src.getName() + " does not exist.");
           
        }
        if(src.renameTo(dest)) {
           return("Moved/Renamed successfully.");
        }else {
           return("Failed to move/rename.");
        }
    }
}
