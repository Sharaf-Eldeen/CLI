package cli.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cli.CommandLineInterpreter;

// import cli.commands.RmCommand;

public class RmCommandTest {
    
    private RmCommand rmCommand;

    @Before
    public void setUp() throws IOException {
        rmCommand = new RmCommand(new CommandLineInterpreter());

        File file = new File("testFile.txt");
        assertTrue(file.createNewFile());

        File dir = new File("testDir");
        assertTrue(dir.mkdir());
    }

    @After
    public void tearDown() {
        File file = new File("testFile.txt");
        file.delete();

        File dir = new File("testDir");
        dir.delete();
    }

    @Test
    public void testExecuteDeletesFileSuccessfully() {
        String[] args = {"rm", "testFile.txt"};
        
        rmCommand.execute(args);

        File file = new File("testFile.txt");

        assertFalse(file.exists());
    }

    @Test
    public void testExecuteFailsIfFileDoesNotExist() {
        String[] args = {"rm", "nonExistentFile.txt"};
        
        rmCommand.execute(args);
    }

    @Test
    public void testExecuteFailsIfDirectoryProvidedInsteadOfFile() {
        String[] args = {"rm", "testDir"};
        
        rmCommand.execute(args);

        File dir = new File("testDir");

        assertTrue(dir.exists());
    }
}
