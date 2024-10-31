package cli.commands;

import cli.CommandLineInterpreter;

import java.io.File;
import java.util.Arrays;

public class LsCommand implements cli.Command {
    private CommandLineInterpreter cli;
    // assign cli to the this atribute so i can keep track of the current opened dir over all the calls of the function
    public LsCommand(CommandLineInterpreter cli) {
        this.cli = cli;
    }

    @Override
    public String execute(String[] args) {
        // first thing checking for the arguments to determine which one i will do from those four 
        // ( ls   ls+ path    ls -a   ls -r)
        File currdir;
        if (args.length > 1 && !args[1].startsWith("-")) {
            currdir = new File(args[1]);
            //here if there is args but not start with - means not ls -a or ls -r   so i will do ls +path and assign curr dir to the given path
        } else {
            currdir = new File(cli.getCurrentDirectory());
            // else the currdir will have the path of the current open dir
        }
        // if the curr dir wrong it will return here and also i make a test for this point
        if (!currdir.isDirectory()) {
            return "Cannot access directory: " + currdir.getPath();
        }
        // if the curr dir is correct then i will make array(curr files) and get all the files and dirs insead the currdir
        File[] currfiles = currdir.listFiles();
        // if the array is empty or null i will terminate the function and also there is a tset for this point
        if (currfiles == null) {
            return "Cannot access directory contents.";
        }


        // i will store all file with hidden ones in case the arg was -a  and i will store the files with reversed order incase if ls -r
        boolean all = Arrays.asList(args).contains("-a");
        boolean rev = Arrays.asList(args).contains("-r");
        

        // as the function type is tring so i make a string to store the output in it
        StringBuilder ret = new StringBuilder();
        Arrays.stream(currfiles)
                .filter(file -> all ||! file.isHidden())
                .sorted((f1, f2) -> rev ? f2.getName().compareTo(f1.getName()) : f1.getName().compareTo(f2.getName()))
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
