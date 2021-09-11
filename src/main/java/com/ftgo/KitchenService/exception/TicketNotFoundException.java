package com.ftgo.KitchenService.exception;

/**
 * Exception meaning ticket not found.
 */

public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException(Long ticketId) {
        super(ticketId.toString());
    }
}
