package com.senurah.lab_test.core;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
public class TicketPool implements TicketOperation {
    private final List<String> tickets = Collections.synchronizedList(new LinkedList<>());
    @Override
    public synchronized void addTickets(String ticket) {
        tickets.add(ticket);
    }
    @Override
    public synchronized String removeTicket() {
        return tickets.isEmpty() ? null : tickets.remove(0);
    }
    public int getTicketCount() {
        return tickets.size();
    }
}