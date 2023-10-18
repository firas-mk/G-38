package Classes;

import java.io.File;
import java.io.IOException;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Functions {
    private static final List<String> favoriteTours = new ArrayList<>();

    public static void loginPanel() {
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n-------------------------------------------------------------");
        System.out.println("| Login by typing in the number of one of the options below |");
        System.out.println("-------------------------------------------------------------" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.ORANGE_BOLD_BRIGHT + "[1] Turist\n" +
                "[2] Guide / Company\n" +
                "[3] Admin" + ConsoleColors.RESET);
        Scanner userLoginOption = new Scanner(System.in);
        Integer  userInput = Integer.valueOf(userLoginOption.nextLine());

        switch (userInput) {
            case 1:
                loadingProgress();
                System.out.println(ConsoleColors.YELLOW + "You are now logged in as" + ConsoleColors.RED_BOLD_BRIGHT + " [Turist]" + ConsoleColors.RESET);
                turistNavigationOptions();
                break;
            case 2:
                loadingProgress();
                System.out.println(ConsoleColors.YELLOW + "You are now logged in as" + ConsoleColors.RED_BOLD_BRIGHT + " [Guide / Company]" + ConsoleColors.RESET);
                break;
            /*More code goes here, such as Guide Panel etc.*/
            case 3:
                loadingProgress();
                System.out.println(ConsoleColors.YELLOW + "You are now logged in as" + ConsoleColors.RED_BOLD_BRIGHT + " [Admin]" + ConsoleColors.RESET);
                break;
            /*More code goes here, such as Admin Panel etc.*/
            default:
                System.out.println(ConsoleColors.RED + "▢ invalid number! Enter 1, 2 or 3 " + ConsoleColors.RESET);
                loginPanel();
            }
        }
    public static void loadingProgress(){
        int totalTasks = 5;

        for (int i = 0; i < totalTasks; i++) {
            int progress = (i * 100) / totalTasks;
            System.out.print("Logging in: " + progress + "% [");

            // Simuler en lastelinje med "#" som fylles gradvis
            int numberOfHashes = (i * 10) / totalTasks;
            for (int j = 0; j < numberOfHashes; j++) {

                System.out.print("#");
            }

            // Fyll resten med mellomrom
            for (int j = numberOfHashes; j < 10; j++) {
                System.out.print(" ");
            }

            System.out.print("]\r");

            try {
                Thread.sleep(500); // Simuler en forsinkelse i arbeidet
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     *  <b>turistNavigatonOptions()</b> is a function that gives the user who's logged in as "Turist" severeal options to chose
     *  from in order to navigate further
     *  <p>
     *  We handle it as a "navigation panel/bar" or "main menu" where the the user can choose between the options in order to:
     *  </p>
     *  <ul>
     *      <li> search for tours
     *      <li> check / show their favorite list
     *      <li> logout
     *      <li> etc.
     * </ul>
    */ //DESC-Javadoc ---> turistNavigatonOptions()
    public static void turistNavigationOptions(){
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n-----------------------------------------------------------------");
        //System.out.println("| In order to navigate, choose one of the options below:    |");
        System.out.println("| You are now in the main menu, choose one of the options below |");
        System.out.println("-----------------------------------------------------------------" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.ORANGE_BOLD_BRIGHT + "[1] Search Tours\n" +
                "[2] Show Favorite\n" +
                "[3] Log Out" + ConsoleColors.RESET);
        Scanner userLoginOption = new Scanner(System.in);
        int userInput = Integer.parseInt(userLoginOption.nextLine());


        switch (userInput) {
            case 1:
                searchAndDisplayTours();
                break;
            case 2:

                showFavoriteTours(); // Show favorite tours
                break;

            case 3:
                // (KHALIL) NEW: log out logic has been implemented
                System.out.println(ConsoleColors.YELLOW + "|| You are successfully logged out, See you later :) ||" + ConsoleColors.RESET);
                Scanner userInputScanner = new Scanner(System.in);
                System.out.println(ConsoleColors.YELLOW +"||> Do you want to login? [y/n]: " + ConsoleColors.RESET);
                String userChoice = String.valueOf(userInputScanner.nextLine());
                if(userChoice.equals("y") || userChoice.equals("Y")){
                    loginPanel();
                }else {
                    System.out.println(ConsoleColors.YELLOW + "Thank you for using Tourly. See you later ;)" + ConsoleColors.RESET);
                }
                break;
            default:
                System.out.println(ConsoleColors.RED + "◆ Invalid number! Enter 1, 2, or 3 " + ConsoleColors.RESET);
                turistNavigationOptions();
        }
    }

    // Method to display tours for a selected city
// Implementation of displayToursForCity method
    public static void searchAndDisplayTours() {
        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + ConsoleColors.YELLOW_UNDERLINED + "Available Cities:" + ConsoleColors.RESET + ConsoleColors.ORANGE_BOLD_BRIGHT
                + "\n[0] Back to Main Menu"
                + "\n[1] Oslo"
                + "\n[2] Bergen"
                + "\n[3] Kristiansand"
                + "\n[4] Halden" + ConsoleColors.RESET
        );

        Scanner userInputScanner = new Scanner(System.in);
        int selectedCity = -1;

        while (selectedCity < 0 || selectedCity > 4) {
            System.out.println(ConsoleColors.YELLOW +"||> Enter the number of the city you want to explore [0-4]: " + ConsoleColors.RESET);
            selectedCity = Integer.parseInt(userInputScanner.nextLine());
        }

        if (selectedCity == 0) {
            turistNavigationOptions(); // Go back to the main menu
        } else {
            displayToursForCity(selectedCity);
        }
    }

    public static void displayToursForCity(int cityNumber) {
        Scanner userInputScanner = new Scanner(System.in);

        if (cityNumber == 0) {
            searchAndDisplayTours(); // Go back to the list of available cities
        } else {
            String cityName;
            String jsonFilePath;

            switch (cityNumber) {
                case 1: // Oslo
                    cityName = "Oslo";
                    jsonFilePath = "src/main/java/JSON_files/oslo_tours.json";
                    break;
                case 2: // Bergen
                    cityName = "Bergen";
                    jsonFilePath = "src/main/java/JSON_files/bergen_tours.json";
                    break;
                case 3: // Kristiansand
                    cityName = "Kristiansand";
                    jsonFilePath = "src/main/java/JSON_files/kristiansand_tours.json";
                    break;
                case 4: // Halden
                    cityName = "Halden";
                    jsonFilePath = "src/main/java/JSON_files/halden_tours.json";
                    break;
                default:
                    System.out.println(ConsoleColors.RED + "◆ Invalid number! Enter 0, 1, 2, 3, or 4 " + ConsoleColors.RESET);
                    searchAndDisplayTours();
                    return;
            }

            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n---------------------------------");
            System.out.println("| Available tours in " + cityName + " |");
            System.out.println("---------------------------------" + ConsoleColors.RESET);
            getToursFromJSONfile(jsonFilePath);

            int selectedTour = -1;

            while (selectedTour < 0 || selectedTour > 3) {
                System.out.println(ConsoleColors.YELLOW + "||> Enter the number of the tour you want to explore [1-3] or [0] Back to Main Menu: " + ConsoleColors.RESET);
                selectedTour = Integer.parseInt(userInputScanner.nextLine());
            }

            if (selectedTour == 0) {
                searchAndDisplayTours(); // Go back to the list of available cities
            } else {
                System.out.println("You selected tour " + selectedTour);
                System.out.println("1. Add to Favorites");
                System.out.println("2. Go to Payment");
                int choice = -1;

                while (choice < 1 || choice > 2) {
                    System.out.println("Enter your choice [1-2]: ");
                    choice = Integer.parseInt(userInputScanner.nextLine());

                    if (choice == 1) {
                        String selectedTourInfo = cityName + " - Tour " + selectedTour;
                        favoriteTours.add(selectedTourInfo);
                        System.out.println("Tour added to favorites!");
                        searchAndDisplayTours(); // Automatically go back to the list of available cities
                    } else if (choice == 2) {
                        // Implement payment logic here
                        // You can add your payment code here
                        System.out.println("Payment logic goes here.");
                    } else {
                        System.out.println(ConsoleColors.RED + "◆ Invalid choice! Enter 1 or 2." + ConsoleColors.RESET);
                    }
                }
            }
        }
    }

    public static void showFavoriteTours() {
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n----------------------------------");
        System.out.println("| Your Favorite Tours |");
        System.out.println("----------------------------------" + ConsoleColors.RESET);

        if (favoriteTours.isEmpty()) {
            System.out.println("You have no favorite tours saved.");
        } else {
            int tourIndex = 1;
            for (String tour : favoriteTours) {
                System.out.println("[" + tourIndex + "] " + tour);
                tourIndex++;
            }

            // Ask the user to select a tour to view
            Scanner userInputScanner = new Scanner(System.in);
            System.out.print("Enter the number of the tour you want to view details (0 to go back): ");
            int choice = Integer.parseInt(userInputScanner.nextLine());

            if (choice == 0) {
                turistNavigationOptions(); // Go back to the main menu
            } else if (choice > 0 && choice <= favoriteTours.size()) {
                // User selected a valid tour
                String selectedTourInfo = favoriteTours.get(choice - 1);
                String[] tourDetails = selectedTourInfo.split(" - Tour ");
                int cityNumber = 0; // You need to parse the city number from the tourDetails array
                int selectedTourNumber = Integer.parseInt(tourDetails[1]);

                // Now you can display the tour details based on the cityNumber and selectedTourNumber
                displayTourDetails(cityNumber, selectedTourNumber);
            } else {
                System.out.println("Invalid choice. Please enter a valid number.");
                showFavoriteTours();
            }
        }
    }

    public static void displayTourDetails(int cityNumber, int tourNumber) {
        String cityName;
        String jsonFilePath;

        switch (cityNumber) {
            case 1: // Oslo
                cityName = "Oslo";
                jsonFilePath = "src/main/java/JSON_files/oslo_tours.json";
                break;
            case 2: // Bergen
                cityName = "Bergen";
                jsonFilePath = "src/main/java/JSON_files/bergen_tours.json";
                break;
            case 3: // Kristiansand
                cityName = "Kristiansand";
                jsonFilePath = "src/main/java/JSON_files/kristiansand_tours.json";
                break;
            case 4: // Halden
                cityName = "Halden";
                jsonFilePath = "src/main/java/JSON_files/halden_tours.json";
                break;
            default:
                System.out.println(ConsoleColors.RED + "◆ Invalid city number!" + ConsoleColors.RESET);
                return;
        }

        // Implement code to read and display details of the selected tour
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonFile = objectMapper.readTree(new File(jsonFilePath));

            // Your code to find and display tour details goes here
            for (JsonNode tour : jsonFile) {
                // Check if this is the selected tour based on tourNumber
                // Display the details if found
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // implementation of getToursFromJSONFile() function, now data from JSON files can be read
    public static void getToursFromJSONfile(String file){
        try{
            // ObjectMapper to read from the JSON file
            ObjectMapper objectMapper = new ObjectMapper();

            //read JSON-filen
            JsonNode jsonFile = objectMapper.readTree(new File(file));
            // if the json-file is empty (which means if there is no tours available) a message will be printed to the user
            if (jsonFile.isEmpty()){
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "|| Oops, looks like there is no tours available! ||" + ConsoleColors.RESET);
                searchAndDisplayTours();
            }else {
                Integer tourNr = 0;
                //Iterate throw and get information
                for(JsonNode tour : jsonFile){
                    tourNr++;
                    String location = tour.get("location").asText();
                    String date = tour.get("date").asText();
                    String time = tour.get("time").asText();
                    String description = tour.get("description").asText();
                    String price = tour.get("price").asText();

                    System.out.println(
                            "\n"+ ConsoleColors.YELLOW_UNDERLINED + ConsoleColors.YELLOW_BOLD_BRIGHT+ "[*] Tour number " + ConsoleColors.RED_BOLD_BRIGHT +  tourNr + ConsoleColors.RESET +
                                    "\n" + ConsoleColors.RED_BOLD_BRIGHT +"[*] " + ConsoleColors.RESET + "Location: " + location +
                                    "\n" + ConsoleColors.RED_BOLD_BRIGHT +"[*] " + ConsoleColors.RESET + "Date: " + date +
                                    "\n" + ConsoleColors.RED_BOLD_BRIGHT +"[*] " + ConsoleColors.RESET + "Time: " + time +
                                    "\n" + ConsoleColors.RED_BOLD_BRIGHT +"[*] " + ConsoleColors.RESET + "Description: " + description +
                                    "\n" + ConsoleColors.RED_BOLD_BRIGHT +"[*] " + ConsoleColors.RESET + "Price: " + price
                    );
                }
            }} catch (IOException e) {
            e.printStackTrace();
        }


    }


}

