package com.senurah.lab_test.core;

public class PriorityRetrieval implements TicketRetrievalStrategy{
    private final TicketPool ticketPool;

    public PriorityRetrieval(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    @Override
    public synchronized String retrieveTicket() {
        // Assuming priority is based on the ticket order in the list (FIFO).
        return ticketPool.removeTicket();
    }
}
