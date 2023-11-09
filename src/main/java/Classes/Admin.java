package Classes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Admin extends userPanel {
    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static void addTour(int tourNumber) {
        // for structures json file
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the city number for the tour: ");
            int cityNumber = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter the tour number: ");

            tourNumber = Integer.parseInt(scanner.nextLine());
            String filePath = getFilePath(cityNumber);

            if (!filePath.equals("")) {
                if (tourExists(filePath, tourNumber)) {
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Tour  number is  already exists!" + ConsoleColors.RESET);
                    return;
                }

                // create a new tour node
                ObjectNode newTour = objectMapper.createObjectNode();
                newTour.put("tourNr", tourNumber);

                // Tour node

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

                // add the new tour node to the tours array
                ArrayNode toursArray = (ArrayNode) objectMapper.readTree(new File(filePath));
                toursArray.add(newTour);

                // write the updated tours array back to the JSON file
                objectMapper.writeValue(new File(filePath), toursArray);

                System.out.println("Tour added successfully!");
            } else {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Invalid choice!" + ConsoleColors.RESET);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
