package cli.commands;

import cli.CommandLineInterpreter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TouchCommandTest {
    private TouchCommand touchCommand;
    private CommandLineInterpreter cli;
    private Path testDir;

    @Before
    public void setUp() throws IOException {
        cli = new CommandLineInterpreter();
        touchCommand = new TouchCommand(cli);

        testDir = Files.createTempDirectory("testDir");
        cli.setCurrentDirectory(testDir.toString());
    }

    @After
    public void tearDown() throws IOException {
        Files.walk(testDir)
             .map(Path::toFile)
             .forEach(File::delete);
    }

    @Test
    public void testFileCreation() {
        String fileName = "newFile.txt";
        String output = touchCommand.execute(new String[]{"touch", fileName});
        
        assertTrue("File should be created", Files.exists(testDir.resolve(fileName)));
        assertEquals("File created: " + testDir.resolve(fileName), output);
    }

    @Test
    public void testFileAlreadyExists() throws IOException {
        Path existingFile = Files.createFile(testDir.resolve("existingFile.txt"));
        
        String output = touchCommand.execute(new String[]{"touch", "existingFile.txt"});
        
        assertEquals("File already exists.", output);
    }

    @Test
    public void testMissingFileNameArgument() {
        String output = touchCommand.execute(new String[]{"touch"});
        
        assertEquals("Usage: touch <filename>", output);
    }

    @Test
    public void testDirectoryCreationAndFileCreation() {
        String nestedFilePath = "nestedDir/newFile.txt";
        String output = touchCommand.execute(new String[]{"touch", nestedFilePath});
        
        assertTrue("Nested directory should be created", Files.exists(testDir.resolve("nestedDir")));
        assertTrue("File in nested directory should be created", Files.exists(testDir.resolve(nestedFilePath)));
        assertEquals("File created: " + testDir.resolve(nestedFilePath), output);
    }
}

