package cli.commands;
import java.io.File;

import cli.CLIUtils;;

public class RmdirCommand implements cli.Command {

    private boolean removeDirectoryHelper(File file) {
        File[] allContents = file.listFiles();
        if(allContents != null) {
            for(File innerFile : allContents) {
                if(innerFile.isDirectory()) {
                    removeDirectoryHelper(innerFile);
                }
                innerFile.delete();
            }
        }
        return file.delete();
    }

    @Override
    public void execute(String[] args) {
        if(!CLIUtils.validateArguments(args, 2, "You should provide the name of directory")) return;
        
        File directory = new File(args[1]);
        if(!directory.isDirectory()) {
            System.out.println("The provided path is not a directory or does not exist.");
            return;
        }

        if (removeDirectoryHelper(directory)) {
            System.out.println("Directory removed: " + args[1]);
        } else {
            System.out.println("Failed to remove directory.");
        }
    }
    
}
