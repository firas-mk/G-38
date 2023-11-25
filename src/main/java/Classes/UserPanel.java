package Classes;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



public class UserPanel {

    static boolean loginVerified = false;
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
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n-------------------------------------------------------------"+
                "\n| Login by typing in the number of one of the options below |"+
                "\n-------------------------------------------------------------" + ConsoleColors.RESET+
                ConsoleColors.ORANGE_BOLD_BRIGHT +
                "\n[1] Tourist\n" +
                "[2] Guide\n" +
                "[3] Admin" + ConsoleColors.RESET);
        Scanner userLoginOption = new Scanner(System.in);
        int  userInput = Integer.parseInt(userLoginOption.nextLine());

        switch (userInput) {
            case 1:
                loadingProgress();
                System.out.println(ConsoleColors.YELLOW + "You are now logged in as" + ConsoleColors.RED_BOLD_BRIGHT + " [Tourist]" + ConsoleColors.RESET);
                Tourist tourist = new Tourist("TouristId", "Tourist name", "Contact info");
                loginVerified = true;
                touristPanel();
                break;
            case 2:
                loadingProgress();
                System.out.println(ConsoleColors.YELLOW + "You are now logged in as" + ConsoleColors.RED_BOLD_BRIGHT + " [Guide]" + ConsoleColors.RESET);
                Guide guide = new Guide("GuideId", "Guide Name", "Contact Info");
                loginVerified = true;
                guidePanel();
                break;
            case 3:
                loadingProgress();
                System.out.println(ConsoleColors.YELLOW + "You are now logged in as" + ConsoleColors.RED_BOLD_BRIGHT + " [Admin]" + ConsoleColors.RESET);
                Admin admin = new Admin();
                loginVerified = true;
                adminPanel();
                break;
            /*More code goes here, such as Admin Panel etc.*/
            default:
                loginVerified = false;
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "◆ invalid number! Enter 1, 2 or 3" + ConsoleColors.RESET);
                loginPanel();
        }
    }

    public static void logOut() {
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "◆ You are successfully logged out, See you later :) " + ConsoleColors.RESET);
        loginVerified = false;
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

    /* Method to get available cities from a JSON file,
        whenever a cities is being added to the json file it will dynamically be printed
    */
    public static void getAvailableCities(String file){
        try{

            ObjectMapper objectMapper = new ObjectMapper();

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

    /**
     *
     *  <b>touristPanel()</b> is a function that gives the user who's logged in as "Tourist" several options to chose
     *  from in order to navigate further
     *  <p>
     *  We handle it as a "navigation panel/bar" or "main menu" where the the user can choose between the options in order to:
     *  </p>
     *  <ul>
     *      <li> Show available tours
     *      <li> Show favorite
     *      <li> Show booked tours
     *      <li> log out
     * </ul>
     */ //DESC-Javadoc ---> touristPanel()
    public static void touristPanel(){
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n-----------------------------------------------------------------");
        System.out.println("| You are now in the main menu, choose one of the options below |");
        System.out.println("-----------------------------------------------------------------" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.ORANGE_BOLD_BRIGHT + "[1] Show available Tours\n" +
                "[2] Show favorite\n" +
                "[3] Show Booked tours\n" +
                "[4] Log Out" + ConsoleColors.RESET);
        Scanner userInput = new Scanner(System.in);
        int userChoice = Integer.parseInt(userInput.nextLine());
        switch (userChoice) {
            case 1:
                Tourist.searchAndDisplayCities();
                break;
            case 2:
                Tourist.getFavoriteTours(); // Show favorite tours
                break;

            case 3:
                Tourist.getAndDisplayBookedTours();
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

    //adminPanel
    public static void adminPanel() {
        boolean isAdminRunning = true;
        Scanner scanner = new Scanner(System.in);

        while (isAdminRunning) {
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n-----------------------------------------------------------------");
            System.out.println("| You are now in the main menu, choose one of the options below |");
            System.out.println("-----------------------------------------------------------------" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.ORANGE_BOLD_BRIGHT + "[1] Show all tours\n" +
                    "[2] Add a tour\n" +
                    "[3] Delete a tour\n" +
                    "[4] Edit a tour\n" +
                    "[5] Log out" + ConsoleColors.RESET);

            int adminChoice = Integer.parseInt(scanner.nextLine());
            String availableCitiesFilePath = "src/main/java/JSON_files/available_cities.json";
            switch (adminChoice) {
                case 1:
                    Admin.showAllTours();
                    break;
                case 2:
                    System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n--------------------");
                    System.out.println("| Available cities |");
                    System.out.println("--------------------" + ConsoleColors.RESET);
                    getAvailableCities(availableCitiesFilePath);
                    Admin.addTour();
                    break;

                case 3:
                    System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n--------------------");
                    System.out.println("| Available cities |");
                    System.out.println("--------------------" + ConsoleColors.RESET);
                    getAvailableCities(availableCitiesFilePath);
                    System.out.println(ConsoleColors.YELLOW_BRIGHT +"||> Enter the city number for the tour you want to delete, [0 -> Main Menu]: ");
                    int cityNumberToDelete = Integer.parseInt(scanner.nextLine());
                    if (cityNumberToDelete == 0){
                        adminPanel();
                    }else {
                        System.out.println("||> Enter the tour number you want to delete, [0 -> Main Menu]: " + ConsoleColors.RESET);
                        int tourNumberToDelete = Integer.parseInt(scanner.nextLine());
                        if (tourNumberToDelete == 0){
                            adminPanel();
                        }else {
                            Admin.deleteTour(cityNumberToDelete, tourNumberToDelete);
                        }
                        break;
                    }
                    break;

                case 4:
                    Admin.editTour();
                    break;
                case 5:
                    logOut();
                    isAdminRunning = false;
                    break;
                default:
                    System.out.println(ConsoleColors.RED + "◆ Invalid number! Enter 1, 2, or 3 " + ConsoleColors.RESET);
                    adminPanel();
                    break;

            }
        }
    }

    //GuidePanel
    public static void guidePanel() {
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n-------------------------------------------------------------");
        System.out.println("|        Guide Menu: Choose one of the options below        |");
        System.out.println("-------------------------------------------------------------" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.ORANGE_BOLD_BRIGHT +
                "[1] Find Tours to book\n" +
                "[2] Show Booked tours\n" +
                "[3] Log Out" + ConsoleColors.RESET);

        Scanner scanner = new Scanner(System.in);
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {

            case 1:
                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n--------------------");
                System.out.println("| Available cities |");
                System.out.println("--------------------" + ConsoleColors.RESET);
                getAvailableCities("src/main/java/JSON_files/available_cities.json");

                Guide.bookTour("GuideId");
                guidePanel();
                break;
            case 2:
                Guide.showBookedTours("GuideId");
                guidePanel();
                break;
            case 3:
                logOut();
                break;
            default:
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "◆ Invalid number! Enter 1, 2, or 3 " + ConsoleColors.RESET);
                guidePanel();
                break;
        }
    }



}


