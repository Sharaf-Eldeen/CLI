package cli.commands;


import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cli.CommandLineInterpreter;


public class PwdCommandTest {
    private PwdCommand pwdCommand;
    private CommandLineInterpreter cli;
    private Path testDir;

    @Before
    public void setUp() throws IOException {
        cli = new CommandLineInterpreter();
        pwdCommand = new PwdCommand(cli);
        testDir = Files.createTempDirectory("testDir");
        cli.setCurrentDirectory(testDir.toString());
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
    public void testExecuteCurrentDirectory() {
        String output = pwdCommand.execute(new String[]{});
        assertEquals("Output should match the current directory", testDir.toAbsolutePath().toString(), output);
    }
}
