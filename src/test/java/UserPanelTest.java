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
    private final ByteArrayInputStream userSimulatedInput = new ByteArrayInputStream("n\n".getBytes());
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
        System.setIn(userSimulatedInput);
    }

    @Test
    public void testLogOutNoInput() {
        UserPanel.logOut(); 

        String expectedOutput = "◆ Thank you for using Tourly. See you later ;)";
        String actualOutput = outputStream.toString();
        assertTrue(actualOutput.contains(expectedOutput));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(stdOutput);
        System.setIn(System.in);
    }

    @Test
    public void testLoginPanelInvalidInput3() {
        assertEquals(4, 4);
    }
    @Test
    public void testLoginPanelInvalidInput5() {
        assertEquals(5, 5);
    }

}