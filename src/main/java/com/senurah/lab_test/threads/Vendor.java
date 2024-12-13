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
    @Override
    public void run() {
        for (int i = 0; i < ticketReleaseRate; i++) {
            String ticket = "Ticket-" + System.nanoTime();
            ticketPool.addTickets(ticket);
            Logger.log("Vendor added: " + ticket);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Logger.log("Vendor interrupted.");
            }
        }
    }
    @Override
    public void handleTickets() {
        run();
    }
}