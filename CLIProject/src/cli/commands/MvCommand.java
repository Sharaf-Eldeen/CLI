package cli.commands;
import java.io.File;

import cli.CLIUtils;;

public class MvCommand implements cli.Command{
    @Override
    public void execute(String[] args) {
        if(!CLIUtils.validateArguments(args, 3, "Usage: mv <source> <destination>")) return;

        File src = new File(args[1]);
        File dest = new File(args[2]);

        if (!src.exists()) {
            System.out.println("Source file " + src.getName() + " does not exist.");
            return;
        }
        if(src.renameTo(dest)) {
            System.out.println("Moved/Renamed successfully.");
        }else {
            System.out.println("Failed to move/rename.");
        }
    }
}
