package org.example;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        System.out.println("\n" + ConsoleColors.ORANGE_BACKGROUND_BRIGHT + ConsoleColors.WHITE_BOLD_BRIGHT + "-------" +
                "| Login |" +
                "\t\t\t\t\t  " + ConsoleColors.RESET);
        System.out.println(ConsoleColors.ORANGE_BACKGROUND_BRIGHT + ConsoleColors.WHITE_BOLD_BRIGHT + "-------" +
                "| Powered by Schibsted |" + "\t\t  " +
                ConsoleColors.RESET);

        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n-------------------------------------------------------------");
        System.out.println("| Login by typing in the number of one of the options below |");
        System.out.println("-------------------------------------------------------------" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.ORANGE_BOLD_BRIGHT + "[1] Turist\n" +
                           "[2] Guide / Company\n" +
                           "[3] Admin" + ConsoleColors.RESET);
        Scanner UserLoginOption = new Scanner(System.in);
        Integer  UserInput = Integer.valueOf(UserLoginOption.nextLine());


        // checks if the user is an Admin or Tourist/Guide
        if (UserInput == 1){
            System.out.println(ConsoleColors.YELLOW + "You are now logged in as" + ConsoleColors.RED_BOLD_BRIGHT + " [Turist]" + ConsoleColors.RESET);


        }else if (UserInput == 2){
            System.out.println(ConsoleColors.YELLOW + "You are now logged in as"+ ConsoleColors.RED_BOLD_BRIGHT + " [Guide/Company] " + ConsoleColors.RESET );

        } else if (UserInput == 3) {
            System.out.println(ConsoleColors.YELLOW + "You are now logged in as"+ ConsoleColors.RED_BOLD_BRIGHT + " [Admin] " + ConsoleColors.RESET );
        }

    }

}
