package Classes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;



public class Guide {
    private String guideId;
    private String name;
    private String contactInformation;

    public Guide(String guideId, String name, String contactInformation) {
        this.guideId = guideId;
        this.name = name;
        this.contactInformation = contactInformation;
    }
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static boolean bookingTest = false;


    public String getGuideId() {
        return guideId;
    }


    public static void bookTour(String guideId) {
        // for structured JSON file
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {

            Scanner scanner = new Scanner(System.in);
            System.out.println(ConsoleColors.YELLOW + "||> Enter the city number to view related tours, [0 -> Main Menu]: " + ConsoleColors.RESET);
            int cityNumber = Integer.parseInt(scanner.nextLine());
            if(cityNumber == 0){
                UserPanel.guidePanel();
            }else {

            String filePath = getFilePath(cityNumber);
            ArrayNode toursArray = (ArrayNode) objectMapper.readTree(new File(filePath));

            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n------------------------------------");
            System.out.println("| Available tours in city number: " + cityNumber);
            System.out.println("------------------------------------" + ConsoleColors.RESET);
            int totalAvailableTours = 0;
            for (JsonNode tour : toursArray) {
                if (tour instanceof ObjectNode) {
                    ObjectNode tourObject = (ObjectNode) tour;
                    String status = tour.get("status").asText();
                    JsonNode jsonFile = objectMapper.readTree(new File(filePath));
                    int totalTours = jsonFile.size();
                    // Display tour details
                    if (status.equals("unavailable")){
                        totalAvailableTours += 1;
                        System.out.println(
                                "\n"+ ConsoleColors.YELLOW_UNDERLINED + ConsoleColors.YELLOW_BOLD_BRIGHT + "[*] Tour number " + ConsoleColors.RED_BOLD_BRIGHT +  tourObject.get("tourNr").asInt() + ConsoleColors.RESET +
                                        "\n" + ConsoleColors.RED_BOLD_BRIGHT +"[*] " + ConsoleColors.RESET + "Location: " + tourObject.get("location").asText() +
                                        "\n" + ConsoleColors.RED_BOLD_BRIGHT +"[*] " + ConsoleColors.RESET + "Date: " + tourObject.get("date").asText() +
                                        "\n" + ConsoleColors.RED_BOLD_BRIGHT +"[*] " + ConsoleColors.RESET + "Time: " + tourObject.get("time").asText() +
                                        "\n" + ConsoleColors.RED_BOLD_BRIGHT +"[*] " + ConsoleColors.RESET + "Description: " + tourObject.get("description").asText() +
                                        "\n" + ConsoleColors.RED_BOLD_BRIGHT +"[*] " + ConsoleColors.RESET + "Price: " + tourObject.get("price").asText() + "\n"
                                + "------------------------------------"
                        );
                    }


                }
            }
            if (totalAvailableTours == 0) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "◆ Oops, looks like there is no tours available! \n" + ConsoleColors.RESET);

            }
            System.out.println(ConsoleColors.YELLOW + "||> Enter the tour number you want to book, [0 -> Main Menu]: ");
            int tourNumber = Integer.parseInt(scanner.nextLine());
            if (tourNumber == 0){
                UserPanel.guidePanel();
            } else{

                if (!filePath.equals("")) {
                    if (!tourExists(filePath, tourNumber)) {
                        System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "◆ Tour number does not exist!" + ConsoleColors.RESET);
                        return;
                    }
                    // Read the existing tours from the JSON file
                    for (JsonNode tour : toursArray) {
                        if (tour.get("tourNr").asInt() == tourNumber) {
                            if (tour instanceof ObjectNode) {
                                ObjectNode tourObject = (ObjectNode) tour;

                                // booking
                                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "◆ Tour booked successfully ✔" + ConsoleColors.RESET);

                                // Display  values
                                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n-----------------------------");
                                System.out.println("| You have booked this tour |");
                                System.out.println("-----------------------------" + ConsoleColors.RESET);
                                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "[*]"  + ConsoleColors.RESET + " Location: " + tourObject.get("location").asText());
                                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "[*]"  + ConsoleColors.RESET + " Date: " + tourObject.get("date").asText());
                                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "[*]"  + ConsoleColors.RESET + " Time: " + tourObject.get("time").asText());
                                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "[*]"  + ConsoleColors.RESET + " Description: " + tourObject.get("description").asText());
                                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "[*]"  + ConsoleColors.RESET + " Price: " + tourObject.get("price").asText());

                                // make tour available to tourist and add guideID
                                tourObject.put("status", "available");
                                tourObject.put("guideID", guideId);

                                // Write the updated tours array back to the JSON file
                                objectMapper.writeValue(new File(filePath), toursArray);
                                if (!bookingTest){
                                    UserPanel.guidePanel();

                                }

                            }
                        }
                    }
                } else {
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "◆ Invalid choice!" + ConsoleColors.RESET);
                }
            }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showBookedTours(String guideId) {
        try {

            String availableCitiesFilePath = "src/main/java/JSON_files/available_cities.json";
            JsonNode availableCities = objectMapper.readTree(new File(availableCitiesFilePath));
            ArrayNode citiesArray = (ArrayNode) availableCities;

            for (JsonNode city : citiesArray) {
                int cityNumber = city.get("cityNr").asInt();
                String cityName = city.get("cityName").asText();  // Fetch city name from JSON

                String cityFilePath = getFilePath(cityNumber);

                // Read the tours from the city's JSON file
                JsonNode tours = objectMapper.readTree(new File(cityFilePath));
                ArrayNode toursArray = (ArrayNode) tours;

                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n------------------------------------");
                System.out.println("| Booked tours in city: " + cityName);
                System.out.println("------------------------------------" + ConsoleColors.RESET);

                int totalBookedTours = 0;

                // Iterate through each tour in the city
                for (JsonNode tour : toursArray) {
                    if (tour instanceof ObjectNode) {
                        ObjectNode tourObject = (ObjectNode) tour;

                        // Check if the tour is booked by the specified guide
                        if (tourObject.has("guideID") && tourObject.get("guideID").asText().equals(guideId)) {
                            totalBookedTours += 1;
                            System.out.println(
                                    "\n" + ConsoleColors.YELLOW_UNDERLINED + ConsoleColors.YELLOW_BOLD_BRIGHT + "[*] Tour number " + ConsoleColors.RED_BOLD_BRIGHT + tourObject.get("tourNr").asInt() + ConsoleColors.RESET +
                                            "\n" + ConsoleColors.RED_BOLD_BRIGHT + "[*] " + ConsoleColors.RESET + "Location: " + tourObject.get("location").asText() +
                                            "\n" + ConsoleColors.RED_BOLD_BRIGHT + "[*] " + ConsoleColors.RESET + "Date: " + tourObject.get("date").asText() +
                                            "\n" + ConsoleColors.RED_BOLD_BRIGHT + "[*] " + ConsoleColors.RESET + "Time: " + tourObject.get("time").asText() +
                                            "\n" + ConsoleColors.RED_BOLD_BRIGHT + "[*] " + ConsoleColors.RESET + "Description: " + tourObject.get("description").asText() +
                                            "\n" + ConsoleColors.RED_BOLD_BRIGHT + "[*] " + ConsoleColors.RESET + "Price: " + tourObject.get("price").asText() + "\n"
                                            + "------------------------------------"
                            );
                        }
                    }
                }

                if (totalBookedTours == 0) {
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "◆ Oops, looks like there are no booked tours for this guide in city " + cityName + "!\n" + ConsoleColors.RESET);
                }
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
        System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "◆ Tour does not exist!" + ConsoleColors.RESET);
        return false;
    }

    }



