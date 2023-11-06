package Classes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Admin extends userPanel {
    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static void addTour(int tourNumber) {
        // for structures json file
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the city number for the tour: ");
            int cityNumber = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter the tour number: ");

            tourNumber = Integer.parseInt(scanner.nextLine());
            String filePath = getFilePath(cityNumber);

            if (!filePath.equals("")) {
                if (tourExists(filePath, tourNumber)) {
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Tour  number is  already exists!" + ConsoleColors.RESET);
                    return;
                }

                // create a new tour node
                ObjectNode newTour = objectMapper.createObjectNode();
                newTour.put("tourNr", tourNumber);

                // Tour node

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
                ArrayNode toursArray = (ArrayNode) objectMapper.readTree(new File(filePath));
                toursArray.add(newTour);

                // write the updated tours array back to the JSON file
                objectMapper.writeValue(new File(filePath), toursArray);

                System.out.println("Tour added successfully!");
            } else {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Invalid choice!" + ConsoleColors.RESET);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteTour(int cityNumber, int tourNumber) {
        // for structures json file
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            String filePath = getFilePath(cityNumber);
            if (!filePath.equals("")) {
                JsonNode tours = objectMapper.readTree(new File(filePath));
                ArrayNode toursArray = (ArrayNode) tours;

                if (toursArray == null) {
                    System.out.println("No available tours in the selected city!");
                } else if (toursArray.size() == 0) {
                    System.out.println("No tours found in the selected city!");
                } else {
                    boolean tourFound = false;
                    for (int i = 0; i < toursArray.size(); i++) {
                        JsonNode tour = toursArray.get(i);
                        int existingTourNumber = tour.get("tourNr").asInt();
                        if (existingTourNumber == tourNumber) {
                            toursArray.remove(i);
                            tourFound = true;
                            break;
                        }
                    }
                    if (tourFound) {
                        // write the updated tours array back to the JSON file
                        objectMapper.writeValue(new File(filePath), toursArray);
                        System.out.println("Tour deleted successfully!");
                    } else {
                        System.out.println("Invalid Tour number \n" +
                                "Tour number does not exist!");
                    }
                }
            } else {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Invalid choice!" + ConsoleColors.RESET);
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
    public static boolean tourExists(String filePath, int tourNumber) throws IOException {
        JsonNode tours = objectMapper.readTree(new File(filePath));
        ArrayNode toursArray = (ArrayNode) tours;

        for (JsonNode tour : toursArray) {
            int existingTourNumber = tour.get("tourNr").asInt();
            if (existingTourNumber == tourNumber) {
                return true;
            }
        }

        return false;
    }
}
