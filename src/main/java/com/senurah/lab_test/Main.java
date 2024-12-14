package com.senurah.lab_test;

import com.senurah.lab_test.config.Configuration;
import com.senurah.lab_test.core.PriorityRetrieval;
import com.senurah.lab_test.core.TicketPool;
import com.senurah.lab_test.core.TicketRetrievalStrategy;
import com.senurah.lab_test.logging.Logger;
import com.senurah.lab_test.threads.Customer;
import com.senurah.lab_test.threads.Vendor;
import com.senurah.lab_test.ui.CommandLineInterface;

public class Main {

    public static void main(String[] args) {
        Configuration config = CommandLineInterface.configureSystem();
        TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity(), config.getTotalTickets());
        Thread vendor = new Thread(new Vendor(ticketPool, config.getTicketReleaseRate()));
//        Thread customer = new Thread(new Customer(ticketPool));
        TicketRetrievalStrategy priorityStrategy = new PriorityRetrieval(ticketPool);
        Thread priorityCustomer = new Thread(new Customer(ticketPool, priorityStrategy));



        vendor.start();
//        customer.start();
        priorityCustomer.start();

        try {
            vendor.join();
//            customer.join();
            priorityCustomer.join();
        } catch (InterruptedException e) {
            Logger.log("Main thread interrupted.");
        }
        Logger.log("System terminated.");
    }
}