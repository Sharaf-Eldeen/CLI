package cli.commands;

import cli.CommandLineInterpreter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TouchCommandTest {
    //defining all variable that i will use in all tests
    private TouchCommand touchCommand;
    private CommandLineInterpreter cli;
    private Path testdir;

    @Before
    public void setUp() throws IOException {
        // new cli so the test dont effect the orignal one and alltests work on the same cli 
        cli = new CommandLineInterpreter();
        touchCommand = new TouchCommand(cli);
        //createing tempdir so it doen't make any problems with any file on the orignal work
        testdir = Files.createTempDirectory("testDir");
        // set the currdir of cli to thest dir
        cli.setCurrentDirectory(testdir.toString());
    }

    @After
    // deleting every new file or path or dir that i used in the test after finshing the tests
    public void tearDown() throws IOException {
        Files.walk(testdir)
             .map(Path::toFile)
             .forEach(File::delete);
    }

    @Test
    // test one: checks if the new file is created or not by calling th funtion and pathing a nam for the file and
    // then check for if the file created or not then check if the output is as i want or not
    public void testFileCreation() {
        String fileName = "newFile.txt";
        String output = touchCommand.execute(new String[]{"touch", fileName});
        
        assertTrue("File should be created", Files.exists(testdir.resolve(fileName)));
        assertEquals("File created: " + testdir.resolve(fileName), output);
    }

    @Test
    // test two: checking the case of an exixting file by creating a file in the curr dir and then call the function to create it
    // and then check if the output as i want or not
    public void testFileAlreadyExists() throws IOException {
        Files.createFile(testdir.resolve("existingFile.txt"));
        
        String output = touchCommand.execute(new String[]{"touch", "existingFile.txt"});
        
        assertEquals("File already exists.", output);
    }

    @Test
    // thest three: check if the function handles the missing args or not
    public void testMissingFileNameArgument() {
        String output = touchCommand.execute(new String[]{"touch"});
        
        assertEquals("Usage: touch <filename>", output);
    }

    @Test
    // checking if the given path has dirs not created so the function should create them and then crete the file in the write path
    public void testDirectoryCreationAndFileCreation() {
        String nestedFilePath = "nestedDir/newFile.txt";
        String output = touchCommand.execute(new String[]{"touch", nestedFilePath});
        
        assertTrue("Nested directory should be created", Files.exists(testdir.resolve("nestedDir")));
        assertTrue("File in nested directory should be created", Files.exists(testdir.resolve(nestedFilePath)));
        assertEquals("File created: " + testdir.resolve(nestedFilePath), output);
    }
}

