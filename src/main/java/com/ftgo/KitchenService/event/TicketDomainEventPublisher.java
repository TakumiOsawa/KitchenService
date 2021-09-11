package com.ftgo.KitchenService.event;

import com.ftgo.KitchenService.domain.ticket.entity.Ticket;
import io.eventuate.tram.events.publisher.DomainEventPublisher;

/**
 * Event Publisher specialized for a ticket aggregate.
 */

public class TicketDomainEventPublisher extends AbstractAggregateDomainEventPublisher<Ticket> {
    public TicketDomainEventPublisher(DomainEventPublisher eventPublisher) {
        super(eventPublisher, Ticket.class, Ticket::getId);
    }
}
