package Classes;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;


public class UserPanel {
    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static void loadingProgress(){
        int totalTasks = 5;

        for (int i = 0; i < totalTasks; i++) {
            int progress = (i * 100) / totalTasks;
            System.out.print("Logging in: " + progress + "% [");

            // Simulate a loading line with "#" that fills gradually
            int numberOfHashes = (i * 10) / totalTasks;
            for (int j = 0; j < numberOfHashes; j++) {

                System.out.print("#");
            }

            // Fill the rest with space
            for (int j = numberOfHashes; j < 10; j++) {
                System.out.print(" ");
            }

            System.out.print("]\r");

            try {
                Thread.sleep(500); // simulate a delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

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
                touristPanel();
                break;
            case 2:
                loadingProgress();
                System.out.println(ConsoleColors.YELLOW + "You are now logged in as" + ConsoleColors.RED_BOLD_BRIGHT + " [Guide / Company]" + ConsoleColors.RESET);
                Guide guide = new Guide("GuideId", "Guide Name", "Contact Info");
                GuidePanel guidePanel = new GuidePanel(guide);
                guidePanel.guideMenu();
                break;
            case 3:
                loadingProgress();
                System.out.println(ConsoleColors.YELLOW + "You are now logged in as" + ConsoleColors.RED_BOLD_BRIGHT + " [Admin]" + ConsoleColors.RESET);
                Admin admin = new Admin();
                adminPanel();
                break;
            /*More code goes here, such as Admin Panel etc.*/
            default:
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "◆ invalid number! Enter 1, 2 or 3 " + ConsoleColors.RESET);
                loginPanel();
        }
    }

    /*       --- [Tourist] related functions ---          */

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
    public static void touristPanel(){
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
                    getFavoriteTours(); // Show favorite tours
                    break;

                case 3:
                    logOut();

                default:
                    System.out.println(ConsoleColors.RED + "◆ Invalid number! Enter 1, 2, or 3 " + ConsoleColors.RESET);
                    touristPanel();
                    break;
            }


    }

    /* Method to get available cities from a JSON file,
        whenever a cities is being added to the json file it will dynamically be printed
    */
    public static void getAvailableCities(String file){
        try{


            JsonNode jsonCityFile = objectMapper.readTree(new File(file));
            if (jsonCityFile.isEmpty()){
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "|| Oops, looks like there is no cities available! ||" + ConsoleColors.RESET);
                touristPanel();
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
            touristPanel(); // Go back to the main menu
        } else {
            displayToursOfACity(userChoice);
        }
    }


    /* Method to display tours based on what city user chosen, it's displays tours dynamically,
        so whenever a new tour being added to a tour related json file it will be displayed.
    */
    public static void displayToursOfACity(int cityNumber) {
        String availableCitiesFile = "src/main/java/JSON_files/available_cities.json";

        try {

            String cityName = "";
            String jsonTourFilePath = "";
            int cityNr;
            JsonNode jsonCityFile = objectMapper.readTree(new File(availableCitiesFile));

            int totalCities = jsonCityFile.size();

            if (cityNumber == 0 ) {
                searchAndDisplayCities(); // Go back to the list of available cities
            } else if (cityNumber <= totalCities){
                for (JsonNode city : jsonCityFile) {
                    cityNr = city.get("cityNr").asInt();
                    if (cityNumber == cityNr) {
                        jsonTourFilePath = city.get("jsonFilePath").asText();
                        cityName = city.get("cityName").asText();

                    }
                }

                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n------------------------------------");
                System.out.println("| Available tours in " + cityName);
                System.out.println("------------------------------------" + ConsoleColors.RESET);

                getToursFromJSONFile(jsonTourFilePath);

                JsonNode jsonTourFile = objectMapper.readTree(new File(jsonTourFilePath));

                int totalTours = jsonTourFile.size();
                int userChoice = -1;
                Scanner userInput = new Scanner(System.in);
                while (userChoice < 0 || userChoice > totalTours){
                    System.out.println(ConsoleColors.YELLOW + "||> Enter the number of the tour you want to explore, [0 -> Go Back]: " + ConsoleColors.RESET);
                    userChoice = Integer.parseInt(userInput.nextLine());
                    if (userChoice == 0) {
                        searchAndDisplayCities();// Go back to the list of available cities
                    } else if (userChoice <= totalTours && userChoice >= 1){
                        bookOrAddTourToFavorite(userChoice, cityNumber, cityName, jsonTourFilePath);

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

    // implementation of getToursFromJSONFile() function, now data from JSON files can be read
    public static void getToursFromJSONFile(String file){
        try{

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
                            "\n"+ ConsoleColors.YELLOW_UNDERLINED + ConsoleColors.YELLOW_BOLD_BRIGHT + "[*] Tour number " + ConsoleColors.RED_BOLD_BRIGHT +  tourNr + ConsoleColors.RESET +
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
    public static void bookTour(int tourNr, String cityName, String JSONFilepath, String bookingContext){
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Enable pretty printing

        try {
            // bookings.json
            String bookingsJsonFilepath = "src/main/java/JSON_files/bookings.json";
            JsonNode bookingFile = objectMapper.readTree(new File(bookingsJsonFilepath));
            ArrayNode bookingsArray = (ArrayNode) objectMapper.readTree(new File(bookingsJsonFilepath));


            // favorite_tours.json, used in case user books a tour from the favored list
            String favoriteToursJsonFilepath = "src/main/java/JSON_files/favorite_tours.json";
            JsonNode favoriteToursFile = objectMapper.readTree(new File(favoriteToursJsonFilepath));
            JsonNode cityToursFile = objectMapper.readTree(new File(JSONFilepath));



            ObjectNode bookedTour = objectMapper.createObjectNode();
            JsonNode fileToBeUsed;
            switch (bookingContext) {
                case "Search":
                    fileToBeUsed = cityToursFile;
                    int tourNumber;
                    for (JsonNode tour : fileToBeUsed) {
                        tourNumber = tour.get("tourNr").asInt();
                        if (tourNr == tourNumber) {
                            assignJsonValues(bookingFile, bookedTour, tour, cityName);
                        }
                        // TODO: remove booked tour from favorite after booking
                    }
                    bookingsArray.add(bookedTour);
                    objectMapper.writeValue(new File(bookingsJsonFilepath), bookingsArray);
                    // TODO: simple payment logic
                    System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "◆ Tour booked successfully ✔" + ConsoleColors.RESET);
                    searchAndDisplayCities();
                    break;
                case "Favorite":
                    fileToBeUsed = favoriteToursFile;

                    for (JsonNode tour : fileToBeUsed) {
                        tourNumber = tour.get("tourNr").asInt();
                        if (tourNr == tourNumber) {
                            String city = tour.get("city").asText();
                            assignJsonValues(bookingFile, bookedTour, tour, city);
                            break;
                        }


                    }
                    bookingsArray.add(bookedTour);
                    objectMapper.writeValue(new File(bookingsJsonFilepath), bookingsArray);
                    // TODO: simple payment logic
                    System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "◆ Tour booked successfully ✔" + ConsoleColors.RESET);
                    touristPanel();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + bookingContext);
            }


        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void assignJsonValues(JsonNode bookingFile, ObjectNode bookedTour, JsonNode tour, String city) {
        int tourNumber;
        String location = tour.get("location").asText();
        String date = tour.get("date").asText();
        String time = tour.get("time").asText();
        String description = tour.get("description").asText();
        String price = tour.get("price").asText();

        tourNumber = bookingFile.size();
        bookedTour.put("tourNr", tourNumber + 1);
        bookedTour.put("city", city);
        bookedTour.put("location", location);
        bookedTour.put("date", date);
        bookedTour.put("time", time);
        bookedTour.put("description", description);
        bookedTour.put("price", price);
        bookedTour.put("touristID", "tourist ID");
    }


    public static void bookOrAddTourToFavorite(int selectedTour, int cityNr, String cityName, String JSONFilepath){
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n---------------------------------");
        System.out.println("|    You selected tour nr. " + selectedTour + "    |");
        System.out.println("---------------------------------" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.ORANGE_BOLD_BRIGHT
                + "[1] Add tour to favorites\n"
                + "[2] Book tour"
                + ConsoleColors.RESET
        );

        int userChoice = -1;
        Scanner userInput = new Scanner(System.in);
        while (userChoice < 0 || userChoice > 2) {
            System.out.println(ConsoleColors.YELLOW + "||> Enter your choice, [0 -> Go Back]: " + ConsoleColors.RESET);
            userChoice = Integer.parseInt(userInput.nextLine());
            switch (userChoice){
                case 0:
                    displayToursOfACity(cityNr);
                    break;
                case 1:
                    addTourToFavorite(selectedTour, cityNr, cityName, JSONFilepath);
                    searchAndDisplayCities(); // Automatically go back to the list of available cities
                    break;
                case 2:
                    bookTour(selectedTour, cityName, JSONFilepath,"Search");
                    break;

            }

        }
        

    }
    public static void bookOrRemoveTourFromFavorite(int selectedTour, String cityName, String JSONFilepath){
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n---------------------------------");
        System.out.println("|    You selected tour nr. " + selectedTour + "    |");
        System.out.println("---------------------------------" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.ORANGE_BOLD_BRIGHT
                + "[1] Remove tour from favorites\n"
                + "[2] Book tour"
                + ConsoleColors.RESET
        );
        int userChoice = -1;
        Scanner userInput = new Scanner(System.in);
        while (userChoice < 0 || userChoice > 2) {
            System.out.println(ConsoleColors.YELLOW + "||> Enter your choice, [0 -> Go Back]: " + ConsoleColors.RESET);
            userChoice = Integer.parseInt(userInput.nextLine());
            switch (userChoice){
                case 0:
                    getFavoriteTours();
                    break;
                case 1:
                    removeTourFromFavorite();
                    searchAndDisplayCities(); // Automatically go back to the list of available cities
                    break;
                case 2:
                    bookTour(selectedTour, cityName, JSONFilepath,"Favorite");
                    break;

            }

        }

    }
    public static void addTourToFavorite(int tourNr, int cityNr, String cityName, String JSONFilepath){
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Enable pretty printing
        /*
         * get the tourNr and the filepath, iterate throw the filepath find the tour the matches the tourNr and get all values then write those value to favorite_tours.json
         */
        try {
            String favoriteJsonFilePath = "src/main/java/JSON_files/favorite_tours.json";
            JsonNode favoriteToursJsonFile = objectMapper.readTree(new File(favoriteJsonFilePath));
            ArrayNode favoriteToursArray = (ArrayNode) objectMapper.readTree(new File(favoriteJsonFilePath));

            JsonNode cityToursJsonFile = objectMapper.readTree(new File(JSONFilepath));
            ObjectNode favoredTour = objectMapper.createObjectNode();

            String location = "";
            int tourNumber;
            for (JsonNode tour : cityToursJsonFile) {
                tourNumber = tour.get("tourNr").asInt();
                if (tourNumber == tourNr){
                    location = tour.get("location").asText();
                    String date = tour.get("date").asText();
                    String time = tour.get("time").asText();
                    String description = tour.get("description").asText();
                    String price = tour.get("price").asText();

                    tourNumber = favoriteToursJsonFile.size();
                    favoredTour.put("tourNr", tourNumber + 1);
                    favoredTour.put("city", cityName);
                    favoredTour.put("cityNr", cityNr);
                    favoredTour.put("location", location);
                    favoredTour.put("date", date);
                    favoredTour.put("time", time);
                    favoredTour.put("description", description);
                    favoredTour.put("price", price);

                }
                for (JsonNode existedTour : favoriteToursJsonFile){
                    if (location.equals(existedTour.get("location").asText()) && cityName.equals(existedTour.get("city").asText())){
                        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "◆ Tour already exist in your favorite" + ConsoleColors.RESET);
                        bookOrAddTourToFavorite(tourNr, cityNr, cityName, JSONFilepath);
                    }
                }
            }
            favoriteToursArray.add(favoredTour);
            objectMapper.writeValue(new File(favoriteJsonFilePath), favoriteToursArray);
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "◆ Tour added to favorites ✔" + ConsoleColors.RESET);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void removeTourFromFavorite() {


    }
    public static void getFavoriteTours() {
        try{
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n----------------------------------");
            System.out.println("|       Your Favorite Tours      |");
            System.out.println("----------------------------------" + ConsoleColors.RESET);

            String favoriteToursJsonFile = "src/main/java/JSON_files/favorite_tours.json";
            JsonNode favoriteToursFile = objectMapper.readTree(new File(favoriteToursJsonFile));
            int tourNr;
            String city = "";
            String location;
            if (favoriteToursFile.isEmpty()) {
                System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "◆ You have no favorite tours saved." + ConsoleColors.RESET);
                touristPanel();
            }else {

                for (JsonNode tour : favoriteToursFile) {
                    tourNr = tour.get("tourNr").asInt();
                    city = tour.get("city").asText();
                    int cityNr = tour.get("cityNr").asInt();
                    location = tour.get("location").asText();
                    String date = tour.get("date").asText();
                    String time = tour.get("time").asText();
                    String description = tour.get("description").asText();
                    String price = tour.get("price").asText();
                    System.out.println(
                            "\n" + ConsoleColors.YELLOW_UNDERLINED + ConsoleColors.YELLOW_BOLD_BRIGHT + "[*] Tour number " + ConsoleColors.RED_BOLD_BRIGHT + tourNr + ConsoleColors.RESET +
                                    "\n" + ConsoleColors.RED_BOLD_BRIGHT + "[*] " + ConsoleColors.RESET + "City: " + city +
                                    "\n" + ConsoleColors.RED_BOLD_BRIGHT + "[*] " + ConsoleColors.RESET + "Location: " + location +
                                    "\n" + ConsoleColors.RED_BOLD_BRIGHT + "[*] " + ConsoleColors.RESET + "Date & Time: " + date + " - " + time +
                                    "\n" + ConsoleColors.RED_BOLD_BRIGHT + "[*] " + ConsoleColors.RESET + "Price: " + price
                    );
                }


            }


            int totalFavTours = favoriteToursFile.size();


            int userChoice = -1;
            Scanner userInput = new Scanner(System.in);
            while (userChoice < 0 || userChoice > totalFavTours){
                System.out.println(ConsoleColors.YELLOW + "||> Enter the number of the tour, [0 -> Go Back]: " + ConsoleColors.RESET);
                userChoice = Integer.parseInt(userInput.nextLine());
                if (userChoice == 0) {
                    touristPanel();// Go back to the list of available cities
                } else if (userChoice <= totalFavTours && userChoice >= 1){
                    bookOrRemoveTourFromFavorite(userChoice, city, favoriteToursJsonFile);


                }
            }


        } catch (Exception e) {
            e.printStackTrace();

        }


    }




    /*       --- [Admin] related functions ---          */
    public static void adminPanel() {
        boolean isAdminRunning = true;
        Scanner scanner = new Scanner(System.in);

        while (isAdminRunning) {
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n-----------------------------------------------------------------");
            System.out.println("| You are now in the main menu, choose one of the options below |");
            System.out.println("-----------------------------------------------------------------" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.ORANGE_BOLD_BRIGHT + "[1] Add a tour\n" +
                    "[2] Delete a tour\n" +
                    "[3] Log out" + ConsoleColors.RESET);

            int adminChoice = Integer.parseInt(scanner.nextLine());
            switch (adminChoice) {
                case 1:
                    //System.out.println(ConsoleColors.RED_BOLD_BRIGHT +"Number 1 for the confirm: ");
                    //int tourNumber = Integer.parseInt(scanner.nextLine());
                    String file = "src/main/java/JSON_files/available_cities.json";
                    getAvailableCities(file);
                    Admin.addTour();
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
                    logOut();
                default:
                    System.out.println(ConsoleColors.RED + "◆ Invalid number! Enter 1, 2, or 3 " + ConsoleColors.RESET);
                    adminPanel();
                    break;

            }
        }
    }
//GuidePanel
    public static class GuidePanel {
        private Guide guide;

        public GuidePanel(Guide guide) {
            this.guide = guide;
        }

        public void guideMenu() {
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n-------------------------------------------------------------");
            System.out.println("| Guide Menu: Choose one of the options below                |");
            System.out.println("-------------------------------------------------------------" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.ORANGE_BOLD_BRIGHT + "[1] Show All Available Tours\n" +
                    "[2] Book a Tour\n" +
                    "[3] Log Out" + ConsoleColors.RESET);

            Scanner scanner = new Scanner(System.in);
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    searchAndDisplayCities();
                    break;
                case 2:
                    getAvailableCities("src/main/java/JSON_files/available_cities.json");
                    Guide.bookTour("GuideId");
                    break;
                case 3:
                    logOut();
                    break;
                default:
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "◆ Invalid number! Enter 1, 2, or 3 " + ConsoleColors.RESET);
                    guideMenu();
                    break;
            }
        }





    // A to do list

    // TODO: Book (done) and payment logic
    //      user can choose between:
    //          - VisaCard - regular bill
    //          - Klarna - regular bill
    //          - Vipps - fast pay
    // TODO: Add book option in favoriteList section so user can book a favored tour (Done)
    // TODO: Add "remove from favorite" option in favorite section so user can remove tours from favorite
    //      implement removeTourFromFavorite() function
}
    public static void logOut() {
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "◆ You are successfully logged out, See you later :) " + ConsoleColors.RESET);
        Scanner userInputScanner = new Scanner(System.in);
        System.out.println(ConsoleColors.YELLOW +"||> Do you want to login? [y/n]: " + ConsoleColors.RESET);
        String userChoice = String.valueOf(userInputScanner.nextLine());
        if(userChoice.equals("y") || userChoice.equals("Y")){
            loginPanel();

        }else if(userChoice.equals("n") || userChoice.equals("N")) {
            System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "◆ Thank you for using Tourly. See you later ;)" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "◆ Timed out due to invalid input, you are successfully logged out" + ConsoleColors.RESET);
        }
    }
}


