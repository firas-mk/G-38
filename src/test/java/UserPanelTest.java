import Classes.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    public void testLoginAsTourist(){
        outputStream.reset();
        System.setIn(new ByteArrayInputStream("1\n".getBytes()));
        UserPanel.loginTest = true;
        UserPanel.loginPanel();
        assertTrue(UserPanel.loginVerified);
        UserPanel.loginTest = false;
    }
    @Test
    public void testLoginAsGuide(){
        outputStream.reset();
        System.setIn(new ByteArrayInputStream("2\n".getBytes()));
        UserPanel.loginTest = true;
        UserPanel.loginPanel();
        assertTrue(UserPanel.loginVerified);
        UserPanel.loginTest = false;
    }
    @Test
    public void testLoginAsAdmin(){
        outputStream.reset();
        System.setIn(new ByteArrayInputStream("3\n".getBytes()));
        UserPanel.loginTest = true;
        UserPanel.loginPanel();
        assertTrue(UserPanel.loginVerified);
        UserPanel.loginTest = false;
    }

    @Test
    public void testLogOutNoAsInput() {
        System.setIn(new ByteArrayInputStream("n\n".getBytes()));
        UserPanel.logOut();

        String expectedOutput = "◆ Thank you for using Tourly. See you later ;)";
        String actualOutput = outputStream.toString();
        assertTrue(actualOutput.contains(expectedOutput));
    }

    /* the bug we face in this test is that it does not stop at loginPanel but runs the code further to touristPanel (which it shouldn't) after logging in
    * this step requires another input when being in touristPanel, this causes the test to stop*/

    @Test
    public void testLogOutYesInput() {
        outputStream.reset(); // Reset the output stream before the next test
        System.setIn(new ByteArrayInputStream("◆ You are successfully logged out, See you later :) ".getBytes()));
        UserPanel.logOut(); // Invoke the method under test

        String expectedOutput = "Do you want to login? [y/n]:";
        String actualOutput = outputStream.toString();
        assertTrue(actualOutput.contains(expectedOutput), "Actual output does not contain the login prompt.");
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
    public void testGetAPriceOfATour() {
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

    // Notice that all tests are tested towards Oslo city, this is because if tests passes when Oslo, then it will pass when choosing other cities
    @Test
    public void testNewTourCanBeAddedToOsloTours() {
        int cityNumber = 1;

        String simulatedUserInput = cityNumber + "\nLocation1\n2023-01-01\n10:00\nA beautiful tour\n500\n";
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

        Admin.addTour();
        String expectedOutput = "◆ Tour added successfully ✔";
        String actualOutput = outputStream.toString();
        assertTrue(actualOutput.contains(expectedOutput), "Tour was not added successfully");

        System.setIn(System.in);
    }

    @Test
    public void testTourNr2FromOsloToursCanBeEdited() {
        int cityNumber = 1;
        int tourNumberToEdit = 2;

        String simulatedUserInput = cityNumber + "\n" + tourNumberToEdit + "\nThe Norwegian Opera & Ballet\n30.10.2023\n17:00\nBehind-the-scenes tour of the Opera House\n225\n";
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

        Admin.editTour();
        String expectedOutput = "◆ Tour edited successfully ✔";
        String actualOutput = outputStream.toString();
        assertTrue(actualOutput.contains(expectedOutput), "Tour was not edited successfully");


        System.setIn(System.in);
    }
    /* NOTE: if you run the test once and delete tour number 3,
        next time you run the test it won't pass because tour number 3 is not available
         since it got deleted when you run the test first time */
    @Test
    public void testTourNr3FromOsloToursCanBeDeleted() {
        int cityNumber = 1; // Oslo City
        int tourNumberToDelete = 3;

        Admin.deleteTour(cityNumber, tourNumberToDelete);
        String expectedOutput = "◆ Tour deleted successfully ✔";
        String actualOutput = outputStream.toString();
        assertTrue(actualOutput.contains(expectedOutput), "Tour was not deleted successfully");
    }

    /* NOTE: if tour number 3 is not deleted and still available then the test won't pass, in this case the asserTrue should be assertFalse to pass the test.
        But if you run testTourNr3FromOsloToursCanBeDeleted() first and so this test then it will pass since tour number 3 is deleted and no longer available*/
    @Test
    public void testTourNr3InOsloToursNotAvailableAfterDeleting() throws IOException {
        String OsloCityTours = "src/main/java/JSON_files/oslo_tours.json";
        int deletedTourNr = 3;

        Guide.tourExists(OsloCityTours, deletedTourNr);
        String expectedMsg = "◆ Tour does not exist!";
        String actualOutput = outputStream.toString();
        assertTrue(actualOutput.contains(expectedMsg));
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
