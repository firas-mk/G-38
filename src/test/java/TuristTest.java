import Classes.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;
public class TuristTest {
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
    public void testLoginAsTourist(){
        outputStream.reset();
        System.setIn(new ByteArrayInputStream("1\n".getBytes()));
        UserPanel.loginTest = true;
        UserPanel.loginPanel();
        assertTrue(UserPanel.loginVerified);
        UserPanel.loginTest = false;
    }
    @Test
    public void testGetOsloCityFromAvailableCities(){
        outputStream.reset();
        String file = "src/main/java/JSON_files/available_cities.json";
        UserPanel.getAvailableCities(file);

        String expectedOutput = "Oslo";
        String actualOutput = outputStream.toString();
        assertTrue(actualOutput.contains(expectedOutput));
    }
    @Test
    public void testKristiansandCityNotAvailableAmongAvailableCities(){
        outputStream.reset();
        String file = "src/main/java/JSON_files/available_cities.json";
        UserPanel.getAvailableCities(file);

        String expectedOutput = "Kristiandsand";
        String actualOutput = outputStream.toString();
        assertFalse(actualOutput.contains(expectedOutput));
    }
    @Test
    public void testDisplayToursOfOsloCity(){
        outputStream.reset();
        String OsloCityTours = "src/main/java/JSON_files/oslo_tours.json";
        Tourist.getToursFromJSONFile(OsloCityTours);
        String expectedTour = "Holmenkollen Ski Jump";
        String actualOutput = outputStream.toString();
        assertTrue(actualOutput.contains(expectedTour));
    }

    @Test
    public void testGetPriceOfATour() {
        outputStream.reset();
        String HaldenCityTours = "src/main/java/JSON_files/halden_tours.json";
        Tourist.getToursFromJSONFile(HaldenCityTours);
        String expectedTourPrice = "175 kr";
        String actualOutput = outputStream.toString();
        assertTrue(actualOutput.contains(expectedTourPrice));

    }

    @Test
    public void testTourIsUnavailableToBeBookedByTouristInOslo(){
        outputStream.reset();
        String OsloCityTours = "src/main/java/JSON_files/oslo_tours.json";

        Tourist.getToursFromJSONFile(OsloCityTours);
        String expectedTourStatus = "unavailable";
        String actualOutput = outputStream.toString();
        assertFalse(actualOutput.contains(expectedTourStatus));
    }

    @Test
    public void testABookedTourByTouristCanBeCanceled(){
        outputStream.reset();
        int tourToBeCanceled = 1;

        Tourist.cancelBookedTour(tourToBeCanceled);
        String expectedTourStatus = "◆ Tour has been canceled successfully ✔";
        String actualOutput = outputStream.toString();
        assertTrue(actualOutput.contains(expectedTourStatus));


    }
}
