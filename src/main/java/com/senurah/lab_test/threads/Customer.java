package com.senurah.lab_test.threads;

import com.senurah.lab_test.core.AbstractTicketHandler;
import com.senurah.lab_test.core.TicketPool;
import com.senurah.lab_test.logging.Logger;


public class Customer extends AbstractTicketHandler implements Runnable {
    public Customer(TicketPool ticketPool) {
        super(ticketPool);
    }

    @Override
    public void run() {
        while (true) {
            synchronized (ticketPool) {
                if (!ticketPool.areTicketsAvailable()) {
                    Logger.log("No tickets available. Customer waiting...");
                    try {
                        ticketPool.wait();
                    } catch (InterruptedException e) {
                        Logger.log("Customer interrupted.");
                        break;
                    }
                } else {
                    String ticket = ticketPool.removeTicket();
                    if (ticket != null) {
                        Logger.logTicketOperation(ticket,"Customer retrieved: ","[REMOVE]");
                        ticketPool.notifyAll(); // Notify vendors
                    } else {
                        Logger.log("No more tickets left. Customer stopping...");
                        break;
                    }
                }
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Logger.log("Customer interrupted.");
                break;
            }
        }
    }

    @Override
    public void handleTickets() {
        run();
    }
}
