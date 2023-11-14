package Classes;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThrows;
class UserPanelTest {
    @Test
    void testLoginPanelTourist() {
        // Redirect System.out to capture the output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Provide input for the scanner
        ByteArrayInputStream inputStream = new ByteArrayInputStream("1\n".getBytes());
        System.setIn(inputStream);

        // Call the method to be tested
        UserPanel.loginPanel();

        // Reset System.in and System.out
        System.setIn(System.in);
        System.setOut(System.out);

        // Verify the output
        String expectedOutput = "You are now logged in as [Turist]";
        assertTrue(outputStream.toString().contains(expectedOutput));
    }

    @Test
    void testLoginPanelInvalidInput() {
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
        assertTrue(outputStream.toString().contains(expectedOutput));
    }

}