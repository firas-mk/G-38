package Classes;

import java.util.Scanner;

public class Functions {

    public static void loginPanel() {
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n-------------------------------------------------------------");
        System.out.println("| Login by typing in the number of one of the options below |");
        System.out.println("-------------------------------------------------------------" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.ORANGE_BOLD_BRIGHT + "[1] Turist\n" +
                "[2] Guide / Company\n" +
                "[3] Admin" + ConsoleColors.RESET);
        Scanner userLoginOption = new Scanner(System.in);
        Integer userInput = Integer.valueOf(userLoginOption.nextLine());

        switch (userInput) {
            case 1:
                loadingProgress();
                System.out.println(ConsoleColors.YELLOW + "You are now logged in as" + ConsoleColors.RED_BOLD_BRIGHT + " [Turist]" + ConsoleColors.RESET);
                turistNavigationOptions();
                break;
            case 2:
                loadingProgress();
                System.out.println(ConsoleColors.YELLOW + "You are now logged in as" + ConsoleColors.RED_BOLD_BRIGHT + " [Guide / Company]" + ConsoleColors.RESET);
                /*More code goes here, such as Guide Panel, etc.*/
                break;
            case 3:
                loadingProgress();
                System.out.println(ConsoleColors.YELLOW + "You are now logged in as" + ConsoleColors.RED_BOLD_BRIGHT + " [Admin]" + ConsoleColors.RESET);
                /*More code goes here, such as Admin Panel, etc.*/
                break;
            default:
                System.out.println(ConsoleColors.RED + "▢ invalid number! Enter 1, 2, or 3 " + ConsoleColors.RESET);
                loginPanel();
        }
    }

    public static void loadingProgress() {
        int totalTasks = 5;

        for (int i = 0; i < totalTasks; i++) {
            int progress = (i * 100) / totalTasks;
            System.out.print("Logging in: " + progress + "% [");

            int numberOfHashes = (i * 10) / totalTasks;
            for (int j = 0; j < numberOfHashes; j++) {
                System.out.print("#");
            }

            for (int j = numberOfHashes; j < 10; j++) {
                System.out.print(" ");
            }

            System.out.print("]\r");

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void turistNavigationOptions() {
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n-------------------------------------------------------------");
        System.out.println("| You are now in the main menu, choose one of the options below:    |");
        System.out.println("-------------------------------------------------------------" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.ORANGE_BOLD_BRIGHT + "[1] Search Tours\n" +
                "[2] Show favorite\n" +
                "[3] Log out" + ConsoleColors.RESET);

        Scanner userInputScanner = new Scanner(System.in);
        int userInput = Integer.parseInt(userInputScanner.nextLine());

        switch (userInput) {
            case 1:
                searchAndDisplayTours();
                break;
            case 2:
                // Implement logic for showing favorite tours
                break;
            case 3:
                // Implement logout logic
                break;
            default:
                System.out.println(ConsoleColors.RED + "▢ Invalid number! Enter 1, 2, or 3 " + ConsoleColors.RESET);
                turistNavigationOptions();
        }
    }

    public static void searchAndDisplayTours() {
        System.out.println("Available Cities:");
        System.out.println("1 - Oslo");
        System.out.println("2 - Bergen");
        System.out.println("3 - Kristiansand");
        System.out.println("4 - Halden");

        Scanner userInputScanner = new Scanner(System.in);
        int selectedCity = -1;

        while (selectedCity < 1 || selectedCity > 4) {
            System.out.print("Enter the number of the city you want to explore (1-4): ");
            selectedCity = Integer.parseInt(userInputScanner.nextLine());
        }

        // Now, you can add tours for the selected city
        String[] cityTours = null;

        switch (selectedCity) {
            case 1: // Oslo
                cityTours = new String[]{"Tour 1: Oslo City Tour", "Tour 2: Oslo Fjord Cruise"};
                break;
            case 2: // Bergen
                cityTours = new String[]{"Tour 1: Bergen Harbor Tour", "Tour 2: Bergen Mountain Hike"};
                break;
            case 3: // Kristiansand
                cityTours = new String[]{"Tour 1: Kristiansand Beach Day", "Tour 2: Kristiansand Cultural Tour"};
                break;
            case 4: // Halden
                cityTours = new String[]{"Tour 1: Halden Castle Tour", "Tour 2: Halden Forest Walk"};
                break;
        }

        System.out.println("Tours in the selected city:");
        for (int i = 0; i < cityTours.length; i++) {
            System.out.println((i + 1) + " - " + cityTours[i]);
        }

        int selectedTour = -1;
        while (selectedTour < 1 || selectedTour > cityTours.length) {
            System.out.print("Enter the number of the tour you want to book (1-" + cityTours.length + "): ");
            selectedTour = Integer.parseInt(userInputScanner.nextLine());
        }

        // You can add further logic here to handle the selected tour, e.g., book it or perform other actions.
    }
}