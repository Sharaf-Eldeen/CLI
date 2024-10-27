package cli.commands;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LsCommandTest {
    @Test
    void testExecuteCurrentDirectory() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        LsCommand lsCommand = new LsCommand();
        lsCommand.execute(new String[]{"ls"});

        String output = outputStream.toString().trim();
        File currentDir = new File(System.getProperty("user.dir"));
        assertTrue(output.contains(currentDir.getName()), "Output should list current directory files and folders");
    }

    @Test
    void testExecuteHiddenFiles() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        LsCommand lsCommand = new LsCommand();
        lsCommand.execute(new String[]{"ls", "-a"});

        String output = outputStream.toString().trim();
        assertTrue(output.contains(".hiddenFile"), "Output should contain hidden files when -a flag is used");
    }

    @Test
    void testExecuteReverseOrder() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        LsCommand lsCommand = new LsCommand();
        lsCommand.execute(new String[]{"ls", "-r"}); // List in reverse order

        String output = outputStream.toString().trim();
        String[] lines = output.split("\\n");

        assertTrue(lines[0].compareTo(lines[lines.length - 1]) > 0, "Output should be in reverse alphabetical order");
    }

    @Test
    void testExecuteSpecificDirectory() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        LsCommand lsCommand = new LsCommand();
        lsCommand.execute(new String[]{"ls", "src"}); // Specify "src" directory

        String output = outputStream.toString().trim();
        assertTrue(output.contains("src"), "Output should list files in the specified 'src' directory");
    }

    @Test
    void testExecuteNonexistentDirectory() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        LsCommand lsCommand = new LsCommand();
        String output = lsCommand.execute(new String[]{"ls", "nonexistentDir"});

        assertEquals("Cannot access directory: nonexistentDir", output, "Output should show an error for nonexistent directory");
    }
}
