import Classes.Tourist;
import Classes.UserPanel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.PrintStream;



import static org.junit.jupiter.api.Assertions.*;

public class UserPanelTest {
    private final PrintStream stdOutput = System.out;

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
    }
    @AfterEach
    public void tearDown() {
        System.setOut(stdOutput);
        System.setIn(System.in);
    }

    @Test
    public void testLogOutNoInput() {
        System.setIn(new ByteArrayInputStream("n\n".getBytes()));
        UserPanel.logOut();

        String expectedOutput = "◆ Thank you for using Tourly. See you later ;)";
        String actualOutput = outputStream.toString();
        assertTrue(actualOutput.contains(expectedOutput));
    }
    @Test
    public void testLogOutYesInput() {
        outputStream.reset(); // Reset the output stream before the next test
        System.setIn(new ByteArrayInputStream("◆ You are successfully logged out, See you later :) ".getBytes()));
        UserPanel.logOut(); // Invoke the method under test

        String expectedOutput = "Do you want to login? [y/n]:";
        String actualOutput = outputStream.toString();
        assertTrue(actualOutput.contains(expectedOutput), "Actual output does not contain the login prompt.");
    }


}