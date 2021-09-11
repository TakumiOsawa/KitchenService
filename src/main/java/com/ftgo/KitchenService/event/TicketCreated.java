package com.ftgo.KitchenService.event;

import com.ftgo.KitchenService.domain.ticket.TicketDetails;

public class TicketCreated implements TicketDomainEvent{
    private Long id;
    private TicketDetails details;

    public TicketCreated(Long id, TicketDetails details) {
        this.id = id;
        this.details = details;
    }
}
