package cli.commands;
import cli.IOperation;
import java.io.FileWriter;
import java.io.IOException;

public class WriteOperation implements IOperation {
    @Override
    public void execute(String content, String target) {
        try (FileWriter writer = new FileWriter(target, false)) {
            writer.write(content);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + target);
        }
    }
}
