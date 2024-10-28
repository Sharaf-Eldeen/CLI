package cli.Operations;
import cli.commands.CatCommand;
import org.junit.Test;
import cli.Command;
import cli.CommandLineInterpreter;

import org.junit.After;
import org.junit.Before;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import java.nio.file.Files;
import java.nio.file.Path;


public class PipeOperationTest {
    private Map<String, Command> commands;
    private File testFile;

    @Before
    public void setUp() throws IOException {
        commands = new HashMap<>();
        commands.put("cat", new CatCommand(new CommandLineInterpreter()));
        testFile = new File("testCatFile.txt");
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("File content for cat.\n");
        }
    }

    @After
    public void takeDown() {
        if (testFile.exists()) {
            testFile.delete();
        }
    }


    @Test
    public void testExecuteWithCatCommand() throws IOException {
        String fileContent = Files.readString(Path.of("testCatFile.txt"));
        assertEquals("File content for cat.\n", fileContent);
    }
 
}
