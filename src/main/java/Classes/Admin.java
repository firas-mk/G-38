package Classes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Admin extends userPanel {
    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static void addTour(int tourNumber) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the city number for the tour: ");
            int cityNumber = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter the tour number: "); // Added this line
            tourNumber = Integer.parseInt(scanner.nextLine()); // Updated this line
            String filePath = getFilePath(cityNumber);

            if (!filePath.equals("")) {
                JsonNode tours = objectMapper.readTree(new File(filePath));
                ArrayNode toursArray = (ArrayNode) tours;

                // create a new tour node
                ObjectNode newTour = objectMapper.createObjectNode();
                newTour.put("tourNr", tourNumber);

                //  Tour node

                System.out.println("Enter the location: ");
                newTour.put("location", scanner.nextLine());

                System.out.println("Enter the date: ");
                newTour.put("date", scanner.nextLine());

                System.out.println("Enter the time: ");
                newTour.put("time", scanner.nextLine());

                System.out.println("Enter the description: ");
                newTour.put("description", scanner.nextLine());

                System.out.println("Enter the price: ");
                newTour.put("price", scanner.nextLine());

                // add the new tour node to the tours array
                toursArray.add(newTour);

                // write the updated tours array back to the JSON file
                objectMapper.writeValue(new File(filePath), toursArray);

                System.out.println("Tour added successfully!");
            } else {
                System.out.println("File path not found!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public static void deleteTour(int cityNumber, int tourNumber) {
        try {
            String filePath = getFilePath(cityNumber);
            if (!filePath.equals("")) {
                JsonNode tours = objectMapper.readTree(new File(filePath));
                ArrayNode toursArray = (ArrayNode) tours;

                if (tourNumber > 0 && tourNumber <= toursArray.size()) {
                    toursArray.remove(tourNumber - 1);

                    // write the updated tours array back to the JSON file
                    objectMapper.writeValue(new File(filePath), toursArray);

                    System.out.println("Tour deleted successfully!");
                } else {
                    System.out.println("Invalid tour number!");
                }
            } else {
                System.out.println("File path not found!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFilePath(int tourNumber) {
        try {
            String filePath = "src/main/java/JSON_files/available_cities.json";
            JsonNode availableCities = objectMapper.readTree(new File(filePath));
            ArrayNode citiesArray = (ArrayNode) availableCities;

            for (JsonNode city : citiesArray) {
                if (city.get("cityNr").asInt() == tourNumber) {
                    return city.get("jsonFilePath").asText();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";

    }
}
