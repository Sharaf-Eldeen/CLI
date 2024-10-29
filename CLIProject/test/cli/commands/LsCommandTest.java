package cli.commands;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cli.CommandLineInterpreter;

public class LsCommandTest {
    private LsCommand lsCommand;
    private CommandLineInterpreter cli;
    private Path testDir;
    private Path hiddenFile;

    @Before
    public void setUp() throws IOException {
        cli = new CommandLineInterpreter();
        lsCommand = new LsCommand(cli);
        testDir = Files.createTempDirectory("testDir");
        cli.setCurrentDirectory(testDir.toString());
        hiddenFile = Files.createFile(testDir.resolve(".hiddenFile"));
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
    public void testExecuteCurrentDirectory() throws IOException {
        Path sampleFile = Files.createFile(testDir.resolve("sampleFile.txt"));
        String output = lsCommand.execute(new String[]{"ls"});
        assertTrue("Output should list the files and folders in the current directory", output.contains("sampleFile.txt"));
    }

    @Test
    public void testExecuteHiddenFiles() {
        String output = lsCommand.execute(new String[]{"ls", "-a"});
        assertTrue("Output should include hidden files when '-a' flag is used", output.contains(".hiddenFile"));
    }

    @Test
    public void testExecuteReverseOrder() throws IOException {
        Files.createFile(testDir.resolve("Afile"));
        Files.createFile(testDir.resolve("Bfile"));

        String output = lsCommand.execute(new String[]{"ls", "-r"});
        String[] lines = output.split("\\n");

        assertTrue("Output should list files in reverse alphabetical order", 
                   lines[0].contains("Bfile") && lines[1].contains("Afile"));
    }

    @Test
    public void testExecuteSpecificDirectory() throws IOException {
        Path srcDir = Files.createDirectory(testDir.resolve("src"));
        assertTrue("The 'src' directory should be created", Files.exists(srcDir));

        Path testFile = Files.createFile(srcDir.resolve("testFile.txt"));
        assertTrue("The 'testFile.txt' file should be created", Files.exists(testFile));

        String output = lsCommand.execute(new String[]{"ls", srcDir.toAbsolutePath().toString()});
    
        assertTrue("Output should list the contents of the specified 'src' directory", output.contains("testFile.txt"));
    }

    @Test
    public void testExecuteNonexistentDirectory() {
        String output = lsCommand.execute(new String[]{"ls", "nonexistentDir"});
        assertEquals("Output should indicate an error for a nonexistent directory",
                "Cannot access directory: nonexistentDir", output);
    }
}


