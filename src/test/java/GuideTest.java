import Classes.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;
public class GuideTest {
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
    public void testLoginAsGuide(){
        System.setIn(new ByteArrayInputStream("2\n".getBytes()));
        UserPanel.loginTest = true;
        UserPanel.loginPanel();
        assertTrue(UserPanel.loginVerified);
        UserPanel.loginTest = false;
    }

    @Test
    public void testTourNr2FromOsloCanBeBookedByGuide(){
        outputStream.reset();

        System.setIn(new ByteArrayInputStream("1\n2\n".getBytes())); // chooses Oslo then tour nr. 2
        Guide.bookingTest = true;
        Guide.bookTour("GuideId");

        String expectedOutput = "◆ Tour booked successfully ✔";
        String actualOutput = outputStream.toString();
        assertTrue(actualOutput.contains(expectedOutput));
        Guide.bookingTest = false;
    }


}
