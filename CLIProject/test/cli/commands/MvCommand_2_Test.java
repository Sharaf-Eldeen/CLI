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

public class MvCommand_2_Test {
    private MvCommand mvCommand;

    @Before
    public void setUp() throws IOException {
        mvCommand = new MvCommand(new CommandLineInterpreter());

        File srcFile = new File("testFile.txt");
        assertTrue(srcFile.createNewFile());

        File destDir = new File("destinationDir");
        assertTrue(destDir.mkdir());
    }

    @After
    public void tearDown() {
        new File("testFile.txt").delete();
        new File("destinationDir/testFile.txt").delete();
        new File("destinationDir").delete();
    }

    @Test
    public void testExecuteMovesFileToDirectory() throws IOException {

        File srcFile = new File("testFile.txt");
        File destDir = new File("destinationDir");
        File movedFile = new File(destDir, "testFile.txt");
        
        String[] args = {"mv", "testFile.txt", "destinationDir/testFile.txt"};
        mvCommand.execute(args);
    
        
        assertFalse(srcFile.exists());
        assertTrue(movedFile.exists());
    }
    
}
