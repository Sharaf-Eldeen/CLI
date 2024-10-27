package cli.commands;
import cli.IOperation;


import java.io.FileWriter;
import java.io.IOException;


public class AppendOperation implements IOperation {
    @Override
    public void execute(String content, String target) {
        try (FileWriter writer = new FileWriter(target, true)) {
            writer.write(content);
        } catch (IOException e) {
            System.out.println("Error appending to file: " + target);
        }
    }
}
