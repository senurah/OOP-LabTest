package com.senurah.lab_test.config;

import com.google.gson.Gson;
import com.senurah.lab_test.exceptions.InvalidConfigurationException;

import java.io.*;
public class Configuration {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    public Configuration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) throws InvalidConfigurationException {
        validateConfiguration(totalTickets,ticketReleaseRate,customerRetrievalRate,maxTicketCapacity);
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public int getTotalTickets() { return totalTickets; }
    public int getTicketReleaseRate() { return ticketReleaseRate; }
    public int getCustomerRetrievalRate() { return customerRetrievalRate; }
    public int getMaxTicketCapacity() { return maxTicketCapacity; }

    public static Configuration loadFromFile(String filePath) throws IOException {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, Configuration.class);
        }
    }

    public void saveToFile(String filePath) throws IOException {
        Gson gson = new Gson();
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(this, writer);
        }
    }

    private void validateConfiguration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity)
            throws InvalidConfigurationException {
        if (totalTickets <= 0 || ticketReleaseRate <= 0 || customerRetrievalRate <= 0 || maxTicketCapacity <= 0) {
            throw new InvalidConfigurationException("All values must be greater than zero.");
        }

        if (ticketReleaseRate >= maxTicketCapacity) {
            throw new InvalidConfigurationException("Ticket release rate must be less than the maximum ticket capacity.");
        }

        if (customerRetrievalRate >= maxTicketCapacity) {
            throw new InvalidConfigurationException("Customer retrieval rate must be less than the maximum ticket capacity.");
        }

        if (ticketReleaseRate >= totalTickets) {
            throw new InvalidConfigurationException("Ticket release rate must be less than the total tickets.");
        }

        if (customerRetrievalRate >= totalTickets) {
            throw new InvalidConfigurationException("Customer retrieval rate must be less than the total tickets.");
        }
    }


}