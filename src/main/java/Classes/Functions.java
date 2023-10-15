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
        Integer  userInput = Integer.valueOf(userLoginOption.nextLine());

        switch (userInput){
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
                /*More code goes here, such as Admin Panel etc.*/
                break;
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
    */ //DESC-Javadoc --> turistNavigatonOptions()
    public static void turistNavigationOptions(){
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n-------------------------------------------------------------");
        //System.out.println("| In order to navigate, choose one of the options below:    |");
        System.out.println("| You are now in the main menu, choose one of the options below:    |");
        System.out.println("-------------------------------------------------------------" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.ORANGE_BOLD_BRIGHT + "[1] Search Torus\n" +
                                                              "[2] Show favorite\n" +
                                                              "[3] Log out" + ConsoleColors.RESET);
        Scanner userLoginOption = new Scanner(System.in);
        Integer  userInput = Integer.valueOf(userLoginOption.nextLine());
    }



}
