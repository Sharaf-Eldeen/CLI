package cli.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LsCommandTest {
    private LsCommand lsCommand;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
        lsCommand = new LsCommand();
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
        outputStream.reset();
    }

    @Test
    public void testExecuteCurrentDirectory() {
        lsCommand.execute(new String[]{"ls"});
        String output = outputStream.toString().trim();
        File currentDir = new File(System.getProperty("user.dir"));
        assertTrue("Output should list current directory files and folders", output.contains(currentDir.getName()));
    }

    @Test
    public void testExecuteHiddenFiles() {
        lsCommand.execute(new String[]{"ls", "-a"});
        String output = outputStream.toString().trim();
        assertTrue("Output should contain hidden files when -a flag is used", output.contains(".hiddenFile"));
    }

    @Test
    public void testExecuteReverseOrder() {
        lsCommand.execute(new String[]{"ls", "-r"});
        String output = outputStream.toString().trim();
        String[] lines = output.split("\\n");
        assertTrue("Output should be in reverse alphabetical order", lines[0].compareTo(lines[lines.length - 1]) > 0);
    }

    @Test
    public void testExecuteSpecificDirectory() {
        lsCommand.execute(new String[]{"ls", "src"});
        String output = outputStream.toString().trim();
        assertTrue("Output should list files in the specified 'src' directory", output.contains("src"));
    }

    @Test
    public void testExecuteNonexistentDirectory() {
        String output = lsCommand.execute(new String[]{"ls", "nonexistentDir"});
        assertEquals("Output should show an error for nonexistent directory",
                "Cannot access directory: nonexistentDir", output);
    }
}

