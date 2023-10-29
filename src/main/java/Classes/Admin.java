package Classes;



import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;


import java.util.Scanner;

public class Admin extends userPanel {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void addTour(int tourNumber) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the city number for the tour: ");
            int cityNumber = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter the tour number: "); // Added this line
            tourNumber = Integer.parseInt(scanner.nextLine()); // Updated this line


            // create a new tour node
            ObjectNode newTour = objectMapper.createObjectNode();
            newTour.put("tourNr", tourNumber);

            //  Tour node

            System.out.println("Enter the location: ");
            newTour.put("location", scanner.nextLine());

            System.out.println("Enter the date: ");
            newTour.put("date", scanner.nextLine());

            System.out.println("Enter the time: ");
            newTour.put("time", scanner.nextLine());

            System.out.println("Enter the description: ");
            newTour.put("description", scanner.nextLine());

            System.out.println("Enter the price: ");
            newTour.put("price", scanner.nextLine());


        }
    }

}