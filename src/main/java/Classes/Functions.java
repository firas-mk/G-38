package Classes;

import java.io.File;
import java.io.IOException;

import java.util.Scanner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Functions {

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
        Integer  userInput = Integer.valueOf(userLoginOption.nextLine());

        switch (userInput) {
            case 1:
                searchAndDisplayTours();
                break;
            case 2:

                // Implement logic for showing favorite tours

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

    // implementation of searchAndDisplayTours() function
    public static void searchAndDisplayTours() {
        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + ConsoleColors.YELLOW_UNDERLINED + "Available Cities:" + ConsoleColors.RESET + ConsoleColors.ORANGE_BOLD_BRIGHT
                + "\n[1] Oslo"
                + "\n[2] Bergen"
                + "\n[3] Kristiansand"
                + "\n[4] Halden" + ConsoleColors.RESET
        );

        Scanner userInputScanner = new Scanner(System.in);
        int selectedCity = -1;

        while (selectedCity < 1 || selectedCity > 4) {
            System.out.println(ConsoleColors.YELLOW +"||> Enter the number of the city you want to explore [1-4]: " + ConsoleColors.RESET);
            selectedCity = Integer.parseInt(userInputScanner.nextLine());
        }
        switch (selectedCity) {
            case 1: // Oslo
                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n---------------------------");
                System.out.println("| Available tours in Oslo |");
                System.out.println("---------------------------" + ConsoleColors.RESET);
                Functions.getToursFromJSONfile("src/main/java/JSON_files/oslo_tours.json");
                break;
            case 2: // Bergen
                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n-----------------------------");
                System.out.println("| Available tours in Bergen |");
                System.out.println("-----------------------------" + ConsoleColors.RESET);
                Functions.getToursFromJSONfile("src/main/java/JSON_files/bergen_tours.json");
                break;
            case 3: // Kristiansand
                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n-----------------------------------");
                System.out.println("| Available tours in Kristiansand |");
                System.out.println("-----------------------------------" + ConsoleColors.RESET);
                Functions.getToursFromJSONfile("src/main/java/JSON_files/kristiansand_tours.json");
                break;
            case 4: // Halden
                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n-----------------------------");
                System.out.println("| Available tours in Halden |");
                System.out.println("-----------------------------" + ConsoleColors.RESET);
                Functions.getToursFromJSONfile("src/main/java/JSON_files/halden_tours.json");
                break;
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

