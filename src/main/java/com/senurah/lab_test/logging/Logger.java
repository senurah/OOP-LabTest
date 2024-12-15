package com.senurah.lab_test.logging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
public class Logger {
    private static final String LOG_FILE = "resources/logs.txt";


//    public static void log(String message) {
//        String timeStampedMessage = LocalDateTime.now() + ": " + message;
//        System.out.println(timeStampedMessage);
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
//            writer.write(timeStampedMessage);
//            writer.newLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    // General logging method
    public static void log(String message) {
        String timeStampedMessage = LocalDateTime.now() + ": " + message;
        System.out.println(timeStampedMessage);
        writeToFile(timeStampedMessage);
    }

    // Method to log ticket operations
    public static void logTicketOperation(String ticketId,String message,  String operationType) {
        String logMessage = String.format("%s | %s | Ticket ID: %s | Operation: %s",
                LocalDateTime.now(),message, ticketId, operationType);
        System.out.println(logMessage);
        writeToFile(logMessage);
    }

    // Helper method to write logs to the file
    private static void writeToFile(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
