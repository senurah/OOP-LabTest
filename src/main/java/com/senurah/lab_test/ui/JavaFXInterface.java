package com.senurah.lab_test.ui;

import com.senurah.lab_test.config.Configuration;
import com.senurah.lab_test.core.TicketPool;
import com.senurah.lab_test.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import com.senurah.lab_test.threads.Customer;
import com.senurah.lab_test.threads.Vendor;

public class JavaFXInterface extends Application {
    private TextField totalTicketsField;
    private TextField ticketReleaseRateField;
    private TextField customerRetrievalRateField;
    private TextField maxTicketCapacityField;
    private Label statusLabel;
    private TableView<String> logTable;
    private TicketPool ticketPool;
    private Thread vendorThread;
    private Thread customerThread;
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Ticket Management System");
        // Configuration input fields
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        totalTicketsField = new TextField();
        ticketReleaseRateField = new TextField();
        customerRetrievalRateField = new TextField();
        maxTicketCapacityField = new TextField();
        gridPane.add(new Label("Total Tickets:"), 0, 0);
        gridPane.add(totalTicketsField, 1, 0);
        gridPane.add(new Label("Ticket Release Rate:"), 0, 1);
        gridPane.add(ticketReleaseRateField, 1, 1);
        gridPane.add(new Label("Customer Retrieval Rate:"), 0, 2);
        gridPane.add(customerRetrievalRateField, 1, 2);
        gridPane.add(new Label("Max Ticket Capacity:"), 0, 3);
        gridPane.add(maxTicketCapacityField, 1, 3);
        // Buttons
        Button startButton = new Button("Start");
        Button stopButton = new Button("Stop");
        gridPane.add(startButton, 0, 4);
        gridPane.add(stopButton, 1, 4);
        // Status Label
        statusLabel = new Label("System Status: Stopped");
        gridPane.add(statusLabel, 0, 5, 2, 1);
        // Log Table
        logTable = new TableView<>();
        logTable.setPlaceholder(new Label("Logs will appear here"));
        gridPane.add(new Label("System Logs:"), 0, 6);
        gridPane.add(logTable, 0, 7, 2, 1);
        // Start button event
        startButton.setOnAction(event -> {
            try {
                startSystem();
            } catch (Exception e) {
                Logger.log("Error starting system: " + e.getMessage());
                updateStatus("Error: Check input values.");
            }
        });
        // Stop button event
        stopButton.setOnAction(event -> stopSystem());
        // Scene setup
        Scene scene = new Scene(gridPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void startSystem() throws Exception {
        // Validate and parse input
        int totalTickets = validateInput(totalTicketsField.getText(), "Total Tickets");
        int ticketReleaseRate = validateInput(ticketReleaseRateField.getText(), "Ticket Release Rate");
        int customerRetrievalRate = validateInput(customerRetrievalRateField.getText(), "Customer Retrieval Rate");
        int maxTicketCapacity = validateInput(maxTicketCapacityField.getText(), "Max Ticket Capacity");
        // Initialize configuration and ticket pool
        Configuration config = new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
        ticketPool = new TicketPool(config.getMaxTicketCapacity(),config.getTotalTickets());
        // Start threads
        vendorThread = new Thread(new Vendor(ticketPool, config.getTicketReleaseRate()));
        customerThread = new Thread(new Customer(ticketPool));
        vendorThread.start();
        customerThread.start();
        updateStatus("System Running...");
    }
    private void stopSystem() {
        if (vendorThread != null && customerThread != null) {
            vendorThread.interrupt();
            customerThread.interrupt();
        }
        updateStatus("System Stopped.");
    }
    private void updateStatus(String status) {
        Platform.runLater(() -> statusLabel.setText("System Status: " + status));
    }
    private int validateInput(String input, String fieldName) throws Exception {
        try {
            int value = Integer.parseInt(input);
            if (value <= 0) {
                throw new Exception(fieldName + " must be positive.");
            }
            return value;
        } catch (NumberFormatException e) {
            throw new Exception(fieldName + " must be a valid integer.");
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
