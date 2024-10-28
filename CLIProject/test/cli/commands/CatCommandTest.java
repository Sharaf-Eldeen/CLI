package cli.commands;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cli.CommandLineInterpreter;

public class CatCommandTest {
    private CatCommand catCommand;
    private File testFile1;
    private File testFile2;

    @Before
    public void setUp() throws IOException {
          

  
        catCommand = new CatCommand(new CommandLineInterpreter());

        // Create test files with content
        testFile1 = new File("testFile1.txt");
        try (FileWriter writer = new FileWriter(testFile1)) {
            writer.write("Hello World!\nThis is testFile1.");
        }

        testFile2 = new File("testFile2.txt");
        try (FileWriter writer = new FileWriter(testFile2)) {
            writer.write("This is testFile2.\nSecond line of testFile2.");
        }

    }

    @After
    public void tearDown() {
        if (testFile1.exists()) testFile1.delete();
        if (testFile2.exists()) testFile2.delete();
    }

    @Test
    public void testExecuteWithSingleFile() {
        String[] args = {"cat", "testFile1.txt"};
        String result = catCommand.execute(args);
        
        String expectedOutput = "Hello World!\nThis is testFile1.\n";
        assertEquals(expectedOutput, result);
    }

    @Test
    public void testExecuteWithMultipleFiles() {
        String[] args = {"cat", "testFile1.txt", "testFile2.txt"};
        String result = catCommand.execute(args);
        
        String expectedOutput = "Hello World!\nThis is testFile1.\nThis is testFile2.\nSecond line of testFile2.\n";
        assertEquals(expectedOutput, result);
    }

    @Test
    public void testExecuteWithNonExistentFile() {
        String[] args = {"cat", "nonExistentFile.txt"};
        String result = catCommand.execute(args);

        String expectedOutput = "File does not exist: nonExistentFile.txt\n";
        assertEquals(expectedOutput, result);
    }

    @Test
    public void testExecuteWithMixedFiles() {
        String[] args = {"cat", "testFile1.txt", "nonExistentFile.txt"};
        String result = catCommand.execute(args);

        String expectedOutput = "Hello World!\nThis is testFile1.\nFile does not exist: nonExistentFile.txt\n";
        assertEquals(expectedOutput, result);
    }

    @Test
    public void testExecuteWithNoArguments() {
        String[] args = {"cat"};
        String result = catCommand.execute(args);

        String expectedOutput = "You should provide at least one file name";
        assertEquals(expectedOutput, result);
    }
}
