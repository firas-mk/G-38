package Classes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Admin extends UserPanel {
    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static void showAllTours() {
        try {
            String filePath = "src/main/java/JSON_files/available_cities.json";
            JsonNode availableCities = objectMapper.readTree(new File(filePath));
            ArrayNode citiesArray = (ArrayNode) availableCities;

            // Display available cities for the admin to choose
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n--------------------");
            System.out.println("| Available cities |");
            System.out.println("--------------------" + ConsoleColors.RESET);

            for (JsonNode city : citiesArray) {
                int cityNr = city.get("cityNr").asInt();
                String cityName =  city.get("cityName").asText();
                System.out.println(ConsoleColors.ORANGE_BOLD_BRIGHT + "[" + cityNr + "] " + cityName + ConsoleColors.RESET);
            }

            Scanner scanner = new Scanner(System.in);
            System.out.println(ConsoleColors.YELLOW + "||> Enter the city number to view related tours, [0 -> Main Menu]: " + ConsoleColors.RESET);
            int selectedCityNumber = Integer.parseInt(scanner.nextLine());
            if (selectedCityNumber == 0){
                UserPanel.adminPanel();
            }else{

                // Find the selected city
                JsonNode selectedCity = null;
                for (JsonNode city : citiesArray) {
                    if (city.get("cityNr").asInt() == selectedCityNumber) {
                        selectedCity = city;
                        break;
                    }
                }

                if (selectedCity != null) {
                    String cityFilePath = selectedCity.get("jsonFilePath").asText();
                    ArrayNode toursArray = (ArrayNode) objectMapper.readTree(new File(cityFilePath));

                    // Display tours for the selected city
                    System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n------------------------------------");
                    System.out.println("| Tours Information for " + selectedCity.get("cityName").asText());
                    System.out.println("------------------------------------" + ConsoleColors.RESET);

                    for (JsonNode tour : toursArray) {
                        if (tour instanceof ObjectNode) {
                            ObjectNode tourObject = (ObjectNode) tour;

                            System.out.println(ConsoleColors.YELLOW_UNDERLINED + ConsoleColors.YELLOW_BOLD_BRIGHT +
                                    "\n[*] Tour number " + ConsoleColors.RED_BOLD_BRIGHT + tourObject.get("tourNr").asInt() +
                                    ConsoleColors.RESET +
                                    "\n" + ConsoleColors.RED_BOLD_BRIGHT + "[*] " + ConsoleColors.RESET + "Location: " + tourObject.get("location").asText() +
                                    "\n" + ConsoleColors.RED_BOLD_BRIGHT + "[*] " + ConsoleColors.RESET + "Date: " + tourObject.get("date").asText() +
                                    "\n" + ConsoleColors.RED_BOLD_BRIGHT + "[*] " + ConsoleColors.RESET + "Time: " + tourObject.get("time").asText() +
                                    "\n" + ConsoleColors.RED_BOLD_BRIGHT + "[*] " + ConsoleColors.RESET + "Description: " + tourObject.get("description").asText() +
                                    "\n" + ConsoleColors.RED_BOLD_BRIGHT + "[*] " + ConsoleColors.RESET + "Price: " + tourObject.get("price").asText() +
                                    "\n" + ConsoleColors.RED_BOLD_BRIGHT + "[*] " + ConsoleColors.RESET + "touristID: " + tourObject.get("touristID").asText() +
                                    "\n" + ConsoleColors.RED_BOLD_BRIGHT + "[*] " + ConsoleColors.RESET + "guideID: " + tourObject.get("guideID").asText() +
                                    "\n" + ConsoleColors.RED_BOLD_BRIGHT + "[*] " + ConsoleColors.RESET + "Status: " + tourObject.get("status").asText() +
                                    "\n------------------------------------");
                        }
                    }
                } else {
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "◆ Invalid city number, enter a valid number!" + ConsoleColors.RESET);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addTour() {
        // for structures json file
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println(ConsoleColors.YELLOW + "||> Choice a city to add the tour to, [0 -> Main Menu]: " + ConsoleColors.RESET);
            int cityNumber = Integer.parseInt(scanner.nextLine());
            if (cityNumber == 0){
                UserPanel.adminPanel();
            }else {
                String filePath = getFilePath(cityNumber);

                // gi turen et nummer i turliste dinamisk
                int tourNumber = 0;
                JsonNode cityToursFile = objectMapper.readTree(new File(filePath));
                tourNumber = cityToursFile.size();

                if (!filePath.equals("")) {

                    // create a new tour node
                    ObjectNode newTour = objectMapper.createObjectNode();
                    newTour.put("tourNr", tourNumber + 1);

                    // Tour node

                    System.out.println(ConsoleColors.YELLOW_BOLD + "◆ Enter the location: " );
                    newTour.put("location", scanner.nextLine());

                    System.out.println("◆ Enter the date: ");
                    newTour.put("date", scanner.nextLine());

                    System.out.println("◆ Enter the time: ");
                    newTour.put("time", scanner.nextLine());

                    System.out.println("◆ Enter the description: ");
                    newTour.put("description", scanner.nextLine());

                    System.out.println("◆ Enter the price: " + ConsoleColors.RESET);
                    newTour.put("price", scanner.nextLine() + " kr");

                    newTour.put("status", "unavailable");
                    newTour.put("guideID", "");
                    newTour.put("touristID", "");

                    // add the new tour node to the tours array
                    ArrayNode toursArray = (ArrayNode) objectMapper.readTree(new File(filePath));
                    toursArray.add(newTour);

                    // write the updated tours array back to the JSON file
                    objectMapper.writeValue(new File(filePath), toursArray);

                    System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "◆ Tour added successfully ✔" + ConsoleColors.RESET);
                }

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
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "◆ Oops, looks like there is no tours in the selected city! " + ConsoleColors.RESET);
                } else if (toursArray.size() == 0) {
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "◆ Oops, looks like there is no tours available! " + ConsoleColors.RESET);
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
                        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "◆ Tour deleted successfully ✔");
                    } else {
                        System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "◆ Invalid Tour number \n" + "◆ Tour not found, try a smaller number!" +  ConsoleColors.RESET);
                    }
                }
            } else {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "◆ Invalid choice!" + ConsoleColors.RESET);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void editTour() {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            String filePath = "src/main/java/JSON_files/available_cities.json";
            JsonNode availableCities = objectMapper.readTree(new File(filePath));
            ArrayNode citiesArray = (ArrayNode) availableCities;

            // Display available cities for the admin to choose
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n--------------------");
            System.out.println("| Available cities |");
            System.out.println("--------------------" + ConsoleColors.RESET);

            for (JsonNode city : citiesArray) {
                System.out.println(ConsoleColors.ORANGE_BOLD_BRIGHT + "[" + city.get("cityNr").asInt() + "] " + city.get("cityName").asText() + ConsoleColors.RESET);
            }

            Scanner scanner = new Scanner(System.in);
            System.out.println(ConsoleColors.YELLOW + "||> Enter the city number to edit tours, [0 -> Go Back]: " + ConsoleColors.RESET);
            int selectedCityNumber = Integer.parseInt(scanner.nextLine());
            if(selectedCityNumber == 0){
                UserPanel.adminPanel();
            }else {

            // Find the selected city
            JsonNode selectedCity = null;
            for (JsonNode city : citiesArray) {
                if (city.get("cityNr").asInt() == selectedCityNumber) {
                    selectedCity = city;
                    break;
                }
            }

            if (selectedCity != null) {
                String cityFilePath = selectedCity.get("jsonFilePath").asText();
                ArrayNode toursArray = (ArrayNode) objectMapper.readTree(new File(cityFilePath));

                // Display tours for the selected city
                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n------------------------------------");
                System.out.println("| Current available tours in " + selectedCity.get("cityName").asText() + " |");
                System.out.println("------------------------------------" + ConsoleColors.RESET);

                for (JsonNode tour : toursArray) {
                    if (tour instanceof ObjectNode) {
                        ObjectNode tourObject = (ObjectNode) tour;

                        System.out.println(ConsoleColors.YELLOW_UNDERLINED + ConsoleColors.YELLOW_BOLD_BRIGHT +
                                "\n[*] Tour number " + ConsoleColors.RED_BOLD_BRIGHT + tourObject.get("tourNr").asInt() +
                                ConsoleColors.RESET +
                                "\n" + ConsoleColors.RED_BOLD_BRIGHT + "[*] " + ConsoleColors.RESET + "Location: " + tourObject.get("location").asText() +
                                "\n" + ConsoleColors.RED_BOLD_BRIGHT + "[*] " + ConsoleColors.RESET + "Date: " + tourObject.get("date").asText() +
                                "\n" + ConsoleColors.RED_BOLD_BRIGHT + "[*] " + ConsoleColors.RESET + "Time: " + tourObject.get("time").asText() +
                                "\n" + ConsoleColors.RED_BOLD_BRIGHT + "[*] " + ConsoleColors.RESET + "Description: " + tourObject.get("description").asText() +
                                "\n" + ConsoleColors.RED_BOLD_BRIGHT + "[*] " + ConsoleColors.RESET + "Price: " + tourObject.get("price").asText() +
                                "\n" + ConsoleColors.RED_BOLD_BRIGHT + "[*] " + ConsoleColors.RESET + "touristID: " + tourObject.get("touristID").asText() +
                                "\n" + ConsoleColors.RED_BOLD_BRIGHT + "[*] " + ConsoleColors.RESET + "guideID: " + tourObject.get("guideID").asText() +
                                "\n" + ConsoleColors.RED_BOLD_BRIGHT + "[*] " + ConsoleColors.RESET + "Status: " + tourObject.get("status").asText() +
                                "\n------------------------------------");
                    }
                }

                // Prompt the admin to enter the tour number to edit
                System.out.println(ConsoleColors.YELLOW + "||> Enter the tour number you want to edit, [0 -> Go Back]: " + ConsoleColors.RESET);

                int selectedTourNumber = Integer.parseInt(scanner.nextLine());
                if (selectedTourNumber == 0){
                    showAllTours();
                }else {

                // Find the selected tour
                ObjectNode selectedTour = null;
                for (JsonNode tour : toursArray) {
                    if (tour.get("tourNr").asInt() == selectedTourNumber) {
                        selectedTour = (ObjectNode) tour;
                        break;
                    }
                }

                if (selectedTour != null) {
                    // Display current tour information
                    System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n-----------------------------");
                    System.out.println("| Current Tour Information |");
                    System.out.println("-----------------------------" + ConsoleColors.RESET);
                    System.out.println("[*] Location: " + selectedTour.get("location").asText());
                    System.out.println("[*] Date: " + selectedTour.get("date").asText());
                    System.out.println("[*] Time: " + selectedTour.get("time").asText());
                    System.out.println("[*] Description: " + selectedTour.get("description").asText());
                    System.out.println("[*] Price: " + selectedTour.get("price").asText());
                    System.out.println("[*] Status: " + selectedTour.get("status").asText());
                    System.out.println("[*] Guide ID: " + selectedTour.get("guideID").asText());
                    System.out.println("[*] Tourist ID: " + selectedTour.get("touristID").asText());

                    // Allow the admin to edit tour information
                    System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n-----------------------------");
                    System.out.println("| Enter the new information for the tour |");
                    System.out.println("-----------------------------" + ConsoleColors.RESET);


                    System.out.println(ConsoleColors.YELLOW_BOLD + "◆ Enter the location: ");
                    selectedTour.put("location", scanner.nextLine());

                    System.out.println("◆ Enter the date: ");
                    selectedTour.put("date", scanner.nextLine());

                    System.out.println("◆ Enter the time: ");
                    selectedTour.put("time", scanner.nextLine());

                    System.out.println("◆ Enter the description: ");
                    selectedTour.put("description", scanner.nextLine());

                    System.out.println("◆ Enter the price: " + ConsoleColors.RESET);
                    selectedTour.put("price", scanner.nextLine() + " kr");

                    // Write the updated tours array back to the JSON file
                    objectMapper.writeValue(new File(cityFilePath), toursArray);
                    System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "◆ Tour edited successfully ✔" + ConsoleColors.RESET);

                } else {
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "◆ Invalid tour number!" + ConsoleColors.RESET);
                }
                }
            } else {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "◆ Invalid city number!" + ConsoleColors.RESET);
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

        return false;
    }
}