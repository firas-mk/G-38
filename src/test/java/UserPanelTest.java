import Classes.*;
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
    public void testLogOutNoAsInput() {
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
    public void testTourPrice175KrExistsInHaldenTours() {
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
    public void testAddTour() {

        String testFilePath = "src/main/java/JSON_files/test_tours.json";
        int cityNumber = 1;

        String simulatedUserInput = cityNumber + "\nLocation1\n2023-01-01\n10:00\nA beautiful tour\n500 kr\n";
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

        Admin.addTour();
        String expectedOutput = "Tour added successfully!";
        String actualOutput = outputStream.toString();
        assertTrue(actualOutput.contains(expectedOutput), "Tour was not added successfully");

        System.setIn(System.in);
    }

    @Test
    public void testEditTour() {
        String testFilePath = "src/main/java/JSON_files/test_tours.json";
        int cityNumber = 1;
        int tourNumberToEdit = 2;

        String simulatedUserInput = cityNumber + "\n" + tourNumberToEdit + "\nNew Location\n2023-01-02\n11:00\nUpdated description\n550 kr\n";
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

        Admin.editTour();
        String expectedOutput = "Tour edited successfully!";
        String actualOutput = outputStream.toString();
        assertTrue(actualOutput.contains(expectedOutput), "Tour was not edited successfully");


        System.setIn(System.in);
    }

    @Test
    public void testDeleteTour() {

        String testFilePath = "src/main/java/JSON_files/test_tours.json";
        int cityNumber = 1;
        int tourNumberToDelete = 3;

        Admin.deleteTour(cityNumber, tourNumberToDelete);
        String expectedOutput = "Tour deleted successfully!";
        String actualOutput = outputStream.toString();
        assertTrue(actualOutput.contains(expectedOutput), "Tour was not deleted successfully");
    }

    /*@Test
    public void testBookTourSuccess() {
        // Arrange
        // Assuming that "1" is the city number and "3" is the tour number for the test
        String input1 = "1";
        String input2 = "3";
        /*System.setIn(new ByteArrayInputStream(input1.getBytes()));
        System.setIn(new ByteArrayInputStream(input2.getBytes()));*/
/*
        // Assuming you have a method to set the test file path for the booking
        String testFilePath = "src/main/java/JSON_files/oslo_tours.json";
        //Admin.setFilePathForTesting(testFilePath); // Set the file path to a test file
        String simulatedUserInput = input1 + "\n" + input2 + "\nNew Location\n2023-01-02\n11:00\nUpdated description\n550 kr\n";
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

        // Act
        Guide.bookTour("guideid");

        // Assert
        // Verify the output on the console
        String expectedOutput = "Tour booked successfully!";
        String actualOutput = outputStream.toString();
        assertTrue(actualOutput.contains(expectedOutput));

        // Optionally, read the test file and check if the tour status is updated to 'available'
        // and the guideID is set to "guide123"
    }*/
   /* @Test
    public void testBookTour() {
        // Simulate user inputs
        String input = "2\n5\n"; // Example: city number 2 and tour number 5
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Mocking file and other dependencies
        // ... (Depends on your implementation)

        // Call the method
        Guide.bookTour("guide123");

        // Verify the output
        assertTrue(outputStreamCaptor.toString().contains("Tour booked successfully"));

        // Verify changes in the JSON file
        // ... (Read the JSON file and check for updates)

        // Reset System.in to its original state
        System.setIn(System.in);
    }*/

}