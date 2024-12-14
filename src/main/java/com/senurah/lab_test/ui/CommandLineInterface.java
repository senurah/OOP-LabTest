package com.senurah.lab_test.ui;

import com.senurah.lab_test.config.Configuration;
import com.senurah.lab_test.exceptions.InvalidConfigurationException;
import com.senurah.lab_test.logging.Logger;

import java.util.InputMismatchException;
import java.util.Scanner;
public class CommandLineInterface {
    public static Configuration configureSystem() {
        Scanner scanner = new Scanner(System.in);
        Configuration config = null;


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