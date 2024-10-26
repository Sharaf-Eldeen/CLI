package cli.commands;
import java.io.File;
import cli.CLIUtils;;

public class MkdirCommand implements cli.Command {
    @Override
    public String execute(String[] args) {
        if(!CLIUtils.validateArguments(args, 2, "You should provide the name of directory")) return "You should provide the name of directory";
    
        File directory = new File(args[1]);
        boolean isCreated = directory.mkdir();
        if(isCreated) {
            return("Directory created: " + args[1]);
        }else {
            return("Failed to create directory.");
        }
    }
}