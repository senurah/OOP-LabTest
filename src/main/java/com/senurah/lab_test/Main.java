package com.senurah.lab_test;

import com.senurah.lab_test.config.Configuration;
import com.senurah.lab_test.core.PriorityRetrieval;
import com.senurah.lab_test.core.TicketPool;
import com.senurah.lab_test.core.TicketRetrievalStrategy;
import com.senurah.lab_test.logging.Logger;
import com.senurah.lab_test.threads.*;
import com.senurah.lab_test.ui.CommandLineInterface;

public class Main {

    public static void main(String[] args) {
        Configuration config = CommandLineInterface.configureSystem();
        TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity(), config.getTotalTickets());
        Thread vendor = new Thread(new Vendor(ticketPool, config.getTicketReleaseRate()));
//        Thread customer = new Thread(new Customer(ticketPool));
        TicketRetrievalStrategy priorityStrategy = new PriorityRetrieval(ticketPool);
        Thread priorityCustomer = new Thread(new Customer(ticketPool, priorityStrategy));

        //Fast vendor using method overloading:
        Thread fastVendor = new Thread(new Vendor(ticketPool, config.getTicketReleaseRate(), 2.0));

        //Fast vendor using overriding
        Thread fastVendor2 = new Thread(new FastVendor(ticketPool,config.getTicketReleaseRate()));

        //Slow vendor using overriding
        Thread slowVendor = new Thread(new SlowVendor(ticketPool, config.getTicketReleaseRate()));

        // Statistics Reporter
        Thread statisticsReporter = new Thread(new StatisticsReporter(ticketPool));





//        vendor.start();
//        customer.start();
        priorityCustomer.start();
        fastVendor.start();
        fastVendor2.start();
        slowVendor.start();
        statisticsReporter.start();




        try {
//            vendor.join();
//            customer.join();
            priorityCustomer.join();
            fastVendor.join();
            fastVendor2.join();
            slowVendor.join();
            statisticsReporter.interrupt();
//            statisticsReporter.join();
        } catch (InterruptedException e) {
            Logger.log("Main thread interrupted.");
        }
        Logger.log("System terminated.");
    }
}