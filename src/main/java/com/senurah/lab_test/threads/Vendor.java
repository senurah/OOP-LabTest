package com.senurah.lab_test.threads;


import com.senurah.lab_test.core.AbstractTicketHandler;
import com.senurah.lab_test.core.TicketPool;
import com.senurah.lab_test.logging.Logger;


public class Vendor extends AbstractTicketHandler implements Runnable {
    private final int ticketReleaseRate;
    public Vendor(TicketPool ticketPool, int ticketReleaseRate) {
        super(ticketPool);
        this.ticketReleaseRate = ticketReleaseRate;
    }

    // Given in the pdf
//    @Override
//    public void run() {
//        for (int i = 0; i < ticketReleaseRate; i++) {
//            String ticket = "Ticket-" + System.nanoTime();
//            ticketPool.addTickets(ticket);
//            Logger.log("Vendor added: " + ticket);
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                Logger.log("Vendor interrupted.");
//            }
//        }
//    }


    @Override
    public void run() {
        while (true) {
            synchronized (ticketPool) {
                if (ticketPool.getTicketCount() >= ticketPool.getMaxTicketCapacity()) {
                    Logger.log("Ticket pool is full. Vendor waiting...");
                    try {
                        ticketPool.wait();
                    } catch (InterruptedException e) {
                        Logger.log("Vendor interrupted.");
                        break;
                    }
                } else if (ticketPool.areTicketsAvailable()) {
                    String ticket = "Ticket-";
                    ticketPool.addTickets(ticket);
                    Logger.logTicketOperation(String.valueOf(ticketPool.getTicketAdded()),"Vendor added: ","[ADD]");
//                    Logger.log("Vendor added: " + ticket+"Tickets remaining :"+ticketPool.getTotalTickets());
                    ticketPool.notifyAll(); // Notify customers
                } else {
                    Logger.log("No more tickets to release. Vendor stopping...");
                    break;
                }
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Logger.log("Vendor interrupted.");
                break;
            }
        }
    }

    @Override
    public void handleTickets() {
        run();
    }
}