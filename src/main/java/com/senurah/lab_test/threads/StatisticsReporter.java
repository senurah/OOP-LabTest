package com.senurah.lab_test.threads;

import com.senurah.lab_test.core.TicketPool;
import com.senurah.lab_test.logging.Logger;

public class StatisticsReporter implements Runnable {

    private final TicketPool ticketPool;

    public StatisticsReporter(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (ticketPool) {
                int totalAdded = ticketPool.getTicketAdded();
                int totalRemoved = totalAdded - ticketPool.getTicketCount();
                int currentInPool = ticketPool.getTicketCount();

                Logger.log(String.format(
                        "Ticket Statistics:\n{\n" +
                                "Total Added: %d ,\n" +
                                "Total Removed: %d,\n" +
                                "Current in Pool: %d\n" +
                                "}",
                        totalAdded, totalRemoved, currentInPool));

            }
            try {
                Thread.sleep(5000); // Wait for 5 seconds
            } catch (InterruptedException e) {
                Logger.log("StatisticsReporter interrupted.");
                break;
            }
        }
    }

}
