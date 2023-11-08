package Classes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;



public class Guide {
    private String guideId;
    private String name;
    private String contactInformation;
    private List<Tour> tours;

    public Guide(String guideId, String name, String contactInformation) {
        this.guideId = guideId;
        this.name = name;
        this.contactInformation = contactInformation;
        this.tours = new ArrayList<>();
    }
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public void addTour(Tour tour) {
        tours.add(tour);
    }


    public List<Tour> getTours() {
        return tours;
    }

    public List<Booking> viewBookings() {
        List<Booking> guideBookings = new ArrayList<>();
        for (Tour t : tours) {
            guideBookings.addAll(t.getBookings());
        }
        return guideBookings;
    }


    public String getGuideId() {
        return guideId;
    }


    public static void bookTour(String guideId) {
        // for structured JSON file
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the city number for the tour: ");
            int cityNumber = Integer.parseInt(scanner.nextLine());
            String filePath = getFilePath(cityNumber);
            ArrayNode toursArray = (ArrayNode) objectMapper.readTree(new File(filePath));

            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n------------------------------------");
            System.out.println("| Available tours in " + cityNumber);
            System.out.println("------------------------------------" + ConsoleColors.RESET);
            for (JsonNode tour : toursArray) {
                if (tour instanceof ObjectNode) {
                    ObjectNode tourObject = (ObjectNode) tour;

                    // Display tour details
                    System.out.println("Tour Number: " + tourObject.get("tourNr").asInt());
                    System.out.println("Location: " + tourObject.get("location").asText());
                    System.out.println("Date: " + tourObject.get("date").asText());
                    System.out.println("Time: " + tourObject.get("time").asText());
                    System.out.println("Description: " + tourObject.get("description").asText());
                    System.out.println("Price: " + tourObject.get("price").asText());
                    System.out.println("");

                    // You can add more details here as needed

                    // Separate each tour with a line
                    System.out.println("------------------------------------");
                }
            }
            System.out.println("Enter the tour number you want to book: ");
            int tourNumber = Integer.parseInt(scanner.nextLine());

            if (!filePath.equals("")) {
                if (!tourExists(filePath, tourNumber)) {
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Tour number does not exist!" + ConsoleColors.RESET);
                    return;
                }

                // Read the existing tours from the JSON file


                for (JsonNode tour : toursArray) {
                    if (tour.get("tourNr").asInt() == tourNumber) {
                        if (tour instanceof ObjectNode) {
                            ObjectNode tourObject = (ObjectNode) tour;

                            // booking
                            System.out.println("Tour booked successfully! ");
                            tourObject.put("Guide", guideId);
                            // Display  values
                            System.out.println("You have bookde this tour:");
                            System.out.println("Location: " + tourObject.get("location").asText());
                            System.out.println("Date: " + tourObject.get("date").asText());
                            System.out.println("Time: " + tourObject.get("time").asText());
                            System.out.println("Description: " + tourObject.get("description").asText());
                            System.out.println("Price: " + tourObject.get("price").asText());
                            System.out.println("");
                            // booking
                            System.out.println("Tour booked successfully! ");
                            tourObject.put("status", "available");
                            tourObject.put("guideID", guideId);

                            // Write the updated tours array back to the JSON file
                            objectMapper.writeValue(new File(filePath), toursArray);

                            return;
                        }
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

              public static void showAvailableTours() {}





    }



