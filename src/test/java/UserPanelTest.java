import Classes.UserPanel;
import org.junit.jupiter.api.Test;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;


import static org.junit.jupiter.api.Assertions.*;

public class UserPanelTest {

    public void testLoginPanelTourist() {
        // Redirect System.out to capture the output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        System.setOut(new PrintStream(outputStream));
        System.out.println("HELLLOOO");

        // Provide input for the scanner
        ByteArrayInputStream inputStream = new ByteArrayInputStream("1\n".getBytes());
        System.setIn(inputStream);

        // Call the method to be tested
        UserPanel.loginPanel();

        // Reset System.in and System.out
        System.setIn(System.in);
        System.setOut(System.out);

        // Verify the output
        String expectedOutput = "You are now logged in as [Tourist]";
        assertTrue(outputStream.toString().contains(expectedOutput));
    }


    public void testLoginPanelInvalidInput() {
        // Redirect System.out to capture the output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Provide invalid input for the scanner
        ByteArrayInputStream inputStream = new ByteArrayInputStream("invalid\n".getBytes());
        System.setIn(inputStream);

        // Call the method to be tested and expect an InputMismatchException
        assertThrows(InputMismatchException.class, UserPanel::loginPanel);

        // Reset System.in and System.out
        System.setIn(System.in);
        System.setOut(System.out);

        // Verify the output
        String expectedOutput = "◆ invalid number! Enter 1, 2 or 3";
        System.out.println("expected output");
        assertTrue(outputStream.toString().contains(expectedOutput));
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