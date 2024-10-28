package cli.commands;

import cli.CommandLineInterpreter;
import cli.IOperation;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteOperation implements IOperation {
    private CommandLineInterpreter cli;

    public WriteOperation(CommandLineInterpreter cli) {
        this.cli = cli;
    }

    @Override
    public void execute(String content, String target) {
     
        File targetFile = new File(cli.getCurrentDirectory(), target);
        
        try (FileWriter writer = new FileWriter(targetFile, false)) {
            writer.write(content);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + targetFile.getAbsolutePath());
        }
    }
}
