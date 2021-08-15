package com.ftgo.KitchenService.event;

import com.ftgo.KitchenService.ticket.Ticket;
import io.eventuate.tram.events.publisher.DomainEventPublisher;

/**
 * Event Publisher specialized for a ticket aggregate.
 */

public class TicketDomainEventPublisher extends AbstractAggregateDomainEventPublisher<Ticket, TicketDomainEvent> {
    public TicketDomainEventPublisher(DomainEventPublisher eventPublisher) {
        super(eventPublisher, Ticket.class, Ticket::getId)
    }
}
