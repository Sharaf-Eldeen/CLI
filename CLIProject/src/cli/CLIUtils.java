package cli;

import java.io.File;

public class CLIUtils {

    private static void printError(String message) {
        System.out.println("Error: " + message);
    }

    public static boolean validateArguments(String[] args, int requiredLength, String message) {
        if (args.length < requiredLength) {
            printError(message);
            return false;
        }
        return true;
    }

    public static File resolvePath(String currentDirectory, String path) {
        File file = new File(path);

        return file.isAbsolute() ? file : new File(currentDirectory, path);
    }
}
