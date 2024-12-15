package com.senurah.lab_test.core;

import com.senurah.lab_test.logging.Logger;

public class IDRetrieval implements TicketRetrievalStrategy{
    private final String targetID;
    private final TicketPool ticketPool;

    public IDRetrieval(String targetID,TicketPool ticketPool) {
        this.targetID = targetID;
        this.ticketPool = ticketPool;
    }

    @Override
    public String retrieveTicket() {
        synchronized (ticketPool) {
            if (!ticketPool.areTicketsAvailable()) return null;
            return ticketPool.getTickets()
                    .stream()
                    .filter(ticket -> ticket.contains(targetID))
                    .findFirst()
                    .orElse("NULL");
        }
    }

}
