package com.senurah.lab_test.threads;

import com.senurah.lab_test.core.AbstractTicketHandler;
import com.senurah.lab_test.core.IDRetrieval;
import com.senurah.lab_test.core.TicketPool;
import com.senurah.lab_test.core.TicketRetrievalStrategy;
import com.senurah.lab_test.logging.Logger;


public class Customer extends AbstractTicketHandler implements Runnable {
//    public Customer(TicketPool ticketPool) {
//        super(ticketPool);
//    }
    private final TicketRetrievalStrategy retrievalStrategy;

    public Customer(TicketPool ticketPool, TicketRetrievalStrategy retrievalStrategy) {
        super(ticketPool);
        this.retrievalStrategy = retrievalStrategy;
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
//                    String ticket = ticketPool.removeTicket();
//                    String ticket = retrievalStrategy.retrieveTicket();
//                    if (ticket != null) {
//                        ticketPool.getTickets().remove(ticket);
//                        Logger.logTicketOperation(ticket,"Customer "+Thread.currentThread().getName()+" retrieved: ","[REMOVE]");
//                        ticketPool.notifyAll(); // Notify vendors
//                    } else {
//                        Logger.log("No more tickets left. Customer stopping...");
//                        break;
//                    }

                    String ticket = retrievalStrategy.retrieveTicket();
                    if (ticket != null && !ticket.equals("NULL")) {
                        ticketPool.getTickets().remove(ticket);
                        Logger.logTicketOperation(ticket, "Customer " + Thread.currentThread().getName() + " retrieved:", "[REMOVE]");
                        ticketPool.notifyAll(); // Notify vendors

                        // Stop the thread for ID retrieval once the target ticket is retrieved
                        if (retrievalStrategy instanceof IDRetrieval) {
                            Logger.log("Customer " + Thread.currentThread().getName() + " found the target ticket. Stopping...");
                            break;
                        }
                    } else if (ticket.equals("NULL")) {
                        Logger.log("Customer " + Thread.currentThread().getName() + " did not find the target ticket. Retrying...");
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
