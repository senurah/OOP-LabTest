package com.senurah.lab_test;

import com.senurah.lab_test.config.Configuration;
import com.senurah.lab_test.core.*;
import com.senurah.lab_test.logging.Logger;
import com.senurah.lab_test.threads.Customer;
import com.senurah.lab_test.threads.Vendor;
import com.senurah.lab_test.ui.CommandLineInterface;

public class TestStrategies  {
    public static void main(String[] args) {
        Configuration config = CommandLineInterface.configureSystem();
        TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity(), config.getTotalTickets());

        // Vendor thread
        Thread vendor = new Thread(new Vendor(ticketPool, config.getTicketReleaseRate()));

        // Customer thread with Priority Retrieval
        TicketRetrievalStrategy priorityStrategy = new PriorityRetrieval(ticketPool);
        Thread priorityCustomer = new Thread(new Customer(ticketPool, priorityStrategy));

        // Customer thread with ID Retrieval
        TicketRetrievalStrategy idStrategy = new IDRetrieval("Ticket- 3",ticketPool);
        Thread idCustomer = new Thread(new Customer(ticketPool, idStrategy));

        vendor.start();
        idCustomer.start();
        priorityCustomer.start();


        try {
            vendor.join();
            priorityCustomer.join();
            idCustomer.join();

        } catch (InterruptedException e) {
            Logger.log("Main thread interrupted.");
        }

        Logger.log("System terminated. Check logs for ticket operations.");
    }
}
