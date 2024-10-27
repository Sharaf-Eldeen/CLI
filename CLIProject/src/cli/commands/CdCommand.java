package cli.commands;
import cli.Command;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CdCommand implements Command{
    private static String current_directory = System.getProperty("user.dir");
    private static final String home_directory=Paths.get("").toAbsolutePath().toString();

    @Override
    public String  execute(String[] args){
        if (args.length < 2) {
            return ("Home directory is required :"+home_directory);
        }
        String Path = args[1];
        if (Path.equals("..")) {
            File parentDir = new File(current_directory).getParentFile();
            if (parentDir != null) {
                current_directory = parentDir.getAbsolutePath();
                System.setProperty("user.dir", current_directory);
                return "Directory changed to: " + current_directory;
            } else {
                return "Already at the root directory.";
            }
        }

        File newDir = new File(Path);

        if (newDir.isDirectory()) {
            current_directory = newDir.getAbsolutePath();
            System.setProperty("user.dir", current_directory);
            return "Directory changed to: " + current_directory;
        } else {
            return "Invalid directory: " + Path;
        }
    }
}
