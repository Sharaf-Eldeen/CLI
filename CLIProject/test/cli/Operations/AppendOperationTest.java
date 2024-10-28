package cli.Operations;

import cli.commands.AppendOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AppendOperationTest {
    private AppendOperation appendOperation;
    private File testFile;

    @Before
    public void setUp() throws IOException {
        appendOperation = new AppendOperation();
        testFile = new File("testAppendFile.txt");
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Initial content.\n");
        }
    }

    @After
    public void takeDown() {
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testExecuteAppendsToFile() throws IOException {
        String contentToAppend = "Appended content.";
        appendOperation.execute(contentToAppend, "testAppendFile.txt");

        String expectedContent = "Initial content.\nAppended content.";
        assertEquals(expectedContent, Files.readString(Path.of("testAppendFile.txt")));
    }

    @Test
    public void testExecuteCreatesNewFileIfNotExists() throws IOException {
        String newFileName = "newTestFile.txt";
        File newFile = new File(newFileName);

        try {
            String contentToAppend = "Content for new file.";
            appendOperation.execute(contentToAppend, newFileName);

            assertTrue(newFile.exists());
            assertEquals(contentToAppend, Files.readString(Path.of(newFileName)));
        } finally {
            if (newFile.exists()) {
                newFile.delete();
            }
        }
    }

    @Test
    public void testExecuteWithEmptyContent() throws IOException {
        String emptyContent = "";
        appendOperation.execute(emptyContent, "testAppendFile.txt");

        String expectedContent = "Initial content.\n";
        assertEquals(expectedContent, Files.readString(Path.of("testAppendFile.txt")));
    }
}
