package com.senurah.lab_test.ui;

import com.senurah.lab_test.config.Configuration;
import com.senurah.lab_test.exceptions.InvalidConfigurationException;
import com.senurah.lab_test.logging.Logger;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
public class CommandLineInterface {
    private static final String CONFIG_FILE_PATH = "Config.json";

    public static Configuration configureSystem() {
        Scanner scanner = new Scanner(System.in);
        Configuration config = null;
        System.out.println("Do you want to load the existing Configuration? (yes/no)");
        String userInput  = scanner.nextLine().trim().toLowerCase();


        if (userInput.equals("yes")) {
            try {
                config = Configuration.loadFromFile(CONFIG_FILE_PATH);
                Logger.log("Configuration loaded successfully from " + CONFIG_FILE_PATH);
                System.out.println("Configuration loaded successfully.");
                return config;
            } catch (IOException e) {
                Logger.log("Error loading configuration: " + e.getMessage());
                System.out.println("No existing configuration found. Proceeding with new configuration setup.");
            } catch (InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }
        }


        while(config==null){
            try {
                Logger.log("Starting system configuration...");
                int totalTickets = getInput(scanner, "Enter Total Tickets: ");
                int ticketReleaseRate = getInput(scanner, "Enter Ticket Release Rate: ");
                int customerRetrievalRate = getInput(scanner, "Enter Customer Retrieval Rate: ");
                int maxTicketCapacity = getInput(scanner, "Enter Max Ticket Capacity: ");
                config = new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
                Logger.log("System configured successfully.");
            }catch (InvalidConfigurationException e){
                Logger.log("Configuration error."+e.getMessage());
                System.out.println("Please re-enter valid configuration values.");

            }
        }
        //saving  to a json file
        System.out.println("Do you want to save this configuration for future use? (yes/no)");
        userInput = scanner.nextLine().trim().toLowerCase();
        if (userInput.equals("yes")) {
            try {
                config.saveToFile(CONFIG_FILE_PATH);
                Logger.log("Configuration saved successfully to " + CONFIG_FILE_PATH);
                System.out.println("Configuration saved successfully.");
            } catch (IOException e) {
                Logger.log("Error saving configuration: " + e.getMessage());
                System.out.println("Failed to save configuration.");
            }
        }

        return config;
    }
    private static int getInput(Scanner scanner, String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            try {
                value = Integer.parseInt(scanner.nextLine());
                if (value > 0) {
                    return value;
                } else {
                    System.out.println("Value must be positive. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }catch(InputMismatchException g){
                System.out.println("Please enter an Integer.");
            }
        }
    }
}