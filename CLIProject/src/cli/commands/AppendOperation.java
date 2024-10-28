package cli.commands;

import cli.CommandLineInterpreter;
import cli.IOperation;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AppendOperation implements IOperation {
    private CommandLineInterpreter cli;

    public AppendOperation(CommandLineInterpreter cli) {
        this.cli = cli;
    }

    @Override
    public void execute(String content, String target) {
        File targetFile = new File(cli.getCurrentDirectory(), target);

        try (FileWriter writer = new FileWriter(targetFile, true)) {  
            writer.write(content);
        } catch (IOException e) {
            System.out.println("Error appending to file: " + targetFile.getAbsolutePath());
        }
    }
}
