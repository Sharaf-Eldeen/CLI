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
    //defining all variable that i will use in all tests
    private LsCommand lsCommand;
    private CommandLineInterpreter cli;
    private Path testDir;
    private Path hiddenFile;

    @Before
    public void setUp() throws IOException {
        // new cli so the test dont effect the orignal one and alltests work on the same cli 
        cli = new CommandLineInterpreter();
        lsCommand = new LsCommand(cli);
        //createing tempdir so it doen't make any problems with any file on the orignal work
        testDir = Files.createTempDirectory("testDir");
        cli.setCurrentDirectory(testDir.toString());
        //creating hidden file for the hidden test for ls -a command and args
        hiddenFile = Files.createFile(testDir.resolve(".hiddenFile"));
    }

    @After
     // deleting every new file or path or dir that i used in the test after finshing the tests
    public void tearDown() throws IOException {
        deleteTree(testDir.toFile());
    }
    private void deleteTree(File file) throws IOException {
        if (file.isDirectory()) {
            for (File subFile : file.listFiles()) {
                deleteTree(subFile);
            }
        }
        file.delete();
    }


    @Test
    // test1:this test check for the simple jop of ls only by creating new file in the tempdir created before an dcheck if the output contains this new file or not
    public void testCurrDir() throws IOException {
        Files.createFile(testDir.resolve("sampleFile.txt"));
        String ret = lsCommand.execute(new String[]{"ls"});
        assertTrue("Output should list the files and folders in the current directory", ret.contains("sampleFile.txt"));
    }

    @Test
    // test2:using the previouse creted hidden file in the set up function, this test checks if the output of ls -a conation that hidden file or not
    public void testHiddenFiles() {
        String ret = lsCommand.execute(new String[]{"ls", "-a"});
        assertTrue("Output should include hidden files when '-a' flag is used", ret.contains(".hiddenFile"));
    }

    @Test
    // test3:this test creates two files with the correct order and then call ls -r and check if the postion of those two files will change or not
    public void testReversed() throws IOException {
        Files.createFile(testDir.resolve("Afile"));
        Files.createFile(testDir.resolve("Bfile"));

        String ret = lsCommand.execute(new String[]{"ls", "-r"});
        String[] v = ret.split("\\n");

        assertTrue("Output should list files in reverse alphabetical order", 
                   v[0].contains("Bfile") && v[1].contains("Afile"));
    }

    @Test
    // test4:this test creates an path to give tho the function and then create a file inside this new path and then pass the path to the function 
    // and check if the created file will be the out put or not
    public void testSpecificDir() throws IOException {
        Path givenpath = Files.createDirectory(testDir.resolve("src"));
        assertTrue("The 'src' directory should be created", Files.exists(givenpath));

        Path testfile = Files.createFile(givenpath.resolve("testFile.txt"));
        assertTrue("The 'testFile.txt' file should be created", Files.exists(testfile));

        String ret = lsCommand.execute(new String[]{"ls", givenpath.toAbsolutePath().toString()});
    
        assertTrue("Output should list the contents of the specified 'src' directory", ret.contains("testFile.txt"));
    }

    @Test
    // test5:this test passing a wrong path for the function and check if it will terminate or will work and checks the output
    public void testWrongPath() {
        String ret = lsCommand.execute(new String[]{"ls", "nonexistentDir"});
        assertEquals("There is an error for a nonexistent dir",
                "Cannot access directory: nonexistentDir", ret);
    }
}


