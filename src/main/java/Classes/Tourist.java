package Classes;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Tourist  {
    private String touristId;
    private String name;
    private String contactInformation;

    public Tourist(String touristId, String name, String contactInformation) {
        this.touristId = touristId;
        this.name = name;
        this.contactInformation = contactInformation;
    }

    public String getTouristId() {
        return touristId;
    }

    public void setTouristId(String touristId) {
        this.touristId = touristId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
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
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static void touristPanel(){
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n-----------------------------------------------------------------");
        System.out.println("| You are now in the main menu, choose one of the options below |");
        System.out.println("-----------------------------------------------------------------" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.ORANGE_BOLD_BRIGHT + "[1] Show available Tours\n" +
                "[2] Show favorite\n" +
                "[3] Show Booked tours\n" +
                "[4] Log Out" + ConsoleColors.RESET);
        Scanner userLoginOption = new Scanner(System.in);
        int userInput = Integer.parseInt(userLoginOption.nextLine());
        switch (userInput) {
            case 1:
                UserPanel.searchAndDisplayCities();
                break;
            case 2:
                getFavoriteTours(); // Show favorite tours
                break;

            case 3:
                getAndDisplayBookedTours();
                touristPanel();
                break;
            case 4:
                UserPanel.logOut();
                break;
            default:
                System.out.println(ConsoleColors.RED + "◆ Invalid number! Enter 1, 2, or 3 " + ConsoleColors.RESET);
                touristPanel();
                break;
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
                UserPanel.searchAndDisplayCities(); // Go back to the list of available cities
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
                        UserPanel.searchAndDisplayCities();// Go back to the list of available cities
                    } else if (userChoice <= totalTours && userChoice >= 1){
                        bookOrAddTourToFavorite(userChoice, cityNumber, cityName, jsonTourFilePath);

                    }
                }

            } else {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "◆ Invalid city number, enter a valid number!" + ConsoleColors.RESET);
                UserPanel.searchAndDisplayCities();
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
            int totalTours = jsonFile.size();
            // if the json-file is empty (which means if there is no tours available) a message will be printed to the user
            if (jsonFile.isEmpty()){
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "◆ Oops, looks like there is no tours available! " + ConsoleColors.RESET);
                UserPanel.searchAndDisplayCities();
            }else {
                int totalAvailableTours = 0;

                //Iterate throw json file and get information
                for(JsonNode tour : jsonFile){
                    String tourNr = tour.get("tourNr").asText();
                    String location = tour.get("location").asText();
                    String date = tour.get("date").asText();
                    String time = tour.get("time").asText();
                    String description = tour.get("description").asText();
                    String price = tour.get("price").asText();
                    String status = tour.get("status").asText();
                    if (status.equals("available")){

                        System.out.println(
                                "\n"+ ConsoleColors.YELLOW_UNDERLINED + ConsoleColors.YELLOW_BOLD_BRIGHT + "[*] Tour number " + ConsoleColors.RED_BOLD_BRIGHT +  tourNr + ConsoleColors.RESET +
                                        "\n" + ConsoleColors.RED_BOLD_BRIGHT +"[*] " + ConsoleColors.RESET + "Location: " + location +
                                        "\n" + ConsoleColors.RED_BOLD_BRIGHT +"[*] " + ConsoleColors.RESET + "Date: " + date +
                                        "\n" + ConsoleColors.RED_BOLD_BRIGHT +"[*] " + ConsoleColors.RESET + "Time: " + time +
                                        "\n" + ConsoleColors.RED_BOLD_BRIGHT +"[*] " + ConsoleColors.RESET + "Description: " + description +
                                        "\n" + ConsoleColors.RED_BOLD_BRIGHT +"[*] " + ConsoleColors.RESET + "Price: " + price
                        );
                        for (int i = 0; i <= totalTours; i++) {
                            if (status.equals("available")){
                                totalAvailableTours += 1;
                            }}
                    } else if (totalAvailableTours == 0) {
                        System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "◆ Oops, looks like there is no tours available! " + ConsoleColors.RESET);
                        UserPanel.searchAndDisplayCities();
                    }


                }
            }} catch (IOException e) {
            e.printStackTrace();
        }



    }

    // reads data from favorite_tours.json and displays it to tourist
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
                    location = tour.get("location").asText();
                    String date = tour.get("date").asText();
                    String time = tour.get("time").asText();
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

    // gets booked tour and
    public static void getAndDisplayBookedTours(){
        try{
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n--------------------------------");
            System.out.println("|       Your Booked Tours      |");
            System.out.println("--------------------------------" + ConsoleColors.RESET);

            String bookedToursJsonFile = "src/main/java/JSON_files/bookings.json";
            JsonNode bookedToursFile = objectMapper.readTree(new File(bookedToursJsonFile));

            if (bookedToursFile.isEmpty()) {
                System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "◆ You have no booked tours yet." + ConsoleColors.RESET);
                touristPanel();
            }else {

                for (JsonNode tour : bookedToursFile) {
                    String city = tour.get("city").asText();
                    String location = tour.get("location").asText();
                    String date = tour.get("date").asText();
                    String time = tour.get("time").asText();
                    String tourID = tour.get("tourID").asText();

                    System.out.println(
                            "\n" + ConsoleColors.RED_BOLD_BRIGHT + "[*] " + ConsoleColors.RESET + "City: " + city +
                                    "\n" + ConsoleColors.RED_BOLD_BRIGHT + "[*] " + ConsoleColors.RESET + "Location: " + location +
                                    "\n" + ConsoleColors.RED_BOLD_BRIGHT + "[*] " + ConsoleColors.RESET + "Date & Time: " + date + " - " + time +
                                    "\n" + ConsoleColors.RED_BOLD_BRIGHT + "[*] " + ConsoleColors.RESET + "TourID: " + tourID +
                                    "\n" + ConsoleColors.RED_BOLD_BRIGHT + "===============================" + ConsoleColors.RESET
                    );
                }

            }



        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    // booking logic, books a tour either from search section or directly from favored tours, then add the booked tour to a bookings.json
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

                    }
                    bookingsArray.add(bookedTour);
                    objectMapper.writeValue(new File(bookingsJsonFilepath), bookingsArray);
                    payment();
                    System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "◆ Tour booked successfully ✔" + ConsoleColors.RESET);
                    UserPanel.searchAndDisplayCities();
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
                    payment();
                    System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "◆ Tour booked successfully ✔" + ConsoleColors.RESET);
                    removeTourFromFavorite(tourNr);
                    touristPanel();
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + bookingContext);
            }


        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /* assigns json values into variables, the logic is duplicated inside bookTour() function,
    therefore it moved into an own function */
    public static void assignJsonValues(JsonNode bookingFile, ObjectNode bookedTour, JsonNode tour, String city) {
        int tourNumber;
        String tourID = tour.get("tourID").asText();
        String location = tour.get("location").asText();
        String date = tour.get("date").asText();
        String time = tour.get("time").asText();
        String description = tour.get("description").asText();
        String price = tour.get("price").asText();
        String status = tour.get("status").asText();
        String guideID = tour.get("guideID").asText();



        tourNumber = bookingFile.size();
        bookedTour.put("tourNr", tourNumber + 1);
        bookedTour.put("tourID", tourID);
        bookedTour.put("city", city);
        bookedTour.put("location", location);
        bookedTour.put("date", date);
        bookedTour.put("time", time);
        bookedTour.put("description", description);
        bookedTour.put("price", price);
        bookedTour.put("status", status);
        bookedTour.put("guideID", guideID);
        bookedTour.put("touristID", "TouristID");

    }

    // displays two options to tourist "Add tour to favorite" and "Book tour", and handle tourist's choice
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
                    UserPanel.searchAndDisplayCities(); // Automatically go back to the list of available cities
                    break;
                case 2:
                    bookTour(selectedTour, cityName, JSONFilepath,"Search");
                    break;

            }

        }


    }

    /* displays two options to tourist when chosing a favored tour
    "Remove tour from favorite" and "Book tour", and handle tourist's choice*/
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
                    removeTourFromFavorite(selectedTour);
                    touristPanel(); // Automatically go back to the tourist panel
                    break;
                case 2:
                    bookTour(selectedTour, cityName, JSONFilepath,"Favorite");
                    break;

            }

        }

    }

    /* Gets values of the tour chosen by tourist and adds it to favorite_tours.json */
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
                    String status = tour.get("status").asText();
                    String guideID = tour.get("guideID").asText();

                    tourNumber = favoriteToursJsonFile.size();
                    favoredTour.put("tourNr", tourNumber + 1);
                    favoredTour.put("city", cityName);
                    favoredTour.put("cityNr", cityNr);
                    favoredTour.put("location", location);
                    favoredTour.put("date", date);
                    favoredTour.put("time", time);
                    favoredTour.put("description", description);
                    favoredTour.put("price", price);
                    favoredTour.put("status", status);
                    favoredTour.put("guideID", guideID);

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
    public static void removeTourFromFavorite(int tourNr) {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            String favoriteToursJsonFilepath = "src/main/java/JSON_files/favorite_tours.json";
            JsonNode favoriteToursFile = objectMapper.readTree(new File(favoriteToursJsonFilepath));
            ArrayNode favoredToursArray = (ArrayNode) favoriteToursFile;

            for(int i = 0; i < favoredToursArray.size(); i++){
                JsonNode tour = favoredToursArray.get(i);
                int tourNumber = tour.get("tourNr").asInt();
                if (tourNumber == tourNr){
                    favoredToursArray.remove(i);
                    break;
                }

            }
            objectMapper.writeValue(new File(favoriteToursJsonFilepath), favoredToursArray);
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "◆ Tour removed from favorites ✔" + ConsoleColors.RESET);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void payment(){
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n-----------------------------------------");
        System.out.println("|       Choose a payment method         |");
        System.out.println("-----------------------------------------" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.ORANGE_BOLD_BRIGHT
                + "[1] VisaCard\n"
                + "[2] Klarna\n"
                + "[3] Vipps"
                + ConsoleColors.RESET
        );
        Scanner userInputPayment = new Scanner(System.in);
        System.out.println(ConsoleColors.YELLOW + "||> Choose a payment method: " + ConsoleColors.RESET);
        int userChoice = Integer.parseInt(userInputPayment.nextLine());
        String paymentMethod = "";

        switch (userChoice){
            case 1:
                paymentMethod = "VisaCard";
                break;
            case 2:
                paymentMethod = "Klarna";
                break;
            case 3:
                paymentMethod = "Vipps";
                break;

        }

        Scanner userInputEmail = new Scanner(System.in);
        System.out.println(ConsoleColors.YELLOW + "||> Provide us your e-mail to receive the payment invoice: " + ConsoleColors.RESET);
        String userEmail = String.valueOf(userInputEmail.nextLine());
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "◆ Invoice with payment details for " + paymentMethod + " has been sent to '" + userEmail + "' ✔");

    }


}
