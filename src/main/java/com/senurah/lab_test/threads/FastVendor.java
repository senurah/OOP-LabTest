package com.senurah.lab_test.threads;

import com.senurah.lab_test.core.TicketPool;
import com.senurah.lab_test.logging.Logger;

public class FastVendor extends Vendor{
    public FastVendor(TicketPool ticketPool, int ticketReleaseRate) {
        super(ticketPool, ticketReleaseRate*2);
    }

}
