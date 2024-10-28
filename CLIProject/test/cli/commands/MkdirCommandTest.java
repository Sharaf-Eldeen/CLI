package cli.commands;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cli.CommandLineInterpreter;

// import cli.commands.*;


public class MkdirCommandTest {
    private MkdirCommand Mkdircommand;

    @Before
    public void setup() {
        Mkdircommand = new MkdirCommand(new CommandLineInterpreter());
    }

    @After
    public void tearDown() {
        File testDirectory = new File("testDir");
        if(testDirectory.exists()) {
            testDirectory.delete();
        }
    }

    @Test
    public void testExecuteCreatesDirectory() {
        String[] args = {"mkdir", "testDir"};
        Mkdircommand.execute(args);
        File testDirectory = new File("testDir");
        assertTrue(testDirectory.exists()&&testDirectory.isDirectory());
    }

    @Test
    public void testExecuteFailsWithNoArguments() {
        String[] args = {"mkdir"};
        Mkdircommand.execute(args);
        File testDirectory = new File("mkdir");
        assertFalse(testDirectory.exists()&&testDirectory.isDirectory());
    }


}