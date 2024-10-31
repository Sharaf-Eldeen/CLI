package cli.commands;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cli.CommandLineInterpreter;

public class CdCommandTest {
    private CdCommand cdCommand;
    private CommandLineInterpreter cli;
    private Path testDir;
    private Path subDir;
    private static final String home_directory=System.getProperty("user.home");

    @Before
    public void setUp() throws IOException {
        cli = new CommandLineInterpreter();
        cdCommand = new CdCommand(cli);
        testDir = Files.createTempDirectory("testDir");
        cli.setCurrentDirectory(testDir.toString());
        subDir = Files.createDirectory(testDir.resolve("subDir"));
    }

    @After
    public void tearDown() throws IOException {
        deleteRecursively(testDir.toFile());
    }

    private void deleteRecursively(File file) throws IOException {
        if (file.isDirectory()) {
            for (File subFile : file.listFiles()) {
                deleteRecursively(subFile);
            }
        }
        file.delete();
    }

    @Test
    public void testExecuteChangeToSubDirectory() {
        String output = cdCommand.execute(new String[]{"cd", "subDir"});
        assertTrue("Should change to subDir", output.contains("Directory changed to: " + subDir.toAbsolutePath()));
        assertEquals("Current directory should be subDir", cli.getCurrentDirectory(), subDir.toAbsolutePath().toString());
    }

    @Test
    public void testExecuteChangeToParentDirectory() throws IOException {
        Files.createFile(testDir.resolve("testFile.txt"));
        cdCommand.execute(new String[]{"cd", "subDir"}); 
        String output = cdCommand.execute(new String[]{"cd", ".."});
        assertTrue("Should change to parent directory", output.contains("Directory changed to: " + testDir.toAbsolutePath()));
        assertEquals("Current directory should be testDir", cli.getCurrentDirectory(), testDir.toAbsolutePath().toString());
    }

    @Test
    public void testExecuteChangeToNonexistentDirectory() {
        String output = cdCommand.execute(new String[]{"cd", "nonexistentDir"});
        assertEquals("Should indicate invalid directory", "Invalid directory: nonexistentDir", output);
    }

    @Test
    public void testExecuteWithNoArguments() {
        String output = cdCommand.execute(new String[]{"cd"});
        assertTrue("Should indicate that a directory is required", output.contains("Home directory is required: " + testDir.toAbsolutePath()));
    }

    @Test
    public void testExecuteChangeToRootDirectory() {
        cli.setCurrentDirectory(home_directory);
        String output = cdCommand.execute(new String[]{"cd", ".."});
        assertTrue("Should indicate already at root directory", output.contains("Already at the root directory."));
    } 
    
}
