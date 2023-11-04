package Classes;

import java.io.File;
import java.io.IOException;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;




public class userPanel {
    private static final List<String> favoriteTours = new ArrayList<>();

    public static void loginPanel() {
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n-------------------------------------------------------------");
        System.out.println("| Login by typing in the number of one of the options below |");
        System.out.println("-------------------------------------------------------------" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.ORANGE_BOLD_BRIGHT + "[1] Turist\n" +
                "[2] Guide / Company\n" +
                "[3] Admin" + ConsoleColors.RESET);
        Scanner userLoginOption = new Scanner(System.in);
        int  userInput = Integer.parseInt(userLoginOption.nextLine());

        switch (userInput) {
            case 1:
                loadingProgress();
                System.out.println(ConsoleColors.YELLOW + "You are now logged in as" + ConsoleColors.RED_BOLD_BRIGHT + " [Turist]" + ConsoleColors.RESET);
                turistNavigationOptions();
                break;
            case 2:
                loadingProgress();
                System.out.println(ConsoleColors.YELLOW + "You are now logged in as" + ConsoleColors.RED_BOLD_BRIGHT + " [Guide / Company]" + ConsoleColors.RESET);

                /*More code goes here, such as Guide Panel etc.*/
                break;
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
                searchAndDisplayCities();
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

    /* Method to get available cities from a JSON file,
        whenever a cities is being added to the json file it will dynamically be printed
    */
    public static void getAvailableCities(String file){
        try{
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode jsonCityFile = objectMapper.readTree(new File(file));
            if (jsonCityFile.isEmpty()){
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "|| Oops, looks like there is no cities available! ||" + ConsoleColors.RESET);
                turistNavigationOptions();
            }else {
                for (JsonNode city : jsonCityFile){
                    String cityNr = city.get("cityNr").asText();
                    String cityName = city.get("cityName").asText();

                    System.out.println(ConsoleColors.ORANGE_BOLD_BRIGHT + "[" + cityNr + "] " + cityName);
                }

            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /* Method to display tours based on the chosen city by the user,
        uses getAvailableCities() method to display available cities so the user can choose between.
    */
    public static void searchAndDisplayCities() {
        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + ConsoleColors.YELLOW_UNDERLINED + "Available Cities:" + ConsoleColors.RESET );
        String availableCities = "src/main/java/JSON_files/available_cities.json";
        getAvailableCities(availableCities);

        Scanner userInputScanner = new Scanner(System.in);

        System.out.println(ConsoleColors.YELLOW +"||> Enter the number of the city you want to explore, [0 -> Main menu]: " + ConsoleColors.RESET);
        int userChoice = Integer.parseInt(userInputScanner.nextLine());


        if (userChoice == 0) {
            turistNavigationOptions(); // Go back to the main menu
        } else {
            displayToursOfACity(userChoice);
        }
    }


    /* Method to display tours based on what city user chosen, it's displays tours dynamically,
        so whenever a new tour being added to a tour related json file it will be displayed.
    */
    public static void displayToursOfACity(int cityNumber) {
        Scanner userInputScanner = new Scanner(System.in);
        String availableCitiesFile = "src/main/java/JSON_files/available_cities.json";

        try {

            String cityName = "";
            String jsonTourFilePath = "";
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonCityFile = objectMapper.readTree(new File(availableCitiesFile));

            int totalCities = jsonCityFile.size();

            if (cityNumber == 0 ) {
                searchAndDisplayCities(); // Go back to the list of available cities
            } else if (cityNumber <= totalCities){
                for (JsonNode city : jsonCityFile) {
                    int cityNr = Integer.parseInt(city.get("cityNr").asText());
                    if (cityNumber == cityNr) {
                        jsonTourFilePath = city.get("jsonFilePath").asText();
                        cityName = city.get("cityName").asText();
                    }
                }

                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n------------------------------------");
                System.out.println("| Available tours in " + cityName);
                System.out.println("------------------------------------" + ConsoleColors.RESET);

                getToursFromJSONFile(jsonTourFilePath);



                // TODO: implement a function that displays ([1] Add tour to favorites [2] Book tour) and the what comes inside those options


                JsonNode jsonTourFile = objectMapper.readTree(new File(jsonTourFilePath));
                int totalTours = jsonTourFile.size();


                int userChoice = -1;
                Scanner userInput = new Scanner(System.in);
                while (userChoice < totalTours || userChoice > totalTours){
                    System.out.println(ConsoleColors.YELLOW + "||> Enter the number of the tour you want to explore, [0 -> Go Back]: " + ConsoleColors.RESET);
                    int selectedTour = Integer.parseInt(userInputScanner.nextLine());
                    if (selectedTour == 0) {
                        searchAndDisplayCities(); // Go back to the list of available cities
                    } else if (selectedTour <= totalTours && selectedTour >= 1){
                        bookOrAddTourToFavorite(selectedTour, cityName, jsonTourFilePath);

                /*while (userChoice < 1 || userChoice > 2) {
                    System.out.println(ConsoleColors.YELLOW + "||> Enter your choice [1-2] or [0 -> Go Back]: " + ConsoleColors.RESET);
                    userChoice = Integer.parseInt(userInputScanner.nextLine());


                    if (userChoice == 1) {
                        String selectedTourInfo = cityName + " - Tour " + selectedTour;
                        favoriteTours.add(selectedTourInfo);
                        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "◆ Tour added to favorites ✔" + ConsoleColors.RESET);
                        searchAndDisplayCities(); // Automatically go back to the list of available cities
                    } else if (userChoice == 2) {
                        // Implement payment logic here
                        // You can add your payment code here
                        System.out.println("Payment & booking logic goes here.");
                    } else if (userChoice == 0) {
                        searchAndDisplayCities();
                    }
                }*/
                    }
                }

            } else {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "◆ Invalid city number, enter a valid number!" + ConsoleColors.RESET);
                searchAndDisplayCities();
            }
        } catch (IOException e){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "◆ Requested file not found" + ConsoleColors.RESET);

        }
    }

    // TODO: reimplement the function to read from the favorite_tours.json
    public static void showFavoriteTours() {
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n----------------------------------");
        System.out.println("| Your Favorite Tours |");
        System.out.println("----------------------------------" + ConsoleColors.RESET);

        if (favoriteTours.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "◆ You have no favorite tours saved." + ConsoleColors.RESET);
            turistNavigationOptions();
        } else {
            int tourIndex = 1;
            for (String tour : favoriteTours) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "[" + tourIndex + "] " + ConsoleColors.ORANGE_BOLD_BRIGHT + tour + ConsoleColors.RESET);
                tourIndex++;
            }

            // Ask the user to select a tour to view
            Scanner userInputScanner = new Scanner(System.in);
            System.out.print("Enter the number of the tour you want to view details [0 -> Main menu]: ");
            int userChoice = Integer.parseInt(userInputScanner.nextLine());

            if (userChoice == 0) {
                turistNavigationOptions(); // Go back to the main menu
            } else if (userChoice > 0 && userChoice <= favoriteTours.size()) {
                // User selected a valid tour
                String selectedTourInfo = favoriteTours.get(userChoice - 1);
                displayTourDetails(selectedTourInfo);
            } else {
                System.out.println(ConsoleColors.RED + "◆ Invalid choice! Enter 1 or 2." + ConsoleColors.RESET);
                showFavoriteTours();
            }
        }
    }


    public static void displayTourDetails(String tourInfo) {
        String[] tourParts = tourInfo.split(" - Tour ");

        if (tourParts.length != 2) {
            System.out.println(ConsoleColors.RED + "◆ Invalid tour format!" + ConsoleColors.RESET);
            return;
        }

        String cityName = tourParts[0];
        int tourNumber = Integer.parseInt(tourParts[1]);

        String jsonFilePath;

        // Map city names to JSON file paths
        switch (cityName) {
            case "Oslo":
                jsonFilePath = "src/main/java/JSON_files/oslo_tours.json";
                break;
            case "Bergen":
                jsonFilePath = "src/main/java/JSON_files/bergen_tours.json";
                break;
            case "Kristiansand":
                jsonFilePath = "src/main/java/JSON_files/kristiansand_tours.json";
                break;
            case "Halden":
                jsonFilePath = "src/main/java/JSON_files/halden_tours.json";
                break;
            default:
                System.out.println(ConsoleColors.RED + "◆ Invalid city name!" + ConsoleColors.RESET);
                return;
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonFile = objectMapper.readTree(new File(jsonFilePath));

            if (tourNumber < 1 || tourNumber > jsonFile.size()) {
                System.out.println(ConsoleColors.RED + "◆ Invalid tour number!" + ConsoleColors.RESET);
                return;
            }

            JsonNode selectedTour = jsonFile.get(tourNumber - 1);
            String location = selectedTour.get("location").asText();
            String date = selectedTour.get("date").asText();
            String time = selectedTour.get("time").asText();
            String description = selectedTour.get("description").asText();
            String price = selectedTour.get("price").asText();

            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\nTour Details for " + cityName + " - Tour " + tourNumber + ConsoleColors.RESET);
            System.out.println("Location: " + location);
            System.out.println("Date: " + date);
            System.out.println("Time: " + time);
            System.out.println("Description: " + description);
            System.out.println("Price: " + price);

            // Ask the user to choose between going back to the main menu or proceeding to payment
            Scanner userInputScanner = new Scanner(System.in);
            int choice = -1;

            while (choice < 1 || choice > 2) {
                System.out.println("1. Back to Main Menu");
                System.out.println("2. Go to Payment");
                System.out.print("Enter your choice [1-2]: ");
                choice = Integer.parseInt(userInputScanner.nextLine());

                // TODO: change it to switch-case
                if (choice == 1) {
                    // Go back to the main menu
                    turistNavigationOptions();
                } else if (choice == 2) {
                    // Proceed to payment logic
                    //TODO: payment logic function
                    System.out.println("Payment logic goes here.");
                } else {
                    System.out.println(ConsoleColors.RED + "◆ Invalid choice! Enter 1 or 2." + ConsoleColors.RESET);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // implementation of getToursFromJSONFile() function, now data from JSON files can be read
    public static void getToursFromJSONFile(String file){
        try{
            // ObjectMapper to read from the JSON file
            ObjectMapper objectMapper = new ObjectMapper();

            //read JSON-filen
            JsonNode jsonFile = objectMapper.readTree(new File(file));
            // if the json-file is empty (which means if there is no tours available) a message will be printed to the user
            if (jsonFile.isEmpty()){
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "◆ Oops, looks like there is no tours available! " + ConsoleColors.RESET);
                searchAndDisplayCities();
            }else {
                //Iterate throw json file and get information
                for(JsonNode tour : jsonFile){
                    String tourNr = tour.get("tourNr").asText();
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

    // Under dev
    public static void bookOrAddTourToFavorite(int selectedTour, String cityName, String JSONFilepath){
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n---------------------------------");
        System.out.println("|    You selected tour nr. " + selectedTour + "    |");
        System.out.println("---------------------------------" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.ORANGE_BOLD_BRIGHT
                + "[1] Add tour to favorites"
                + "\n[2] Book tour"
                + ConsoleColors.RESET
        );


        int userChoice = -1;
        Scanner userInput = new Scanner(System.in);
        while (userChoice < 1 || userChoice > 2) {
            System.out.println(ConsoleColors.YELLOW + "||> Enter your choice [1-2] or [0 -> Go Back]: " + ConsoleColors.RESET);
            userChoice = Integer.parseInt(userInput.nextLine());
            if (userChoice == 1){
                // TODO: implement a method that takes selected tours info and writes it to favoriteTours.json
                addTourToFavorite(selectedTour, JSONFilepath);
                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "◆ Tour added to favorites ✔" + ConsoleColors.RESET);
                searchAndDisplayCities(); // Automatically go back to the list of available cities
            } else if(userChoice == 2){
                System.out.println("Hello");
            }
        }

    }

    public static void addTourToFavorite(int tourNr, String JSONFilepath){
        /*
         * get the tourNr and the filepath, iterate throw the filepath find the tour the matches the tourNr and get all values then write those value to favorite_tours.json*/
    }
    public static void adminPanel() {
        boolean isAdminRunning = true;
        Scanner scanner = new Scanner(System.in);

        while (isAdminRunning) {
            System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "You are now logged in as an Admin." + ConsoleColors.RESET);
            System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "[1] Add a tour\n" +
                    "[2] Delete a tour\n" +
                    "[3] Go back to the main menu" + ConsoleColors.RESET);

            int adminChoice = Integer.parseInt(scanner.nextLine());
            switch (adminChoice) {
                case 1:
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT +"Number 1 for the confirm: ");
                    int tourNumber = Integer.parseInt(scanner.nextLine());
                    String file = "src/main/java/JSON_files/available_cities.json";
                    getAvailableCities(file);
                    Admin.addTour(tourNumber);
                    break;

                case 2:
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT +"Available Tours: ");
                    String fileForDelete = "src/main/java/JSON_files/available_cities.json";
                    getAvailableCities(fileForDelete);
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT +"Enter the city number for the tour you want to delete: ");
                    int cityNumberToDelete = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter the tour number you want to delete: ");
                    int tourNumberToDelete = Integer.parseInt(scanner.nextLine());
                    Admin.deleteTour(cityNumberToDelete, tourNumberToDelete);
                    break;

                case 3:
                    // Go back to the main menu
                    userPanel.turistNavigationOptions();
                    isAdminRunning = false;
                    break;

                default:
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Invalid choice!" + ConsoleColors.RESET);
                    // adminPanel();
                    break;
            }
        }
    }

}

