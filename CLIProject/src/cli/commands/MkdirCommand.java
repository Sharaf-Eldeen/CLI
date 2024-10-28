package cli.commands;
import java.io.File;
import cli.CLIUtils;
import cli.CommandLineInterpreter;;

public class MkdirCommand implements cli.Command {
    private CommandLineInterpreter cli;

    public MkdirCommand(CommandLineInterpreter cli) {
        this.cli = cli;
    }

    @Override
    public String execute(String[] args) {
        if(!CLIUtils.validateArguments(args, 2, "You should provide the name of directory")) return "You should provide the name of directory";

        File directory = CLIUtils.resolvePath(cli.getCurrentDirectory(), args[1]);
        boolean isCreated = directory.mkdir();
        if(isCreated) {
            return("Directory created: " + args[1]);
        }else {
            return("Failed to create directory.");
        }
    }
}