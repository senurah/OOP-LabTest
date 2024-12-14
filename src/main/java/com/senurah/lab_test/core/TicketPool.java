package com.senurah.lab_test.core;

import com.senurah.lab_test.logging.Logger;

import java.util.Collections;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
public class TicketPool implements TicketOperation {
    private final List<String> tickets = Collections.synchronizedList(new LinkedList<>());
    // new additions
    private int maxTicketCapacity ;

    //creating the limitation factor
    private int totalTickets;
    private int ticketAdded;

    //Initializing the parameterized constructor for the ticket pool
    public TicketPool(int maxTicketCapacity,int totalTickets){
        this.maxTicketCapacity = maxTicketCapacity;
        this.totalTickets = totalTickets;
//        this.tickets = Collections.synchronizedList(new LinkedList<>());

    }


    @Override
    public synchronized void addTickets(String ticket) {

        while(tickets.size()>= maxTicketCapacity){
            try{
                Logger.log("Ticket pool is full. Waiting customers to buy Tickets");
                wait();
            } catch (InputMismatchException e){
                Thread.currentThread().interrupt();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        //Giving a ticket Id

        if(totalTickets>0){
            ticketAdded++;
            ticket = ticket+" "+ticketAdded;
            tickets.add(ticket);
            totalTickets--;

            //this is not needed as the customer thread already does this
//            Logger.log("Ticket added to the pool by"+ Thread.currentThread().getName()+ "Ticket Pool :"+tickets.size());
            notifyAll();
        }

    }
    @Override
    public synchronized String removeTicket() {


        while(tickets.isEmpty() && totalTickets > 0){
            try{
                Logger.log("TicketPool is empty. Waiting for vendors to add tickets");
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if(!tickets.isEmpty()){
            String ticket = tickets.remove(0);
            notifyAll();
            return ticket;
        }else {
            Logger.log("No more tickets available.");
            return null;
        }

    }


    public int getTicketCount() {
        return tickets.size();
    }

    public synchronized boolean areTicketsAvailable(){
        return totalTickets > 0 || !tickets.isEmpty();
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public int getTotalTickets(){
        return totalTickets;
    }

    public int getTicketAdded(){
        return ticketAdded;
    }




}