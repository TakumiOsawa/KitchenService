package com.ftgo.KitchenService;

import com.ftgo.KitchenService.event.TicketDomainEventPublisher;
import com.ftgo.KitchenService.ticket.Ticket;
import io.eventuate.tram.events.common.DomainEvent;
import io.eventuate.tram.events.publisher.DomainEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Service to create ticket.
 */

public class KitchenService {
    private final TicketRepository ticketRepository;
    private final TicketDomainEventPublisher eventPublisher;

    public KitchenService(@Autowired TicketRepository ticketRepository,
                          @Autowired TicketDomainEventPublisher eventPublisher) {
        this.ticketRepository = ticketRepository;
        this.eventPublisher = eventPublisher;
    }

    public void accept(long ticketId, ZonedDateTime readyBy) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException(ticketId));
        List<DomainEvent> events = ticket.accept(readyBy);
        eventPublisher.publish(Ticket.class, ticketId, events);
    }
}
