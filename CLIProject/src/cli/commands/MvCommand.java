package cli.commands;
import java.io.File;
import cli.CommandLineInterpreter;
import cli.CLIUtils;;


public class MvCommand implements cli.Command{
    private CommandLineInterpreter cli;

    public MvCommand(CommandLineInterpreter cli) {
        this.cli = cli;
    }
    @Override
    public String execute(String[] args) {
        if(!CLIUtils.validateArguments(args, 3, "Usage: mv <source> <destination>")) return "Usage: mv <source> <destination>";

        File src = new File(cli.getCurrentDirectory(), args[1]);
        File dest = new File(cli.getCurrentDirectory(), args[2]);

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
