package cli.Operations;

import cli.CommandLineInterpreter;
import cli.commands.WriteOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WriteOperationTest {
    private WriteOperation writeOperation;
    private File testFile;

    @Before
    public void setUp() {
        writeOperation = new WriteOperation(new CommandLineInterpreter());
        testFile = new File("testWriteFile.txt");
    }

    @After
    public void takeDown() {
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testExecuteWritesToNewFile() throws IOException {
        String contentToWrite = "This is new content.";
        writeOperation.execute(contentToWrite, "testWriteFile.txt");

        assertTrue(testFile.exists());
        assertEquals(contentToWrite, Files.readString(Path.of("testWriteFile.txt")));
    }

    @Test
    public void testExecuteOverwritesExistingFile() throws IOException {
        writeOperation.execute("Initial content.", "testWriteFile.txt");
        String newContent = "Overwritten content.";
        writeOperation.execute(newContent, "testWriteFile.txt");

        assertEquals(newContent, Files.readString(Path.of("testWriteFile.txt")));
    }

    @Test
    public void testExecuteWithEmptyContent() throws IOException {
        writeOperation.execute("Some content.", "testWriteFile.txt");
        writeOperation.execute("", "testWriteFile.txt");

        assertEquals("", Files.readString(Path.of("testWriteFile.txt")));
    }
}
