package cli.commands;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cli.CommandLineInterpreter;

// import cli.commands.MvCommand;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

public class MvCommandTest {
    private MvCommand Mvcommand;

    @Before
    public void setup() throws IOException {
        Mvcommand = new MvCommand(new CommandLineInterpreter());
        File srcFile = new File("testFile.txt");
        assertTrue(srcFile.createNewFile());
    }

    @After
    public void tearDown() {
        File srcFile = new File("testFile.txt");
        File dstFile = new File("movedFile.txt");
        srcFile.delete();
        dstFile.delete();
        assertFalse(srcFile.exists() || dstFile.exists());
    }

    @Test
    public void testExecuteMovesFileSuccessfully() {
        String[] args = {"mv", "testFile.txt", "movedFile.txt"};
        Mvcommand.execute(args);
        File srcFile = new File("testFile.txt");
        File dstFile = new File("movedFile.txt");
        assertFalse(srcFile.exists());
        assertTrue(dstFile.exists());
    }

    @Test
    public void testExecuteFailsIfSourceDoesNotExist() {
        String[] args = {"mv", "nonExistentFile.txt", "movedFile.txt"};
        Mvcommand.execute(args);
        File dstFile = new File("movedFile.txt");
        assertFalse(dstFile.exists());
    }

    @Test 
    public void testExecuteFailsWithInsufficientArguments() {
        String[] args = {"mv", "testFile.txt"};
        Mvcommand.execute(args);
        File srcFile = new File("testFile.txt");
        assertTrue(srcFile.exists());
    }

    
}
