package cli.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// import cli.commands.RmdirCommand;

public class RmdirCommandTest {
    private RmdirCommand rmdirCommand;

    @Before
    public void setUp() throws IOException {
        rmdirCommand = new RmdirCommand();

        File dir = new File("testDir");
        assertTrue(dir.mkdir());

        File file = new File("testDir/testFile.txt");
        assertTrue(file.createNewFile());
    }

    @After
    public void tearDown() {
        File dir = new File("testDir");
        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File f : files) {
                    f.delete();
                }
            }
            dir.delete();
        }
        assertFalse(dir.exists());
    }

    @Test
    public void testExecuteDeletesDirectorySuccessfully() {
        String[] args = {"rmdir", "testDir"};
        
        rmdirCommand.execute(args);

        File dir = new File("testDir");

        assertFalse(dir.exists());
    }

    @Test
    public void testExecuteFailsIfDirectoryDoesNotExist() {
        String[] args = {"rmdir", "nonExistentDir"};
        rmdirCommand.execute(args);
        File dir = new File("testDir");
        assertTrue(dir.exists());
        
    }    
}
