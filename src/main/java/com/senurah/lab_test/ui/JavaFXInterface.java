package com.senurah.lab_test.ui;

import com.senurah.lab_test.config.Configuration;
import com.senurah.lab_test.core.PriorityRetrieval;
import com.senurah.lab_test.core.TicketPool;
import com.senurah.lab_test.core.TicketRetrievalStrategy;
import com.senurah.lab_test.threads.Customer;
import com.senurah.lab_test.threads.Vendor;
import com.senurah.lab_test.exceptions.InvalidConfigurationException;
import com.senurah.lab_test.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFXInterface extends Application {
    private TextField totalTicketsField;
    private TextField ticketReleaseRateField;
    private TextField customerRetrievalRateField;
    private TextField maxTicketCapacityField;
    private Label statusLabel;
    private ListView<String> ticketListView;
    private ObservableList<String> ticketList;
    private static final String CONFIG_FILE_PATH = "Config.json";
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
        Button saveButton = new Button("Save");
        Button loadButton = new Button("Load");

        gridPane.add(startButton, 0, 4);
        gridPane.add(stopButton, 1, 4);
        gridPane.add(saveButton, 0, 5);
        gridPane.add(loadButton, 1, 5);

        // Status Label
        statusLabel = new Label("System Status: Stopped");
        gridPane.add(statusLabel, 0, 6, 2, 1);

        // Ticket List View
        ticketList = FXCollections.observableArrayList();
        ticketListView = new ListView<>(ticketList);
        gridPane.add(new Label("Current Tickets in Pool:"), 0, 7);
        gridPane.add(ticketListView, 0, 8, 2, 1);

        // Button actions
        startButton.setOnAction(event -> {
            try {
                startSystem();
            } catch (Exception e) {
                Logger.log("Error starting system: " + e.getMessage());
                updateStatus("Error: Check input values.");
            }
        });

        stopButton.setOnAction(event -> stopSystem());

        saveButton.setOnAction(event -> saveConfiguration());
        loadButton.setOnAction(event -> loadConfiguration());

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
        ticketPool = new TicketPool(config.getMaxTicketCapacity(), config.getTotalTickets());

        // Start threads
        vendorThread = new Thread(() -> {
            Vendor vendor = new Vendor(ticketPool, ticketReleaseRate);
            vendor.run();
        });

        customerThread = new Thread(() -> {
            TicketRetrievalStrategy priorityStrategy = new PriorityRetrieval(ticketPool);
            Customer customer = new Customer(ticketPool, priorityStrategy); // Replace null with a valid strategy
            customer.run();
        });

        vendorThread.start();
        customerThread.start();

        // Update tickets dynamically
        new Thread(this::updateTicketListView).start();

        updateStatus("System Running...");

    }


    private void updateTicketListView() {
        while (true) {
            Platform.runLater(() -> {
                ticketList.setAll(ticketPool.getTickets());
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Logger.log("Ticket list updater interrupted.");
                break;
            }
        }
    }


    private void stopSystem() {
        updateStatus("System Stopped.");
    }

    private void saveConfiguration() {
        try {
            int totalTickets = validateInput(totalTicketsField.getText(), "Total Tickets");
            int ticketReleaseRate = validateInput(ticketReleaseRateField.getText(), "Ticket Release Rate");
            int customerRetrievalRate = validateInput(customerRetrievalRateField.getText(), "Customer Retrieval Rate");
            int maxTicketCapacity = validateInput(maxTicketCapacityField.getText(), "Max Ticket Capacity");

            Configuration config = new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
            config.saveToFile(CONFIG_FILE_PATH);
            Logger.log("Configuration saved successfully.");
            updateStatus("Configuration saved.");
        } catch (Exception e) {
            Logger.log("Error saving configuration: " + e.getMessage());
            updateStatus("Error: Could not save configuration.");
        }
    }

    private void loadConfiguration() {
        try {
            Configuration config = Configuration.loadFromFile(CONFIG_FILE_PATH);
            totalTicketsField.setText(String.valueOf(config.getTotalTickets()));
            ticketReleaseRateField.setText(String.valueOf(config.getTicketReleaseRate()));
            customerRetrievalRateField.setText(String.valueOf(config.getCustomerRetrievalRate()));
            maxTicketCapacityField.setText(String.valueOf(config.getMaxTicketCapacity()));

            Logger.log("Configuration loaded successfully.");
            updateStatus("Configuration loaded.");
        } catch (IOException e) {
            Logger.log("Error loading configuration: " + e.getMessage());
            updateStatus("Error: Could not load configuration.");
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
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

