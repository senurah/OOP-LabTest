package com.senurah.lab_test.core;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
public class TicketPool implements TicketOperation {
    private final List<String> tickets = Collections.synchronizedList(new LinkedList<>());
    // new additions
    private int maxTicketCapacity ;

    //Initializing the parameterized constructor for the ticket pool
    public TicketPool(int maxTicketCapacity){
        this.maxTicketCapacity = maxTicketCapacity;
    }


    @Override
    public synchronized void addTickets(String ticket) {

        if(tickets.size() < maxTicketCapacity){
            tickets.add(ticket);
        } else {
            System.out.println("Ticket pool is full.. Waiting customers to buy Tickets");
        }

    }
    @Override
    public synchronized String removeTicket() {
        if(tickets.isEmpty()) {
            return "Ticket pool is empty.. waiting to vendors to add tickets";
        } else {
            return tickets.remove(0);
        }
    }
    public int getTicketCount() {
        return tickets.size();
    }
}