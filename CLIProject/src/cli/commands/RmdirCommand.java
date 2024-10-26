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
    public String execute(String[] args) {
        if(!CLIUtils.validateArguments(args, 2, "You should provide the name of directory")) return "You should provide the name of directory";
        
        File directory = new File(args[1]);
        if(!directory.isDirectory()) {
            return("The provided path is not a directory or does not exist.");
            
        }

        if (removeDirectoryHelper(directory)) {
            return("Directory removed: " + args[1]);
        } else {
            return("Failed to remove directory.");
        }
    }
    
}
